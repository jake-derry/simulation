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
    public State updateNext() {
        if (getState() == EMPTY) {
            if (getCountMap().get(LIVE) == 3) {
                return LIVE;
            } else {
                return EMPTY;
            }

        } else {
            if (getCountMap().get(LIVE) < 2 || getCountMap().get(LIVE) > 3) {
                return EMPTY;
            } else {
                return LIVE;
            }

        }
    }
}
