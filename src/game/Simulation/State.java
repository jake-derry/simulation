package game.Simulation;

import java.util.Map;
import java.util.TreeMap;

public enum State {
    // TODO: Fill in the state codes        JONAH
    EMPTY(null),
    LIVE(null),
    WALL(),
    WATER(),
    PREDATOR(),
    PREY(),
    GROUP_A(),
    GROUP_B(),
    BURNING(),
    TREE();

    private Map<String, State> stateMap = getStateMap();
    private String[] myStateCodes;

    State(String[] stateCodes) {
        myStateCodes = stateCodes;
    }

    State getState(String stateCode) {
        return stateMap.get(stateCode);
    }

    private String[] getStateCodes() {
        return myStateCodes;
    }

    private Map<String, State> getStateMap() {
        Map<String, State> newStateMap = new TreeMap<>();
        for (State state : State.values()) {
            for (String stateCode : getStateCodes()) {
                newStateMap.put(stateCode, state);
            }
        }
        return newStateMap;
    }
}