package game.Simulation.Cell;

import game.Simulation.State;

import static game.Simulation.State.EMPTY;
import static game.Simulation.State.WATER;

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
    public void updateNext() {
        if (getState() == EMPTY && getCountMap().get(WATER) > 0) {
            setNextState(WATER);
        }
        setNextState(getState());
    }
}
