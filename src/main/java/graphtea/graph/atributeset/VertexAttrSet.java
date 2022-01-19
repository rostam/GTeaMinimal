// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.platform.attribute.AttributeSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Azin Azadi
 * @see GraphAttrSet
 */
public class VertexAttrSet implements AttributeSet<Object> {
    private final Vertex v;
    public static final String LABEL = "Label";
    public static final String COLOR = "Color";
    public static final String SHAPE = "Shape";
    public static final String BORDER = "Border Stroke";
    public static final String LOCATION = "Location";
    public static final String SIZE = "Size";
    public static final String MARK = "Mark";
    public static final String SELECTED = "Selected";
    public static final String LABEL_LOCATION = "Label Location";

    public VertexAttrSet(Vertex v) {
        this.v = v;
    }

    public Map<String, Object> getAttrs() {
        Map<String, Object> ret = new HashMap<>(15);
        ret.put(LABEL, v.getLabel());
        ret.put(COLOR, v.getColor());
        ret.put(LOCATION, v.getLocation());
        ret.put(MARK, v.getMark());
        ret.put(SELECTED, v.isSelected());
        ret.put(LABEL_LOCATION, v.getLabelLocation());
        if (v.getUserDefinedAttributes() != null)
            ret.putAll(v.getUserDefinedAttributes());
        return ret;
    }

    public void put(String atrName, Object val) {
        switch (atrName) {
            case LABEL:
                v.setLabel((String) val);
                break;
            case LOCATION:
                v.setLocation((GPoint) val);
                break;
            case MARK:
                v.setMark((Boolean) val);
                break;
            case SELECTED:
                v.setSelected((Boolean) val);
                break;
            case COLOR:
                v.setColor((Integer) val);
                break;
            case LABEL_LOCATION:
                v.setLabelLocation((GPoint) val);
                break;
            default:
                v.setUserDefinedAttribute(atrName, val);
                break;
        }
    }

    public Object get(String atrName) {
        Object ret = null;
        switch (atrName) {
            case LABEL:
                ret = v.getLabel();
                break;
            case LOCATION:
                ret = v.getLocation();
                break;
            case MARK:
                ret = v.getMark();
                break;
            case SELECTED:
                ret = v.isSelected;
                break;
            case COLOR:
                ret = v.getColor();
                break;
            case LABEL_LOCATION:
                ret = v.getLabelLocation();
                break;
            default:
                ret = v.getUserDefinedAttribute(atrName);
                break;
        }
        return ret;
    }

}