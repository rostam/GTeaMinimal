// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.graph;

import graphtea.library.ListGraph;
import graphtea.library.exceptions.InvalidEdgeException;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.lang.ArrayX;
import java.io.File;
import java.util.*;


/**
 * @author Hooman Mohajeri Moghaddam - added image file for background.
 * @author Azin Azadi,roozbeh ebrahimi
 */

public class GraphModel extends ListGraph<Vertex, Edge> {

	public static boolean showEdgeWeights = false;
	public static boolean vertexLabelsEnabled = true;
	private boolean drawVertexLabels = vertexLabelsEnabled;
	private boolean drawEdgeLabels = showEdgeWeights;
	private ArrayX<String> zoom = new ArrayX<>("100%", "10%", "25%", "50%", "75%", "100%", "150%", "200%", "400%");
	private File backgroundImageFile=new File("");


	/**
	 * a number which is constructed from zoom, (150% -> 1.5)
	 */
	private double zoomFactor = 1;
    public static boolean allowLoopsProperty = false;
    private boolean allowLoops = allowLoopsProperty;

    public GraphModel createEmptyGraph() {
		return new GraphModel(isDirected());
	}

	static final int VERTEX_ADDED_GRAPH_CHANGE = 0;
	static final int VERTEX_REMOVED_GRAPH_CHANGE = 1;
	static final int EDGE_ADDED_GRAPH_CHANGE = 2;
	static final int EDGE_REMOVED_GRAPH_CHANGE = 3;
	static final int GRAPH_CLEARED_GRAPH_CHANGE = 4;
	static final int REPAINT_GRAPH_GRAPH_CHANGE = 5;

	boolean showChangesOnView;
	private String label;
	public static final Color[] color =
            {Color.gray, Color.white
                    , Color.yellow, Color.green, Color.magenta, Color.red, Color.cyan, Color.pink, Color.lightGray, Color.darkGray};

    private int lastUsedID = 0;
	/**
	 * It is true if the edges of this graph are curves (not line),
	 * This will be applied to all edges of graph
	 */
	public boolean isEdgesCurved;

	/**
	 * generates an undirected graph
	 */
	public GraphModel() {
		super(true, 20);
		showChangesOnView = false;
		drawVertexLabels = vertexLabelsEnabled;
	}

	public GraphModel(boolean isdirected) {
		super(isdirected, 20);
		showChangesOnView = false;
		drawVertexLabels = vertexLabelsEnabled;
	}


	//________________________   + Userdefined Attributes    _________________________________
	/**
	 * This is a place to put custom attributes in the graph, It will be shown in property editor and editable
	 */
	private HashMap<String, Object> userDefinedAttributes = null;

	/**
	 * these attributed will be added to each graph's userDefinedAttributes on constructing time.
	 */
	private static HashMap<String, Object> globalUserDefinedAttributes = null;


	/**
	 * sets and stores a user defined attribute for the graph. here you can put any attribute you like that are not available
	 * in the standard attributes. your attributes will be editable in property editor part of GUI.
	 *
	 * @param name The attribute name
	 * @param value The attribute value
	 */
	public void setUserDefinedAttribute(String name, Object value) {
		if (userDefinedAttributes == null) {
			userDefinedAttributes = new HashMap<>();
		}
		userDefinedAttributes.put(name, value);
	}

	/**
	 * returns the specified user defined attribute, or null if it does not exists.
	 *
	 * @param name The name of the attribute
	 * @return The specific user defined attribute
	 */
	public <t> t getUserDefinedAttribute(String name) {
		if (userDefinedAttributes == null)
			return null;
		return (t) userDefinedAttributes.get(name);
	}

	/**
	 * removes the given attribute from the list of user defined attributes
	 *
	 * @param name The name of the attribute
	 */
	public void removeUserDefinedAttribute(String name) {
		userDefinedAttributes.remove(name);
		if (userDefinedAttributes.size() == 0)
			userDefinedAttributes = null;
	}

	/**
	 * @return a HashMap containing all user defined attributes.
	 */
	public HashMap<String, Object> getUserDefinedAttributes() {
		return userDefinedAttributes;
	}


	/**
	 * sets and stores a global user defined attribute for the graph. this attributes will be added to each graph on
	 * constructing time using setUserDefinedAttribute method.
	 * <p/>
	 * note that this method only affects the afterward created graphs, and current graph will not affected by this method.
	 */
	public static void addGlobalUserDefinedAttribute(String name, Object defaultvalue) {
		if (globalUserDefinedAttributes == null) {
			globalUserDefinedAttributes = new HashMap<>();
		}
		globalUserDefinedAttributes.put(name, defaultvalue);
	}

	/**
	 * @see GraphModel#addGlobalUserDefinedAttribute
	 */
	public static void removeGlobalUserDefinedAttribute(String name) {
		globalUserDefinedAttributes.remove(name);
		if (globalUserDefinedAttributes.size() == 0)
			globalUserDefinedAttributes = null;
	}

	{
		//default constructor
		if (globalUserDefinedAttributes != null) {
			userDefinedAttributes = new HashMap<>();
			userDefinedAttributes.putAll(globalUserDefinedAttributes);
		}
	}
	//________________________   - Userdefined Attributes    _________________________________

	/**
	 * determines whether show changes in model to view, for example when an algorithm changes the color of a vertex
	 * in Vertex(BaseVertex) should a color be assigned in GUI to it or not?
	 *
	 * @param showChangesOnView switch showing the changes on view
	 */
	public void setShowChangesOnView(boolean showChangesOnView) {
		this.showChangesOnView = showChangesOnView;
	}

	public boolean isShowChangesOnView() {
		return showChangesOnView;
	}

	/**
	 * same to insertVertex
	 */
	public void insertVertex(Vertex newVertex) {
		super.insertVertex(newVertex);
        if (newVertex.label == null){
            int nid = newVertex.getId();
            lastUsedID = Math.max(lastUsedID,nid);
            newVertex.label = String.valueOf(lastUsedID++);
        }
	}

	public void insertVertices(Collection<Vertex> vertices) {
		vertices.forEach(this::insertVertex);
	}

	public void removeAllEdges(Vertex source, Vertex target) throws InvalidVertexException {
		super.removeAllEdges(source, target);
	}

	public void removeEdge(Edge edge) throws InvalidEdgeException {
		super.removeEdge(edge);
	}

	public void removeVertex(Vertex v) throws InvalidVertexException {
		Iterator<Edge> it = edgeIterator(v);
		while (it.hasNext()) {
			removeEdge(it.next());

		}
		super.removeVertex(v);
	}

	public void clear() {
		super.clear();
        lastUsedID = 0;
	}

	public Edge getEdge(Vertex src, Vertex trg) {
		Object[] t = null;
		try {
			t = super.getEdges(src, trg).toArray();
		}
		catch (Exception e) {
			ExceptionHandler.catchException(e);
		}
		if (t==null || t.length == 0)
			return null;
		else
			return (Edge) t[0];
	}

	/**
	 * adds new edge only if it doesn't exist in graph
	 *
	 * @param newedge The new edge
	 */
	public void insertEdge(Edge newedge) {
        if (!isAllowLoops() && newedge.source == newedge.target)
            return;
		try {
			if (!isEdge(newedge.source, newedge.target)) {
				super.insertEdge(newedge);
			}
		}
		catch (Exception e) {
			ExceptionHandler.catchException(e);
		}
	}

	public int getEdgesCount() {
		return super.getEdgesCount();
		//        //graph fact: num of edges = 1/2 * sigma(degrees)
		//        int ret = 0;
		//        for (Vertex v : this) {
		//            ret += getInDegree(v);
		//        }
		//        return (int) (ret / (isDirected() ? 1 : 2));
	}

	public void setLabel(String s) {
		this.label = s;
	}

	public String getLabel() {
		return label;
	}

	/**
	 * the standard way to convert simple integers (1,2,3...) to colors
	 */
	private static Color color(int m) {
		return color[m % color.length];
	}

	public void insertEdges(Iterable<Edge> edges) {
		for (Edge edge : edges)
			insertEdge(edge);
	}


	/**
	 * in GraphTea all Colors that assign to Vertices/Edges are in values, so they
	 * can not directly shown with distinct colors, this method gived the standard GraphTea
	 * solution to this which assigns unique colors to 1..20 and if i>20, It will regards the i
	 * itself as the color (new Color(i)) regarding the fact that normally in GraphTheory Colors have
	 * small values.
	 *
	 * @return an RGB color which is representing the given integer Color in GraphTea
	 */
	public static Color getColor(Integer i) {
		Color c;
		if (i == null)
			i = 0;
		if (i < 20 && i >= 0) {
			int ii = i % 20;
			if (ii < 10)
				c = color(i);
			else
				c = color(i).darker();
		} else {
			c = new Color(i);
		}
		return c;
	}

	public boolean isDrawEdgeLabels() {
		return drawEdgeLabels;
	}

	public void setDrawEdgeLabels(boolean drawEdgeLabels) {
		this.drawEdgeLabels = drawEdgeLabels;
	}

	public boolean isDrawVertexLabels() {
		return drawVertexLabels;
	}

	public void setDrawVertexLabels(boolean drawVertexLabels) {
		this.drawVertexLabels = drawVertexLabels;
	}

	/**
	 * @return true if the edges of this graph are curves (not lines),
	 *         This is about all edges of graph
	 */
	public boolean isEdgesCurved() {
		return isEdgesCurved;
	}

	/**
	 * set the edges of this graph to be curves or lines
	 *
	 * @param isCurve Sets if the the edge is curved
	 */
	public void setIsEdgesCurved(boolean isCurve) {
		this.isEdgesCurved = isCurve;
	}

	public void insertVertices(Vertex[] vertices) {
		for (Vertex v : vertices) {
			insertVertex(v);
		}
	}

	public void insertEdges(Edge[] edges) {
		for (Edge e:edges)
			insertEdge(e);
	}


	@Override
	public Vertex[] getVertexArray() {
		Vertex[] arr = new Vertex[getVerticesCount()];

		for (Vertex v : this)
			arr[getId(v)] = v;

		return arr;
	}

	public Edge insertEdge(Vertex src, Vertex trg) {
		Edge ret = new Edge(src, trg);
		insertEdge(ret);
		return ret;
	}

	public boolean isAllowLoops(){
        return allowLoops;
    }
    public void setAllowLoops(boolean allowLoops) {
        this.allowLoops = allowLoops;
    }

	public GraphModel getCopy() {
		GraphModel g = new GraphModel();
		HashMap<Integer, Integer> vTov = new HashMap<>();
		for (Vertex v : this) {
			Vertex w = v.getCopy();

			g.addVertex(w);
			vTov.put(v.getId(), w.getId());
		}

		for (Edge e : this.getEdges()) {
			Vertex src = e.source, tgt = e.target;
			Edge ee = new Edge(g.getVertex(vTov.get(src.getId())),
					g.getVertex(vTov.get(tgt.getId())));
			g.addEdge(ee);
		}

		g.setDirected(false);

		return g;
	}

	public Vector<Vertex> directNeighbors(Vertex v) {
		Iterator<Edge> eit = this.edgeIterator();
		Vector<Vertex> vs = new Vector<>();
		while (eit.hasNext()) {
			Edge e = eit.next();
			if (e.source.getId() == v.getId()) {
				if(!vs.contains(e.target)) {
					vs.add(e.target);
				}
			} else if (e.target.getId() == v.getId()) {
				if(!vs.contains(e.source)) {
					vs.add(e.source);
				}
			}
		}
		return vs;
	}
}
