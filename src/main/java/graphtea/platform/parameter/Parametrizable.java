// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.parameter;

/**
 * The basic Parametrizable interface. Extensions which implement this interface can have parametric field.
 * which are public fields that user can set them before executing extension.
 *
 * @author azin azadi
 */
//todo rename to Parameterizable
public interface Parametrizable {
    /**
     * checks the parameters and return an error string if the parameter
     * values are invalid, otherwise it returns null,
     * also if some other fields should be set after setting the parameters
     * you can do it in this method
     */
    String checkParameters();
}
