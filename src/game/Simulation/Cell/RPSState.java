package game.Simulation.Cell;

import game.Simulation.State;

/**
 * Enumerated type that outlines the states
 * involved in Rock, Paper, Scissors simulations. Allows
 * more information to be stored about them like which
 * State beats which State.
 */
public enum RPSState {
    ROCK(State.ROCK, State.PAPER),
    PAPER(State.PAPER, State.SCISSORS),
    SCISSORS(State.SCISSORS, State.ROCK);

    /**
     * State that corresponds to the RPSState
     */
    private State state;

    /**
     * State that the RPSState is beat by.
     */
    private State beatBy;

    RPSState(State rpsState, State stateBeatBy) {
        state = rpsState;
        beatBy = stateBeatBy;
    }

    /**
     * Get who an RPSState is beat by.
     *
     * @return          State who RPSState is beat by
     */
    public State beatBy() {
        return beatBy;
    }

    /**
     * Get state that corresponds to the RPSState
     *
     * @return          State that corresponds to the RPSState
     */
    public State getState() {
        return state;
    }

    /**
     * Based on a state, finds the RPSState connected to it.
     *
     * @param state     State to be found
     * @return          RPSState that corresponds to it
     */
    public static RPSState find(State state) {
        for (RPSState rpsState : RPSState.values()) {
            if (rpsState.getState() == state) {
                return rpsState;
            }
        }
        return null;
    }
}
