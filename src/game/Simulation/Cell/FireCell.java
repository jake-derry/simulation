package game.Simulation.Cell;

import game.Simulation.State;

import java.util.Arrays;
import java.util.Random;

import static game.Simulation.State.*;

/**
 * Cell that implements rules of a fire simulation.
 */
public class FireCell extends Cell {

    /**
     * Likelihood of a neighboring cell of a burning cell
     * catching fire.
     */
    private double myProbCatch;

    private Random random;

    /**
     * Constructor for FireCell. Initializes state and the
     * likelihood of a neighboring cell of a burning cell
     * catching fire.
     *
     * @param state         Initial state
     * @param probCatch     Probability of a neighboring cell of
     *                      a burning cell catching fire.
     */
    public FireCell(State state, double probCatch) {
        super(state);
        myProbCatch = probCatch;
        random = new Random();
        setStateList(Arrays.asList(EMPTY, TREE, BURNING));
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

    /**
     * Based on the probability of catching fire determines if
     * a tree cell will burn.
     *
     * @return          Whether a tree cell will burn
     */
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
