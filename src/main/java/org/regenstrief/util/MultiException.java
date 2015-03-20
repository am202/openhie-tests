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
package org.regenstrief.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Title: Multi Exception
 * </p>
 * <p>
 * Description: Runtime Exception that wraps multiple Exceptions (or other Throwables). This would
 * typically be used if an Exception occurs during handling of an earlier Exception in a catch
 * block: try { PreparedStatement.executeUpdate(); } catch (Exception e) { try {
 * Connection.rollback(); } catch (Exception e2) { throw new MultiException(e, e2); } }
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class MultiException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private static final StringGetter getMessageGetter = new GetMessageGetter();
    
    private static final StringGetter toStringGetter = new ToStringGetter();
    
    private static final Throwable[] EMPTY_ARRAY_THROWABLE = {};
    
    private final Throwable[] causes;
    
    public final static Throwable createThrowable(final Throwable... causes) {
        final int size = Util.length(causes);
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return causes[0];
        } else {
            return new MultiException(causes);
        }
    }
    
    public final static Exception createException(final Throwable... causes) {
        return Util.toException(createThrowable(causes));
    }
    
    public final static RuntimeException createRuntimeException(final Throwable... causes) {
        return Util.toRuntimeException(createThrowable(causes));
    }
    
    public final static Throwable createThrowable(final Collection<? extends Throwable> causes) {
        return createThrowable(toArray(causes));
    }
    
    public final static Exception createException(final Collection<? extends Throwable> causes) {
        return createException(toArray(causes));
    }
    
    public final static RuntimeException createRuntimeException(final Collection<? extends Throwable> causes) {
        return createRuntimeException(toArray(causes));
    }
    
    private final static Throwable[] toArray(final Collection<? extends Throwable> causes) {
        return causes == null ? null : causes.toArray(EMPTY_ARRAY_THROWABLE);
    }
    
    /**
     * Constructs a new MultiException
     * 
     * @param causes the Throwable[] array of causes
     **/
    public MultiException(final Throwable... causes) {
        final List<Throwable> list = new ArrayList<Throwable>(causes.length);
        
        addCauses(list, causes);
        
        this.causes = list.toArray(new Throwable[0]);
    }
    
    /**
     * Adds causes to an Collection
     * 
     * @param list the Collection
     * @param causes the Throwable[] array of causes
     **/
    private void addCauses(final Collection<Throwable> list, final Throwable[] causes) {
        final int size = Util.length(causes);
        
        for (int i = 0; i < size; i++) {
            final Throwable cause = causes[i];
            if (cause instanceof MultiException) {
                addCauses(list, ((MultiException) cause).getCauses());
            } else {
                list.add(cause);
            }
        }
    }
    
    /**
     * Always returns null, even if MultiException.initCause is invoked, forcing the caller to use a
     * supported method to get anything meaningful
     * 
     * @return null
     **/
    @Override
    public Throwable getCause() {
        return null;
    }
    
    /**
     * Retrieves the Throwable[] array of causes
     * 
     * @return the Throwable[] array of causes
     **/
    public Throwable[] getCauses() {
        return this.causes;
    }
    
    /**
     * Prints the stack trace to the given PrintStream
     * 
     * @param s the PrintStream
     **/
    @Override
    public void printStackTrace(final PrintStream s) {
        printStackTrace(new PrintWriter(s, true));
    }
    
    /**
     * Prints the stack trace to the given PrintWriter
     * 
     * @param w the PrintWriter
     **/
    @Override
    public void printStackTrace(final PrintWriter w) {
        w.println(getClass().getName() + ": Multiple exceptions occurred.");
        for (int i = 0; i < this.causes.length; i++) {
            w.print(i + 1);
            w.print(".) ");
            this.causes[i].printStackTrace(w);
        }
    }
    
    /**
     * <p>
     * Title: String Getter
     * </p>
     * <p>
     * Description: Interface for retrieving a String from a Throwable
     * </p>
     * <p>
     * Copyright: Copyright (c) 2007
     * </p>
     * <p>
     * Company: Regenstrief Institute
     * </p>
     * 
     * @author Andrew Martin
     * @version 1.0
     */
    static interface StringGetter {
        
        /**
         * Retrieves a String from a Throwable
         * 
         * @param e the Throwable
         * @return the String
         **/
        public String getString(Throwable e);
    }
    
    /**
     * <p>
     * Title: Get Message Getter
     * </p>
     * <p>
     * Description: Calls Throwable.getMessge()
     * </p>
     * <p>
     * Copyright: Copyright (c) 2007
     * </p>
     * <p>
     * Company: Regenstrief Institute
     * </p>
     * 
     * @author Andrew Martin
     * @version 1.0
     */
    static class GetMessageGetter implements StringGetter {
        
        /**
         * Retrieves a String from a Throwable
         * 
         * @param e the Throwable
         * @return the String
         **/
        @Override
        public String getString(final Throwable e) {
            return e.getMessage();
        }
    }
    
    /**
     * <p>
     * Title: To String Getter
     * </p>
     * <p>
     * Description: Calls Throwable.toString()
     * </p>
     * <p>
     * Copyright: Copyright (c) 2007
     * </p>
     * <p>
     * Company: Regenstrief Institute
     * </p>
     * 
     * @author Andrew Martin
     * @version 1.0
     */
    static class ToStringGetter implements StringGetter {
        
        /**
         * Retrieves a String from a Throwable
         * 
         * @param e the Throwable
         * @return the String
         **/
        @Override
        public String getString(final Throwable e) {
            return e.toString();
        }
    }
    
    /**
     * Retrieves the concatenation of the getMessage() values of all causes
     * 
     * @return the concatenation of the getMessage() values of all causes
     **/
    @Override
    public String getMessage() {
        return getString(getMessageGetter);
    }
    
    /**
     * Retrieves the concatenation of the toString() values of all causes
     * 
     * @return the concatenation of the toString() values of all causes
     **/
    @Override
    public String toString() {
        return getString(toStringGetter);
    }
    
    /**
     * Retrieves the concatenation of String values of all causes
     * 
     * @param getter retrieves a String value from a single Throwable cause
     * @return the concatenation of String values of all causes
     **/
    private String getString(final StringGetter getter) {
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < this.causes.length; i++) {
            if (i > 0) {
                sb.append("; ");
            }
            sb.append(getter.getString(this.causes[i]));
        }
        
        return sb.toString();
    }
}
