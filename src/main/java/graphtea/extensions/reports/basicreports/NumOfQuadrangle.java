// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_quadrangle", abbreviation = "_noqa")
public class NumOfQuadrangle implements GraphReportExtension<Integer> {
    public String getName() {
        return "number of quadrangle";
    }

    public String getDescription() {
        return "number of quadrangle";
    }

    public Integer calculate(GraphModel g) {
        return getNumOfQuadrangles(g);
    }

    /**
     * @return number of quadrangles in the given graph
     */
    public static int getNumOfQuadrangles(GraphModel graph) {
        int quadrangles = 0;
        for (Vertex i : graph)
            for (Vertex j : graph.directNeighbors(i))
                for (Vertex k : graph.directNeighbors(j))
                    for (Vertex l : graph.directNeighbors(k))
                        if (l != j &&
                                l != i &&
                                k != i &&
                                graph.isEdge(i, l)) {
                            quadrangles++;
                        }

        return quadrangles / 8;
    }

	@Override
	public String getCategory() {
		return "General";
	}
}
