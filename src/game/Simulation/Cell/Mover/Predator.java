package game.Simulation.Cell.Mover;

public class Predator implements Mover {
    private int energy;

    public Predator(int initialEnergy) {
        energy = initialEnergy;
    }


    @Override
    public boolean readyToBreed() {
        return false;
    }

    @Override
    public Mover offspring() {
        return null;
    }
}
