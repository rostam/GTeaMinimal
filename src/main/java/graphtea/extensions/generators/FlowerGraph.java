// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

/**
 * Author: Mohsen Khaki
 * 
 */
@CommandAttitude(name = "generate_flower", abbreviation = "_g_flower", description = "generates a Helm graph of order n")
public class FlowerGraph implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
	@Parameter(name = "n")
	public static int n = 3;

	public String getName()
	{
		return "Flower Graph";
	}

	public String getDescription()
	{
		return "Generate Flower Graph";
	}

	Vertex[] v;

	public Vertex[] getVertices()
	{
		Vertex[] result = new Vertex[2*n+1];
		for (int i = 0; i < 2*n+1; i++)
			result[i] = new Vertex();
		v = result;
		return result;
	}

	public Edge[] getEdges() {
		Edge[] result =  new Edge[4*n];
        int ecnt = 0;
		for (int i = 0; i < n; i++) {
			result[ecnt] = new Edge(v[i], v[n+i]);
            ecnt++;
			result[ecnt] = new Edge(v[n+i], v[2*n]);
            ecnt++;
			result[ecnt] = new Edge(v[n+i],v[n+((i+1)%n)]);
            ecnt++;
            result[ecnt] = new Edge(v[i],v[2*n]);
            ecnt++;
		}

        return result;
	}

	public GPoint[] getVertexPositions()
	{
		int w = 1000;
		double mw = ((double)w)/2.0, qw = ((double)w)/4.0;
		GPoint[] result = new GPoint[2*n+1];
		result[2*n] = new GPoint(w/2, w/2);
		double ang = Math.PI*2.0/n;
		double offset = 0.0;
		if ((n % 2) == 0)
			offset = ang/2.0; 
		for ( int i = 0 ; i < n ; i++ )
		{
			double angle = offset + i * ang;
            double angle2 = angle + ang/6;
			result[i] = new GPoint((int)(mw + Math.sin(angle2)* mw), (int)(mw - Math.cos(angle2)* mw));
			result[n+i] = new GPoint((int)(mw + Math.sin(angle)* qw), (int)(mw - Math.cos(angle)* qw));
		}
		return result;
	}

	public String checkParameters(){
		if( n<3)return "n must be higher than 2!";
		else
			return null;
	}

	public GraphModel generateGraph()
	{
		return GraphGenerator.getGraph(false, this);
	}

	/**
	 * generates a Flower Graph with given parameters
	 */
	public static GraphModel generateHelm(int n)
	{
		FlowerGraph.n = n;
		return GraphGenerator.getGraph(false, new FlowerGraph());
    }

    @Override
    public String getCategory() {
        return "Web Class Graphs";
    }
}