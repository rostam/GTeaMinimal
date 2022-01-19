// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.graphgenerator;

import graphtea.extensions.generators.*;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.plugin.PluginMethods;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;

/**
 * @author azin azadi

 */
public class GraphGenerator implements PluginMethods {


    /**
     * generates and return a graph from the given interface, not showing it on GUI
     */
    public static GraphModel getGraph(boolean isDirected, SimpleGeneratorInterface gi) {
        GraphModel ret = new GraphModel(isDirected);
        Vertex[] vertices = gi.getVertices();
        GPoint[] pos = gi.getVertexPositions();
        Edge[] edges = gi.getEdges();
        for (Vertex v : vertices)
            ret.insertVertex(v);

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setLocation(new GPoint(pos[i].x, pos[i].y));
        }

        for (Edge e : edges) {
            ret.insertEdge(e);
        }

        return ret;
    }

    //________________    Graph Generators    _______________
    /**
     * @see CircleGenerator#generateCircle(int)
     */
    public static GraphModel generateCircle(int n) {
        return CircleGenerator.generateCircle(n);
    }

    /**
     * @see CmnGenerator#generateCmn(int,int)
     */
    public static GraphModel generateCmn(int m, int n) {
        return CmnGenerator.generateCmn(m, n);
    }

    /**
     * @see GeneralizedPetersonGenerator#generateGeneralizedPeterson(int,int)
     */
    public static GraphModel generateGeneralizedPeterson(int n, int k) {
        return GeneralizedPetersonGenerator.generateGeneralizedPeterson(n, k);
    }

    /**
     * @see CompleteGraphGenerator#generateCompleteGraph(int)
     */
    public static GraphModel generateCompleteGraph(int n) {
        return CompleteGraphGenerator.generateCompleteGraph(n);
    }

    /**
     * @see KndKneserGraphGenerator#generateKenserGraph(int,int)
     */
    public static GraphModel generateKenserGraph(int n, int d) {
        return KndKneserGraphGenerator.generateKenserGraph(n, d);
    }


    /**
     * @see KmnGenerator#generateKmn(int,int)
     */
    public static GraphModel generateKmn(int m, int n) {
        return KmnGenerator.generateKmn(m, n);
    }

    /**
     * @see PathGenerator#generatePath(int)
     */
    public static GraphModel generatePath(int n) {
        return PathGenerator.generatePath(n);
    }

    /**
     * @see PmnGenerator#generatePmn(int,int)
     */
    public static GraphModel generatePmn(int m, int n) {
        return PmnGenerator.generatePmn(m, n);
    }

    /**
     * @see RandomGenerator#generateRandomGraph(int,int)
     */
    public static GraphModel generateRandomGraph(int n, int e) {
        return RandomGenerator.generateRandomGraph(n, e);
    }

    /**
     * @see StarGenerator#generateStar(int)
     */
    public static GraphModel generateStar(int n) {
        return StarGenerator.generateStar(n);
    }

    /**
     * @see TreeGenerator#generateTree(int,int)
     */
    public static GraphModel generateTree(int depth, int degree) {
        return TreeGenerator.generateTree(depth, degree);
    }

    /**
     * @see WheelGenerator#generateWheel(int)
     */
    public static GraphModel generateWheel(int n) {
        return WheelGenerator.generateWheel(n);
    }


}

