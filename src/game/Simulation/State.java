package game.Simulation;

import java.util.Map;
import java.util.TreeMap;

//Simulations Supported
//private static final String LIFE = "gameOfLife";
//private static final String SEGREGATION = "segregation";
//private static final String PREDATOR_PREY = "predatorPrey";
//private static final String FIRE = "fire";
//private static final String PERCOLATION = "percolation";

public enum State {
    //Are these now considered "Magic Values"??
    EMPTY(new String[]{"gameOfLife0", "segregation0", "predatorPrey0", "fire0", "percolation0"}),
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

    private Map<String, State> stateMap = getStateMap();
    private String[] myStateCodes;

    State(String[] stateCodes) {
        myStateCodes = stateCodes;
    }

    public State getState(String stateCode) {
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