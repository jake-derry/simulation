package game.Simulation.Cell;

import game.Simulation.State;

public enum RPSState {
    ROCK(State.ROCK, State.PAPER),
    PAPER(State.PAPER, State.SCISSORS),
    SCISSORS(State.SCISSORS, State.ROCK);

    private State state;
    private State beatBy;

    RPSState(State rpsState, State stateBeatBy) {
        state = rpsState;
        beatBy = stateBeatBy;
    }

    public State beatBy() {
        return beatBy;
    }

    public State getState() {
        return state;
    }

    public static RPSState find(State state) {
        for (RPSState rpsState : RPSState.values()) {
            if (rpsState.getState() == state) {
                return rpsState;
            }
        }
        return null;
    }
}
