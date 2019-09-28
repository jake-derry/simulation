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
     * @param neighbors
     */
    public GameOfLifeCell(State state, Iterator<Cell> neighbors) {
        super(state, neighbors);
    }

    @Override
    public void updateNext() {
        State nextState;

        if (getState() == EMPTY) {
            if (getCountMap().get(LIVE) == 3) {
                nextState = LIVE;
            } else {
                nextState = EMPTY;
            }

        } else {
            if (getCountMap().get(LIVE) < 2 || getCountMap().get(LIVE) > 3) {
                nextState = EMPTY;
            } else {
                nextState = LIVE;
            }

        }

        setNextState(nextState);
    }
}
