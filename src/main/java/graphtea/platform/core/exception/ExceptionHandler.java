// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.core.exception;

import graphtea.platform.core.BlackBoard;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

    BlackBoard blackBoard;

    public ExceptionHandler(BlackBoard bb) {
        super();
        blackBoard = bb;
    }

    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Exception Occurred: " + e.toString());
        e.printStackTrace();
    }

    public static void catchException(Exception e) {
        e.printStackTrace();
    }
}
