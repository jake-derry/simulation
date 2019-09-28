package game.Simulation;

import java.util.Map;
import java.util.TreeMap;

/**
 * TODO: Maybe find a way to get these strings from a file
 *
 * Enumerates the states of cells in simulations.
 *
 * EXAMPLE:
 *      Cell.setNextState(State.EMPTY);
 *
 * use a string "EMPTY" to represent an EMPTY state
 */
public enum State {
    EMPTY(""),
    LIVE(""),
    WALL(""),
    WATER(""),
    PREDATOR(""),
    PREY(""),
    GROUP_A(""),
    GROUP_B(""),
    BURNING(""),
    TREE(""),
    BEYOND_EDGE("");

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
     * Gets the index that corresponds with the simulation type and
     * state.
     *
     * @param simulationType                    String that represents which simulation the index
     *                                          needs to be retrieved for
     * @throws StateIncompatibleException       when the simulation does not have a state code
     *                                          that corresponds to the state called on
     * @return
     */
    public int getIndex (String simulationType) {
        for (String stateCode : myStateCodes) {
            if (stateCode.startsWith(simulationType)) {
                return Integer.parseInt(stateCode.substring(stateCode.length()-1));
            }
        }
        throw new StateIncompatibleException("This state is incompatible with a running simulation\n" +
                                             "Make sure that the correct stateCode is listed in the" +
                                             "State enum.");
    }

    /**
     * Getter for the list of state codes for some state.
     *
     * @return                                  Array of state codes
     */
    private String[] getStateCodes() {
        return myStateCodes;
    }

    /**
     * Generates the map of each state code and which State it corresponds to.
     *
     * @return                                  Map with stateCodes as the key and States as the values
     */
    private static Map<String, State> getStateMap() {
        Map<String, State> newStateMap = new TreeMap<>();
        for (State state : State.values()) {
            for (String stateCode : state.getStateCodes()) {
                newStateMap.put(stateCode, state);
            }
        }
        return newStateMap;
    }
}