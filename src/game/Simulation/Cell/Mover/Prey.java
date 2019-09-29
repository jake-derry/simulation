package game.Simulation.Cell.Mover;

import game.Simulation.State;

public class Prey implements Mover {
    private final int myBreedTime;
    private int clock;
    private boolean living;

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

    public boolean isLiving() {
        return living;
    }

    public void kill() {
        living = false;
    }

    @Override
    public State moverState() {
        return State.PREY;
    }
}
