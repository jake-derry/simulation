package game.Simulation.Cell;

import game.Simulation.State;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static game.Simulation.State.*;

public class FireCell extends Cell {

    private double myProbCatch;
    private Random random;

    public FireCell(State state, double probCatch) {
        super(state);
        myProbCatch = probCatch;
        random = new Random();
    }

    @Override
    public void updateNext() {
        State nextState;
        if (getState() == EMPTY || getState() == BURNING) {
                setNextState(EMPTY);
        }

        else {
                setNextState(burning() ? BURNING : TREE);
        }

    }

    private boolean burning() {
        int burningCount;
        if (getCountMap().containsKey(BURNING)) {
            burningCount = getCountMap().get(BURNING);
        } else {
            burningCount = 0;
        }
        for (int i = 0; i < burningCount; i++) {
            if (random.nextDouble() <= myProbCatch) {
                return true;
            }
        }
        return false;
    }
}
