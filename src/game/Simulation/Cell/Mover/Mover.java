package game.Simulation.Cell.Mover;

import game.Simulation.State;

public interface Mover {

    /**
     *
     * @return
     */
    public boolean readyToBreed();

    /**
     *
     * @return
     */
    public Mover offspring();

    /**
     *
     */
    public void step();

    /**
     *
     * @return
     */
    public boolean isLiving();

    /**
     *
     * @return
     */
    public State moverState();

    /**
     *
     */
    public void kill();
}
