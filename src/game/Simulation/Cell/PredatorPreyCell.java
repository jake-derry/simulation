package game.Simulation.Cell;

import game.Simulation.Cell.Agent.Mover;
import game.Simulation.Cell.Agent.Predator;
import game.Simulation.Cell.Agent.Prey;
import game.Simulation.State;

/**
 * Cell that implements Predator-Prey rules.
 */
public class PredatorPreyCell extends Cell {
    private Mover mover;
    private Mover nextMover;

    /**
     * Constructor for Cell. Initializes the state of the cell, initial
     * energy, food boost, and breeding threshold of the predator, and the
     * breed time of the prey.
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
     * Updates a predator.
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
     * Updates a prey.
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
     * Places an offspring in a random neighboring cell if the mover
     * is ready to replicate.
     *
     * @param randomCell        Cell for offspring to be placed.
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

    /**
     * Sets the mover to the next mover.
     */
    private void stepMover() {
        mover = nextMover;
    }

    /**
     * Sets the next mover
     *
     * @param newMover      The next mover
     */
    private void setNextMover(Mover newMover) {
        nextMover = newMover;
    }

    /**
     * Sets the mover.
     *
     * @param newMover      The mover
     */
    private void setMover(Mover newMover) {
        mover = newMover;
    }

    /**
     * Gets the next mover.
     *
     * @return              The next mover
     */
    private Mover getNextMover() {
        return nextMover;
    }

    /**
     * Gets mover.
     *
     * @return              The mover
     */
    private Mover getMover() {
        return mover;
    }
}
