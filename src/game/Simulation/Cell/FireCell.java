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
                nextState = EMPTY;
        }

        else {
                nextState = (burning() ? BURNING : TREE);
        }
        setNextState(nextState);

    }

    private boolean burning() {
        int emptyCount;
        if (getCountMap().containsKey(EMPTY)) {
            emptyCount = getCountMap().get(EMPTY);
        } else {
            emptyCount = 0;
        }
        for (int i = 0; i < emptyCount; i++) {
            if (random.nextDouble() <= myProbCatch) {
                return true;
            }
        }
        return false;
    }
}
