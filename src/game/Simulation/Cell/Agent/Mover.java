package game.Simulation.Cell.Agent;

import game.Simulation.State;

/**
 * An abstract class that organizes the behavior of the
 * predator and prey in a predator-prey simulation.
 */
public interface Mover {

    /**
     * Returns whether a breeding condition is met.
     * Generalizes the different breeding condition that
     * both have.
     *
     * @return          If breeding condition is met
     */
    boolean readyToBreed();

    /**
     * Returns an instance of the mover of the particular
     * class that it is called on.
     *
     * @return          Mover object with the same parameters
     *                  as the mover it is called on.
     */
    Mover offspring();

    /**
     * Alters the condition of the mover which will influence
     * whether it lives or breeds. Called when the mover
     * moves between two cells.
     */
    void step();

    /**
     * Return if the mover is living.
     * @return          If mover is living
     */
    boolean isLiving();

    /**
     * Returns the state associated with a given mover.
     * Used to find the state associated with an implementation
     * while using the Mover interface.
     *
     * @return          State associated with a given mover
     */
    State moverState();

    /**
     * Sets living to false.
     */
    void kill();
}
