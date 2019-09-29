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
    public State updateNext() {
        State nextState;
        if (getState() == EMPTY || getState() == BURNING) {
                return EMPTY;
        }

        else {
                return burning() ? BURNING : TREE;
        }

    }

    private boolean burning() {
        for (int i = 0; i < getCountMap().get(EMPTY); i++) {
            if (random.nextDouble() <= myProbCatch) {
                return true;
            }
        }
        return false;
    }
}
