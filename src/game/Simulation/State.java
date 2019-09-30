package game.Simulation;

import java.util.Map;
import java.util.TreeMap;

/**
 * Enumerates the states of cells in simulations.
 *
 * EXAMPLE:
 *      Cell.setNextState(State.EMPTY);
 *
 */
public enum State {
    EMPTY("empty"),
    LIVE("live"),
    WALL("wall"),
    WATER("water"),
    PREDATOR("predator"),
    PREY("prey"),
    GROUP_A("a"),
    GROUP_B("b"),
    BURNING("fire"),
    TREE("tree"),
    ROCK("rock"),
    PAPER("paper"),
    SCISSORS("scissors"),
    ANT("ant"),
    FOOD("food"),
    NEST("nest")
    ;

    /**
     * A string representation of the state which is how a state
     * is represented in an XML file.
     */
    private String myStateCode;


    /**
     * A constructor that takes in an array of the state codes.
     *
     * @param stateCode                        A string representation of the
     *                                         state.
     */
    State(String stateCode) {
        myStateCode = stateCode;
    }

    /**
     * Given a state code, finds the State object that corresponds
     * with it.
     *
     * @param stateCode                         A state code that corresponds with
     *                                          a State object
     * @return                                  The state object the state code corresponds
     *                                          with
     */
    public static State getState(String stateCode) {
        return getStateMap().get(stateCode);
    }


    /**
     * Getter for the state code for some state.
     *
     * @return                                  State code
     */
    private String getStateCode() {
        return myStateCode;
    }

    /**
     * Generates the map of each state code and which State it corresponds to.
     *
     * @return                                  Map with stateCodes as the key and States as the values
     */
    private static Map<String, State> getStateMap() {
        Map<String, State> newStateMap = new TreeMap<>();
        for (State state : State.values()) {
            newStateMap.put(state.getStateCode(), state);
        }
        return newStateMap;
    }
}