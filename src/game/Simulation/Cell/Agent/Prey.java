package game.Simulation.Cell.Agent;

import game.Simulation.State;

/**
 *
 */
public class Prey implements Mover {
    private final int myBreedTime;
    private int clock;
    private boolean living;

    /**
     * Constructor for Prey. Initializes the clock that counts the time
     * that determines when the Prey produces offspring at the breed time
     * which is also initialized in the constructor.
     *
     * @param breedTime             Time at which the prey will produce an
     *                              offspring
     */
    public Prey(int breedTime) {
        myBreedTime = breedTime;
        clock = 0;
    }

    @Override
    public void step () {
        clock++;
    }

    @Override
    public Prey offspring() {
        clock = 0;
        return new Prey(myBreedTime);
    }

    @Override
    public boolean readyToBreed() {
        return clock >= myBreedTime;
    }

    @Override
    public boolean isLiving() {
        return living;
    }

    @Override
    public State moverState() {
        return State.PREY;
    }

    @Override
    public void kill() {
        living = false;
    }
}
