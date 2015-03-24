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
package org.regenstrief.util.criterion;

import org.regenstrief.util.XMLUtil;
import org.w3c.dom.Node;

/**
 * <p>
 * Title: NodeTypeCriterion
 * </p>
 * <p>
 * Description: A Criterion that tests whether Objects are assignable from a Class
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class NodeTypeCriterion implements Criterion {
    
    private final static NodeTypeCriterion element = new NodeTypeCriterion(Node.ELEMENT_NODE);
    
    private final static NodeTypeCriterion attribute = new NodeTypeCriterion(Node.ATTRIBUTE_NODE);
    
    private final short nodeType;
    
    /**
     * Constructs a new NodeTypeCriterion for the given node type
     * 
     * @param nodeType the node type
     **/
    private NodeTypeCriterion(final short nodeType) {
        this.nodeType = nodeType;
    }
    
    /**
     * Retrieves an NodeTypeCriterion for the given node type
     * 
     * @param nodeType the node type
     * @return the NodeTypeCriterion
     **/
    public final static NodeTypeCriterion getInstance(final short nodeType) {
        switch (nodeType) {
            case Node.ELEMENT_NODE:
                return element;
            case Node.ATTRIBUTE_NODE:
                return attribute;
        }
        return new NodeTypeCriterion(nodeType); // Could cache these
    }
    
    /**
     * Retrieves whether the Criterion is met (the Object is a Node of the Criterion's specified
     * type)
     * 
     * @param o the Object to test
     * @return whether the Criterion is met
     **/
    @Override
    public boolean isMet(final Object o) {
        return !(o instanceof Node) ? false : ((Node) o).getNodeType() == this.nodeType;
    }
    
    @Override
    public String toString() {
        return "nodeType == " + XMLUtil.toString(this.nodeType);
    }
}
