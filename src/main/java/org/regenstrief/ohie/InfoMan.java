/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */
package org.regenstrief.ohie;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.util.Util;
import org.regenstrief.util.XMLUtil;
import org.w3c.dom.NodeList;

/**
 * InfoMan
 */
public class InfoMan {
    
    private static final Log log = LogFactory.getLog(InfoMan.class);
    
    private final static String URL = Util.getProperty("org.regenstrief.ohie.InfoMan.url", "http://hwr.test.ohie.org:8984/CSD/csr/anonymous/careServicesRequest");
    
    private final static Map<String, Filler> fillers = new HashMap<String, Filler>();
    
    private static Integer defaultMax = initDefaultMax();
    
    private static String TMP_FACILITY_SEARCH = null;
    
    private static String TMP_PROVIDER_SEARCH = null;
    
    public final static class CodedType {
        
        private final String code;
        
        private final String codingScheme;
        
        public CodedType(final String code, final String codingScheme) {
            this.code = code;
            this.codingScheme = codingScheme;
        }
        
        private String[] getVars() {
            return new String[] { this.code, this.codingScheme };
        }
    }
    
    private static class BaseArgs {
        
        private Integer _max = null;
        
        public void setMax(final Integer max) {
            this._max = max;
        }
        
        protected Integer getMax() {
            return Util.nvl(_max, defaultMax);
        }
    }
    
    public final static class FacilityArgs extends BaseArgs {
        
        private String primaryName = null;
        
        public void setPrimaryName(final String primaryName) {
            this.primaryName = primaryName;
        }
    }
    
    public final static class ProviderArgs extends BaseArgs {
        
        private String commonName = null;
        
        private String organization = null;
        
        private String facility = null;
        
        private CodedType type = null;
        
        public void setCommonName(final String commonName) {
            this.commonName = commonName;
        }
        
        public void setOrganization(final String organization) {
            this.organization = organization;
        }
        
        public void setFacility(final String facility) {
            this.facility = facility;
        }
        
        public void setType(final CodedType type) {
            this.type = type;
        }
    }
    
    private final static class Filler {
        
        private final String original;
        
        private final String template;
        
        private Filler(final String path) {
            final List<String> tokens = Util.splitExactIntoList(path, '/');
            final StringBuilder o = new StringBuilder();
            boolean attr = false;
            int vars = 1;
            for (final String token : tokens) {
                if (attr) {
                    throw new IllegalArgumentException("Attribute found before last token of " + path);
                } else if (token.startsWith("@")) {
                    final List<String> attrs = Util.splitExactIntoList(token, ';');
                    vars = attrs.size();
                    int i = 0;
                    for (final String a : attrs) {
                        o.append(' ').append(a, (i == 0) ? 1 : 0, a.length()).append("=\"").append(getVar(i)).append('"');
                        i++;
                    }
                    attr = true;
                    continue;
                } else if (o.length() > 0) {
                    o.append('>');
                }
                o.append('<').append(token);
            }
            if (attr) {
                o.append('/');
            }
            o.append('>');
            if (!attr) {
                o.append(getVar(0));
            }
            Collections.reverse(tokens);
            boolean skip = false;
            for (final String token : tokens) {
                if (attr) {
                    attr = false;
                    skip = true;
                    continue;
                } else if (skip) {
                    skip = false;
                    continue;
                }
                o.append("</").append(token).append('>');
            }
            this.template = o.toString();
            String orig = this.template;
            for (int i = 0; i < vars; i++) {
                orig = getOneReplacement(orig, i, "");
            }
            this.original = orig;
//System.out.println(this.original);
        }
        
        private String fill(final String in, final Object val) {
            final Object[] vals;
            if (val == null) {
                vals = null;
            } else if (val instanceof CodedType) {
                vals = ((CodedType) val).getVars();
            } else {
                vals = new Object[] { val };
            }
            return fillArray(in, vals);
        }
        
        private String fillArray(final String in, final Object[] vals) {
            final String replacement;
            if (vals == null) {
                replacement = "";
            } else {
                replacement = getReplacement(vals);
            }
            return Util.replaceAllExact(in, this.original, replacement);
        }
        
        private String getReplacement(final Object... vals) {
            String r = this.template;
            int i = 0;
            for (final Object val : vals) {
                if (val == null) {
                    throw new NullPointerException("fill should be all-valued or just null");
                }
                r = getOneReplacement(r, i, val.toString());
                i++;
            }
            return r;
        }
        
        private String getOneReplacement(final String r, final int i, final String val) {
            return Util.replaceAllExact(r, getVar(i), val);
        }
        
        private String getVar(final int i) {
            return "<$" + i + ">";
        }
    }
    
    private final static Filler getFiller(final String tag) {
        Filler filler = fillers.get(tag);
        if (filler == null) {
            filler = new Filler(tag);
            fillers.put(tag, filler);
        }
        return filler;
    }
    
    private final static String fill(final String in, final String tag, final Object val) {
        return getFiller(tag).fill(in, val);
    }
    
    public final static void setDefaultMax(final Integer defaultMax) {
        InfoMan.defaultMax = defaultMax;
    }
    
    private final static Integer initDefaultMax() {
        final String propMax = Util.getProperty("org.regenstrief.ohie.InfoMan.defaultMax");
        return Util.isEmpty(propMax) ? null : Integer.valueOf(propMax);
    }
    
    public NodeList getFacilities(final FacilityArgs args) throws Exception {
        return invokeXml(getFacilitySearch(args), "facility");
    }
    
    private final String getFacilitySearch(final FacilityArgs args) throws Exception {
        if (TMP_FACILITY_SEARCH == null) {
            TMP_FACILITY_SEARCH = Util.readFile("org/regenstrief/ohie/FacilitySearch.xml");
        }
        String req = fill(TMP_FACILITY_SEARCH, "primaryName", args.primaryName);
        req = fill(req, "max", String.valueOf(args.getMax()));
        return req;
    }
    
    public NodeList getProviders(final ProviderArgs args) throws Exception {
        return invokeXml(getProviderSearch(args), "provider");
    }
    
    private final String getProviderSearch(final ProviderArgs args) throws Exception {
        if (TMP_PROVIDER_SEARCH == null) {
            TMP_PROVIDER_SEARCH = Util.readFile("org/regenstrief/ohie/ProviderSearch.xml");
        }
        String req = fill(TMP_PROVIDER_SEARCH, "commonName", args.commonName);
        req = fill(req, "organizations/organization/@entityID", args.organization);
        req = fill(req, "facilities/facility/@entityID", args.facility);
        req = fill(req, "codedType/@code;codingScheme", args.type);
        req = fill(req, "max", args.getMax());
        return req;
    }
    
    private NodeList invokeXml(String req, final String tag) throws Exception {
        req = Util.replaceAllExact(req, "            \n", "");
        req = Util.replaceAllExact(req, "            \r\n", "");
        return XMLUtil.getChildren(XMLUtil.parseXMLFromString(invoke(req)), tag);
    }
    
    private String invoke(final String req) throws Exception {
        log.info("Sending to " + URL + "\n" + req);
        final URLConnection ucon = new URL(URL).openConnection();
        ucon.setRequestProperty("Accept", "text/xml");
        ucon.setRequestProperty("Accept-Charset", "utf-8");
        ucon.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        ucon.setDoInput(true);
        ucon.setDoOutput(true);
        ucon.getOutputStream().write(req.getBytes());
        final InputStream in = Util.getRawStream(ucon);
        final String rsp;
        try {
            rsp = Util.readStream(in);
        } finally {
            in.close();
        }
        log.info("Received from " + URL + "\n" + rsp);
        return rsp;
    }
}
