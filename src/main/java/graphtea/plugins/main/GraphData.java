// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.library.algorithms.LibraryUtils;
import graphtea.platform.core.BlackBoard;

/**
 * This class provides useful information and methods all in one place
 * @author azin azadi
 */
public class GraphData {
    BlackBoard blackboard;
    AlgorithmUtils algorithmUtils = new AlgorithmUtils();
    LibraryUtils libraryUtils = new LibraryUtils();
//    public RightClickPluginMethods rightclick;
//    public ReporterPluginMethods browser;
//    public PreviewPluginMethods preview;
    //    public HelpPluginMethods help;

    public GraphData(BlackBoard blackboard) {
        this.blackboard = blackboard;

//        rightclick = new RightClickPluginMethods();
//        browser = new ReporterPluginMethods();
//        preview = new PreviewPluginMethods();
//        help = new HelpPluginMethods(blackboard);

    }

    /**
     * @return returns the current graph
     */
    public GraphModel getGraph() {
        return blackboard.getData(GraphAttrSet.name);
    }

    public BlackBoard getBlackboard() {
        return blackboard;
    }

}