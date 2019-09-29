package game.Simulation.Cell;

import game.Simulation.Cell.Mover.Mover;
import game.Simulation.Cell.Mover.Predator;
import game.Simulation.Cell.Mover.Prey;
import game.Simulation.State;

import java.util.*;

/**
 * Idk if this works?
 */
public class PredatorPreyCell extends Cell {
    private Mover mover;
    private Mover nextMover;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state Initial state of the Cell
     */
    public PredatorPreyCell(State state, int initialEnergy, int foodBoost, int breedThreshold,
                            int breedTime) {
        super(state);
        if (getState() == State.PREY) {
            mover = new Prey(breedTime);

        } else if (getState() == State.PREDATOR) {
            mover = new Predator(initialEnergy, foodBoost, breedThreshold);

        } else {
            mover = null;
        }
    }

    @Override
    public void stepState() {
        if (getNextState() == null) {
            setState(null);
        }
        else {
            if (getNextMover().isLiving()) {
                setState(getNextMover().moverState());
                stepMover();
            }
            else {
                setState(null);
                setMover(null);
            }
        }
    }

    @Override
    public void updateNext() {
        if (getState() == State.PREY) {
            updatePrey();

        } else if (getState() == State.PREDATOR) {
            updatePredator();

        } else {    // getState == State.EMPTY
            return;
        }
    }

    /**
     *
     */
    private void updatePredator() {
        Predator predator = (Predator) mover;
        PredatorPreyCell randomCell = (PredatorPreyCell) getNeighborhood().
                chooseNeighbor(new State[] {State.EMPTY, State.PREY});

        randomCell.setNextMover(mover);

        Mover oldMover = randomCell.getMover();
        predator.eat(oldMover);

        getMover().step();

        replicateIfReady(randomCell);

        setNextMover(null);
    }

    /**
     *
     */
    private void updatePrey() {
        PredatorPreyCell randomCell = (PredatorPreyCell) getNeighborhood().
                chooseNeighbor(new State[] {State.EMPTY});
        randomCell.setNextMover(mover);

        getMover().step();

        replicateIfReady(randomCell);

        setNextMover(null);
    }

    /**
     *
     *
     * @param randomCell
     */
    private void replicateIfReady(PredatorPreyCell randomCell) {
        if (mover.readyToBreed()) {
            Mover newMover = mover.offspring();

            PredatorPreyCell offspringCell = (PredatorPreyCell) getNeighborhood().
                    chooseNeighbor(new State[] {State.EMPTY});
            if (offspringCell == randomCell) {
                offspringCell = (PredatorPreyCell) getNeighborhood().
                        chooseNeighbor(new State[] {State.EMPTY});
            }
            offspringCell.setNextMover(newMover);

        }
    }

    private void stepMover() {
        mover = nextMover;
    }

    private void setNextMover(Mover newMover) {
        nextMover = newMover;
    }

    private void setMover(Mover newMover) {
        mover = newMover;
    }

    private Mover getNextMover() {
        return nextMover;
    }

    private Mover getMover() {
        return mover;
    }
}
