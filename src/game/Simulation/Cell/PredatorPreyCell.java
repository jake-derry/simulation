package game.Simulation.Cell;

import game.Simulation.State;

import java.util.*;

public class PredatorPreyCell extends Cell {
    private Random random;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state Initial state of the Cell
     */
    public PredatorPreyCell(State state) {
        super(state);
        random = new Random();
    }

    @Override
    public void updateNext() {
        if (getState() == State.PREY) {
            chooseNeighbor(new State[] {State.EMPTY}).
                    setNextState(State.PREY);
        } else if (getState() == State.PREDATOR) {
            chooseNeighbor(new State[] {State.EMPTY, State.PREY}).
                    setNextState(State.PREDATOR);
        }
    }
}
