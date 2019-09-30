package game.Simulation.Cell;

import game.Simulation.State;

import java.util.Arrays;

import static game.Simulation.State.EMPTY;
import static game.Simulation.State.WALL;
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
        setStateList(Arrays.asList(EMPTY, WALL, WATER));
    }

    @Override
    public void updateNext() {
        if (getState() == EMPTY && getCountMap().containsKey(WATER) && getCountMap().get(WATER) > 0) {
            setNextState(WATER);
        }
        else{
            setNextState(getState());
        }

    }
}
