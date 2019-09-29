package game.Simulation.Cell;

import game.Simulation.State;

public class PercolationCell extends Cell{

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state Initial state of the Cell
     */
    public PercolationCell(State state) {
        super(state);
    }

    @Override
    public State updateNext() {
        if (getState() == State.EMPTY && getCountMap().get(State.WATER) > 0) {
            return State.WATER;
        }
        return getState();
    }
}
