// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algs;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.genericcloners.BaseEdgeVertexCopier;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * This Algorithm computes the length of the shortest
 * path between any two arbitrary vertices.
 * This method is usually used for sparse graphs.
 *
 * @author Soroush Sabet
 * @author M. Ali Rostami
 */
public class Johnson extends GraphAlgorithm {

    int[] a;
    int[][] d;

    public Johnson(BlackBoard blackBoard) {
        super(blackBoard);
    }

    public int[][] ComputePaths(GraphModel g) {

        BaseEdgeVertexCopier gc = new BaseEdgeVertexCopier();
        Vertex u;
        BaseEdge e;

        u = (Vertex) gc.convert(null);
        g.insertVertex(u);
        for (Vertex v : g) {

            if (v != u) {
                e = gc.convert(null, u, v);
                g.insertEdge((Edge) e);
                e.setWeight(0);
            }

        }
        BellmanFord sp = new BellmanFord(blackBoard);

        if (sp.computePaths(g, u) != null) {
            Vector<Vertex> pd = sp.computePaths(g, u);
            for (Vertex v : g) {

                int dd = 0;
                Edge h;
                while (v != u) {
                    h = getSingleEdge(g, pd.get(v.getId()), v);
                    dd += h.getWeight();
                }
                a[v.getId()] = dd;

            }
            Iterator<Edge> iet = g.edgeIterator();
            Edge h;
            while (iet.hasNext()) {
                h = iet.next();
                int w = h.getWeight() + a[h.source.getId()] - a[h.target.getId()];
                //nw.put(h,w);
                h.setWeight(w);
            }

            for (Vertex v : g) {
                Dijkstra dj = new Dijkstra();
                Vector<Vertex> pdj = dj.getShortestPath(g, v);
                for (Vertex z : g) {
                    int dd = 0;
                    Edge f;
                    while (z != v) {
                        f = getSingleEdge(g, pdj.get(z.getId()), z);
                        dd += f.getWeight();
                    }
                    d[v.getId()][z.getId()] = dd + a[z.getId()] - a[v.getId()];

                }
            }

        }


        return new int[0][];
    }

    private Edge getSingleEdge(GraphModel g, Vertex v, Vertex w) {
        Collection<Edge> f;
        Edge h;
        f = g.getEdges(v, w);
        h = f.iterator().next();
        return h;
    }

}
