// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.event.AlgorithmStep;
import graphtea.library.event.Event;
import graphtea.library.event.EventDispatcher;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.algorithmanimator.core.atoms.*;

import java.util.Vector;

/**
 * Actually the class which animates the algorithms!
 *
 * @author Azin Azadi
 */
public class AlgorithmAnimator implements EventDispatcher {
    static Vector<AtomAnimator<Event>> animators = new Vector<>();
    BlackBoard blackboard;
    private boolean paused = true;
    /**
     * running algorithm just one step
     */
    private boolean oneStep;

    public AlgorithmAnimator(BlackBoard blackboard) {
        this.blackboard = blackboard;
        registerAtomAnimation(new GraphSelect());
        registerAtomAnimation(new PrePostWork());
        registerAtomAnimation(new DelayEventHandler());
    }

    /**
     * registers a new kind of AtomAnimator
     *
     * @param a The nimator
     */
    static public void registerAtomAnimation(AtomAnimator a) {
        animators.add(a);
    }

    public Event animateEvent(Event ae) {
        for (AtomAnimator<Event> animator : animators)
            if (animator.isAnimatable(ae)) {
                return animator.animate(ae, blackboard);
            }
        return ae;
    }

    /**
     * The main method, Animates an algorithm
     *
     * @param aa The automated algorithm
     */
    public void animateAlgorithm(final AutomatedAlgorithm aa) {
        new Thread(() -> {
            GraphModel g = blackboard.getData(GraphAttrSet.name);
            boolean b = g.isShowChangesOnView();
            g.setShowChangesOnView(true);
            aa.acceptEventDispatcher(AlgorithmAnimator.this);
            aa.doAlgorithm();
            g.setShowChangesOnView(b);
        }).start();

    }

    /**
     * dispatchs events that recieved from the algorithm
     *
     * @param event The event
     * @return The event
     */
    public Event dispatchEvent(Event event) {
        try {

            if (event instanceof AlgorithmStep) {
                if (!oneStep) {
                    double s = 100.0;
                    Thread.sleep((long) (10 * s));
                }
                if (oneStep) {
                    paused = true;
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Thread sleep has error.");
        }



        Event output = animateEvent(event);

        if (oneStep && event instanceof AlgorithmStep) {
            oneStep = false;
        }
        while (paused)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                ExceptionHandler.catchException(e);
            }

        return output;
    }

}