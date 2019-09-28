package game.Simulation.Cell;

import game.Simulation.State;
import game.Simulation.StateIncompatibleException;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static game.Simulation.State.*;

public class FireCell extends Cell {

    private double myProbCatch;
    private Random random;
    private Map<State, Integer> countMap;

    public FireCell(State state, Iterator<Cell> neighborIterator, double probCatch) {
        super(state, neighborIterator);
        myProbCatch = probCatch;
        random = new Random();
        updateCountMap();
    }

    @Override
    public void updateNext() {
        updateCountMap();
        State nextState;
        if (getState() == EMPTY || getState() == BURNING) {
                nextState = EMPTY;
        }

        else if (getState() == TREE) {
                nextState = (burning() ? BURNING : TREE);
        }

    }

    private void updateCountMap() {
        countMap = CellUtils.countMap(getNeighbors());
    }

    private boolean burning() {
        for (int i = 0; i < countMap.get(EMPTY); i++) {
            if (random.nextDouble() <= myProbCatch) {
                return true;
            }
        }
        return false;
    }
}
