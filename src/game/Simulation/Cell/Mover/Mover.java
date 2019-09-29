package game.Simulation.Cell.Mover;

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
}
