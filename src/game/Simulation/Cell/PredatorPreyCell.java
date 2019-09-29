package game.Simulation.Cell;

import game.Simulation.Cell.Mover.Mover;
import game.Simulation.Cell.Mover.Predator;
import game.Simulation.Cell.Mover.Prey;
import game.Simulation.State;

import java.util.*;

public class PredatorPreyCell extends Cell {
    private Mover mover;
    private Mover nextMover;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state Initial state of the Cell
     */
    public PredatorPreyCell(State state, int initialEnergy, int breedTime) {
        super(state);
        if (getState() == State.PREY) {
            mover = new Prey(breedTime);

        } else if (getState() == State.PREDATOR) {
            mover = new Predator(initialEnergy);

        } else {
            mover = null;
        }
    }

    @Override
    public void stepState() {

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

    private void updatePredator() {

    }

    private void updatePrey() {
        PredatorPreyCell randomCell = (PredatorPreyCell) getNeighborhood().
                chooseNeighbor(new State[] {State.EMPTY});
        randomCell.setNextMover(mover);

        ((Prey) getMover()).step();
        
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
        setNextMover(null);
    }

    public Mover setNextMover(Mover newMover) {
        return null;
    }

    public Mover getMover() {
        return mover;
    }
}
