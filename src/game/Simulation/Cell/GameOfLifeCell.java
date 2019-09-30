package game.Simulation.Cell;

import game.Simulation.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static game.Simulation.State.*;
import static game.Simulation.State.BURNING;

public class GameOfLifeCell extends Cell {

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state     Initial state of the Cell
     */
    public GameOfLifeCell(State state) {
        super(state);
        setStateList(Arrays.asList(EMPTY, LIVE));
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
