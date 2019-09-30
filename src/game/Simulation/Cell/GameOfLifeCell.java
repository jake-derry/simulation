package game.Simulation.Cell;

import game.Simulation.State;

import java.util.Iterator;

import static game.Simulation.State.EMPTY;
import static game.Simulation.State.LIVE;

public class GameOfLifeCell extends Cell {

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state     Initial state of the Cell
     */
    public GameOfLifeCell(State state) {
        super(state);
    }

    @Override
    public void updateNext() {
        if (getState() == EMPTY) {
            if (getCountMap().containsKey(LIVE) && getCountMap().get(LIVE) == 3) {
                setNextState(LIVE);
            } else {
                setNextState(EMPTY);
            }

        } else {
            if (getCountMap().containsKey(LIVE) && (getCountMap().get(LIVE) == 2 || getCountMap().get(LIVE) == 3)) {
                setNextState(LIVE);
            } else {
                setNextState(EMPTY);
            }

        }
    }
}
