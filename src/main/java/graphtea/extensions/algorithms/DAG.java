// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.LibraryUtils;
import graphtea.platform.lang.Pair;

import java.util.*;


/**
 * this class contains some basic algorithms of Directed Acyclic Graphs
 *
 * @author Azin Azadi
 * @author Omid Aladini
 */
public class DAG extends Algorithm implements AutomatedAlgorithm {
    public static AbstractList<Vertex> doSort(GraphModel graph) {
        ArrayList<Vertex> alv = new ArrayList<>();
        ArrayList<Vertex> out = new ArrayList<>();

        LibraryUtils.falsifyVertexMarks(graph);

        for (Vertex v : graph)
            if (graph.getInDegree(v) == 0) {
                alv.add(v);
                v.setMark(true);
            }

        while (alv.size() != 0) {
            Vertex v = alv.remove(0);
            out.add(v);

            A:
            for (Vertex target : graph.getNeighbors(v)) {
                if (target.getMark())
                    continue;
                for (Vertex src : graph.getBackNeighbours(target)) {
                    if (!src.getMark()) {
                        continue A;
                    }
                }
                target.setMark(true);
                alv.add(target);
            }
        }

        for (Vertex v : graph) {
            if (!v.getMark()) {
                return null; //graph has a loop
            }
        }
        return out;
    }

    /**
     * finds a maximal length path in the given DAG
     *
     * @param graph The given graph
     * @return null if it is not a DAG, else a list of pairs, which ret[i].first is the parent of the longest path
     *         of the vertex with Id i, and ret[i].second is the longest path length.
     */
    public static 
    AbstractList<Pair<Vertex, Integer>>
    findLongestPath(GraphModel graph) {
        int[] maxPath = new int[graph.getVerticesCount()];
        BaseVertex[] parent = new BaseVertex[maxPath.length];
        AbstractList<Vertex> sort = doSort(graph);
        if (sort == null)
            return null;
        //the greedy section
        for (Vertex v : sort) {
            int id = v.getId();
            if (graph.getInDegree(v) == 0) {
                maxPath[id] = 0;
                parent[id] = null;
            } else {
                int max = -1;
                Vertex maxV = null;
                for (Vertex src : graph.getBackNeighbours(v)) {
                    if (max < maxPath[src.getId()] + 1) {
                        max = maxPath[src.getId()] + 1;
                        maxV = src;
                    }
                }
                maxPath[id] = max;
                parent[id] = maxV;
            }
        }
        AbstractList<Pair<Vertex, Integer>> ret = new ArrayList<>(maxPath.length);
        for (int i = 0; i < maxPath.length; i++) {
            ret.add(new Pair<>((Vertex) parent[i], maxPath[i]));
        }
        return ret;
    }

    /**
     *
     * @return the list of vertices which can be visited by starting a traverse from root
     */
    public static 
    List<Vertex>
    getTraversableSubGraph(GraphModel graph, Vertex root){
        LibraryUtils.falsifyVertexMarks(graph);
        List<Vertex> ret= new ArrayList<>();
        Queue<Vertex> q = new LinkedList<>();
        q.add(root);
        root.setMark(true);
        while (!q.isEmpty()){
            Vertex v = q.poll();
            ret.add(v);
            for (Vertex nv:graph.getNeighbors(v)){
                if (!nv.getMark()){
                    q.add(nv);
                    nv.setMark(true);
                }
            }
        }
        return ret;
    }
    /**
     * This method returns a cycle in the given2t directed graph which is a proof that it is not a DAG,
     *
     * @param graph The input graph
     * @return a cycle in the graph if it is NOT a dag or null if it is a dag
     */
    public static 
    LinkedList<Vertex>
    findACycle(GraphModel graph) {
        byte[] mark = new byte[graph.getVerticesCount()];
        BaseVertex[] V = graph.getVertexArray();
        BaseVertex[] parent = new BaseVertex[mark.length];
        BaseVertex[] root = new BaseVertex[mark.length];
        Integer[] dfsNum = new Integer[mark.length];
        LibraryUtils.falsifyVertexMarks(graph);

        //start from one vertex and go to  it's neighbours [BFS], continue until you meet a MARKed vertex
        HashSet<Vertex> visitedVertices = new HashSet<>();
        LinkedList<Vertex> bfsStack = new LinkedList<>();
        Vertex cycleStart = null;
        Vertex cycleEnd = null;

//        visitedVertices.add(current);
        int scan = 0;
        int counter = 0;
        Vertex current;
        //perform a nonrecursive dfs
        for (scan = 0; scan < V.length; scan++) {
            Vertex u = (Vertex) V[scan];
            if (!u.getMark()) { /* Start a new search from u */
                bfsStack.push(u);
                root[u.getId()] = u;
                while (!bfsStack.isEmpty()) {
                    Vertex v = bfsStack.pop();
                    if (!v.getMark()) {
                        v.setMark(true);
                        dfsNum[v.getId()] = counter;
                        counter++;
                        for (Vertex w : graph.getNeighbors(v))
                            if (!w.getMark()) {
                                bfsStack.push(w);
                                parent[w.getId()] = v;
                                root[w.getId()] = u;
                            }
                    }
                }
            }

        }

        //try to fing backedges
        for (Edge e : graph.edges()) {
            int src = e.source.getId();
            int trg = e.target.getId();
            if (root[src] == root[trg] && dfsNum[src] > dfsNum[trg]) {
                cycleStart = e.target;
                cycleEnd = e.source;


                current = cycleEnd;
                LinkedList<Vertex> ret = new LinkedList<>();
                ret.addFirst(current);
                while (current != null && current != cycleStart) {
                    current = (Vertex) parent[current.getId()];
                    ret.addFirst(current);
                }
                if (current != null)
                    return ret;

            }
        }
        return null;
//        A:
//        while (true) {
//            if (bfsStack.isEmpty()) {
//                for (; scan < mark.length; scan++) {
//                    bfsStack.add((Vertex) V[scan]);
//                    scan++;
//                    visitedVertices.clear();
//                    LibraryUtils.falsifyVertexMarks(graph);
//                    break;
//                }
//            }
//            if (bfsStack.isEmpty())  //no unmarked vertex
//                return null;
//            current = bfsStack.pop();
//            current.setMark(true);
//            for (Vertex trg : graph.getNeighbors(current)) {
//                if (!trg.getMark()) {
//                    bfsStack.addLast(trg);
//                    parent[trg.getId()] = current;
//                } else {
//                    if (visitedVertices.contains(trg)) {
//                        cycleStart = trg;
//                        cycleEnd = current;
//                        break A;
//                    }
//                }
//            }
//        }

        //extract from parents, the path between the cycle start and cycle end

//        return ret;

    }

    /**
     * finds all paths in the given DAG from src to trg.
     */
    public static 
    Vector<Stack<Vertex>> findAllPaths(GraphModel dag, Vertex src, Vertex trg) {
        Vector<Stack<Vertex>> ret = new Vector<>();
        findAllPathsRec(dag, src, trg, ret, new Stack<>());
        return ret;
    }

    private static 
    void findAllPathsRec(GraphModel graph, Vertex src, Vertex trg, Vector<Stack<Vertex>> ret, Stack<Vertex> currentPath) {
        currentPath.push(src);
        if (src == trg) {
            ret.add((Stack<Vertex>) currentPath.clone());
        } else {
            for (Vertex v : graph.getNeighbors(src)) {
                findAllPathsRec(graph, v, trg, ret, currentPath);
            }
        }
        currentPath.pop();
    }

    /**
     * finds all ancestors of src in the given DAG.
     * closer ancestors to src come first in returned vector
     */
    public static 
    Vector<Vertex> findAllAncestors(GraphModel dag, Vertex src) {
        Vector<Vertex> ret = new Vector<>();
        Queue<Vertex> q = new LinkedList<>();
        q.add(src);
        while (!q.isEmpty()) {
            Vertex cur = q.poll();
            for (Vertex anc : dag.getBackNeighbours(cur)) {
                q.add(anc);
                ret.add(anc);
            }
        }
        return ret;
    }

    public void doAlgorithm() {
//        BaseGraphRequest gr = new BaseGraphRequest();
//        dispatchEvent(gr);
//        GraphModel graph = gr.getGraph();
//        AbstractList<BaseVertex> alv = doSort(graph);
//        if (alv == null)
//            dispatchEvent(new MessageEvent("Graph has a cycle"));
//        else {
//            String s = "Topological sort sequence:";
//            for (BaseVertex v : alv)
//                s += v + ", ";
//
//            dispatchEvent(new MessageEvent(s));
//        }

    }


}

