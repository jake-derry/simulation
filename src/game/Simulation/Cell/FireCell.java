package game.Simulation.Cell;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class FireCell extends Cell {
    private static final int EMPTY = 0;
    private static final int BURNING = 1;
    private static final int TREE = 2;

    private double myProbCatch;
    private Random random;
    private Map<Integer, Integer> countMap;

    public FireCell(int state, Iterator<Cell> neighborIterator, double probCatch) {
        super(state, neighborIterator);
        myProbCatch = probCatch;
        random = new Random();
        updateCountMap();
    }

    @Override
    public void updateNext() {
        updateCountMap();
        int nextState;
        switch (getState()) {
            case (EMPTY) :
            case (BURNING) :{
                nextState = EMPTY;
            }
            case (TREE) : {
                nextState = (burning() ? BURNING : TREE);

            }
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
