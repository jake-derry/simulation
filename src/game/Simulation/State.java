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
 */
public enum State {
    EMPTY(new String[]{"gameOfLife0",
                       "segregation0",
                       "predatorPrey0",
                       "fire0",
                       "percolation0"}),
    LIVE(new String[]{"gameOfLife1"}),
    WALL(new String[]{"percolation1"}),
    WATER(new String[]{"percolation2"}),
    PREDATOR(new String[]{"predatorPrey1"}),
    PREY(new String[]{"predatorPrey2"}),
    GROUP_A(new String[]{"segregation1"}),
    GROUP_B(new String[]{"segregation2"}),
    BURNING(new String[]{"fire1"}),
    TREE(new String[]{"fire2"}),
    BEYOND_EDGE(new String[]{});

    /**
     * An array that stores all of the codes which are formatted
     * in a string as the name of the simulation plus the number
     * used to represent that state in XML.
     */
    private String[] myStateCodes;


    /**
     * A constructor that takes in an array of the state codes.
     *
     * @param stateCodes                        State codes enumerate all simulations
     *                                          and numbers that represent the state
     *                                          in that simulation.
     */
    State(String[] stateCodes) {
        myStateCodes = stateCodes;
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