package game.Simulation.Cell;

import game.Simulation.State;

/**
 * Cell class that implements the Rock, Paper, Scissors
 * simulation.
 */
public class RPSCell extends Cell{

    /**
     * Threshold at which a cell becomes the type that
     * beats it.
     */
    private int myThreshold;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state Initial state of the Cell
     */
    public RPSCell(State state, int threshold) {
        super(state);
        myThreshold = threshold;
    }

    @Override
    public void updateNext() {
        RPSState state = RPSState.find(getState());

        if (getCountMap().get(state.beatBy()) >= myThreshold) {
            setNextState(state.beatBy());
        }
    }
}
