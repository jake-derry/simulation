package game.Simulation.Cell;

import game.Simulation.State;

public class RPSCell extends Cell{
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

        if (getCountMap().containsKey(state.beatBy()) && getCountMap().get(state.beatBy()) > myThreshold) {
            setNextState(state.beatBy());
        }
    }
}
