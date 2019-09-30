package game.Simulation.Cell.Agent;

import game.Simulation.State;

public abstract class Mover {

    /**
     *
     * @return
     */
    abstract public boolean readyToBreed();

    /**
     *
     * @return
     */
    abstract public Mover offspring();

    /**
     *
     */
    abstract public void step();

    /**
     *
     * @return
     */
    abstract public boolean isLiving();

    /**
     *
     * @return
     */
    abstract public State moverState();

    /**
     *
     */
    abstract public void kill();
}
