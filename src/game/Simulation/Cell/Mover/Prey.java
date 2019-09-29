package game.Simulation.Cell.Mover;

public class Prey implements Mover {
    private final int myBreedTime;
    private int clock;
    private boolean living;

    public Prey(int breedTime) {
        myBreedTime = breedTime;
        clock = 0;
    }

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
}
