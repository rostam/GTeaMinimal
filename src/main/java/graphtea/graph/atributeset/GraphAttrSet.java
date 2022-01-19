// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.attribute.NotifiableAttributeSetImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * this class provides a way to have a Graph object as a NotifiableAttributeSet
 * this is useful whenever some one wants to work blindly with graph attributes
 * for example on saving graph to a gml xml file it is important to have all attributes
 * saved, the meaning of values of attributes is not important, or when a property editor
 * wants to show and edit the attributes of graph to the user, at that time it can use
 * a XAttribute to have better looks see GraphPropertyEditor class as an example.
 * <p/>
 * An other usage of this class is whenever some one wants to listen to changes of
 * a user defined or a rare attribute which normally has no listening capability,
 * for example when you want to change the program according to Graph ID whenever it
 * changes. ID attribute on graph has not a formal listening way, so this class is usefull
 * at that time.
 *
 * @author Hooman Mohajeri Moghaddam - added image file for background.
 * @author Azin Azadi
 */
public class GraphAttrSet implements AttributeSet {

    GraphModel g;
    NotifiableAttributeSetImpl atrs = new NotifiableAttributeSetImpl();

    public static final String EDGEDEFAULT = "edgedefault";
    public static final String EDGEDEFAULT_DIRECTED = "directed";
    public static final String EDGEDEFAULT_UNDIRECTED = "undirected";

    //    public static final String ID = "id";
    public static final String DIRECTED = "directed";
    public static final String LABEL = "label";
    public static final String ZOOM = "Zoom";
    public static final String FONT = "Font";
    public static final String DRAW_VERTEX_LABELS = "Vertex Labels";
    public static final String DRAW_EDGE_LABELS = "Edge Labels";
    public static final String IS_EDGES_CURVED = "Curved Edges";
    public static final String BACKGROUND_IMAGE = "Background";
    public static final String Allow_Loops = "Allow Loops";

    //****//
    //    public static final String CENTERX = "centerx";
    //    public static final String CENTERY = "centery";
    public static final String name = "Graph.GraphModel";

    public Map<String, Object> getAttrs() {
        Map<String, Object> ret = new HashMap<>();
        ret.put(DRAW_VERTEX_LABELS, g.isDrawVertexLabels());
        ret.put(DRAW_EDGE_LABELS, g.isDrawEdgeLabels());
        ret.put(IS_EDGES_CURVED, g.isEdgesCurved());
        ret.put(Allow_Loops, g.isAllowLoops());
        ret.put(DIRECTED, g.isDirected() ? EDGEDEFAULT_DIRECTED : EDGEDEFAULT_UNDIRECTED);
        ret.put(LABEL, g.getLabel());

        if (g.getUserDefinedAttributes() != null)
            ret.putAll(g.getUserDefinedAttributes());
        return ret;
    }

    public void put(String atrName, Object val) {
        switch (atrName) {
            case LABEL:
                g.setLabel((String) val);
                break;

            case DRAW_VERTEX_LABELS:
                g.setDrawVertexLabels((Boolean) val);
                break;
            case IS_EDGES_CURVED:
                g.setIsEdgesCurved((Boolean) val);
                break;

            case Allow_Loops:
                g.setAllowLoops((Boolean) val);
                break;
            case DRAW_EDGE_LABELS:
                g.setDrawEdgeLabels((Boolean) val);
                break;
            default:
                g.setUserDefinedAttribute(atrName, val);
                break;
        }

    }

    public Object get(String atrName) {
        Object ret = null;
        switch (atrName) {
            case LABEL:
                ret = g.getLabel();
                break;
            case DIRECTED:
                ret = g.isDirected();
                break;

            case DRAW_VERTEX_LABELS:
                ret = g.isDrawVertexLabels();
                break;
            case IS_EDGES_CURVED:
                ret = g.isEdgesCurved();
                break;

            case DRAW_EDGE_LABELS:
                ret = g.isDrawEdgeLabels();
                break;
            case Allow_Loops:
                ret = g.isAllowLoops();
                break;
            default:
                ret = g.getUserDefinedAttribute(atrName);
                break;
        }
        return ret;
    }

    public GraphAttrSet(GraphModel g) {
        this.g = g;
    }
}
