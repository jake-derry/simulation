package game.Simulation.Cell;

import game.Simulation.Cell.Agent.Ant;
import game.Simulation.State;

import java.util.Random;

/**
 * Cell that implements the rules of a Foraging Ants simulation.
 */
public class ForagingCell extends Cell {
    private int pheromones;
    private int myForagingPheromones;
    private int myReturningPheromones;
    private boolean nest;
    private boolean food;
    private Ant ant;
    private Ant nextAnt;
    private Random random;

    /**
     * Constructor for Cell. Initializes the state of the cell, the amount of
     * pheromones released during foraging and returning, and the probability of
     * birth of the nest.
     *
     * @param state         Initial state of the Cell
     */
    public ForagingCell(State state, int foragingPheromones, int returningPheromones, double birthProbability) {
        super(state);
        pheromones = 0;
        Random random = new Random();

        if (getState() == State.ANT) {
            ant = new Ant(foragingPheromones, returningPheromones);
        }

        nest = (getState() == State.NEST);
        food = (getState() == State.FOOD);
    }

    @Override
    public void stepState() {
        setState((getNextAnt() != null) ? State.ANT : State.EMPTY);
        stepAnt();
    }

    @Override
    public void updateNext() {
        switch (getState()) {
            case NEST: {
                updateNestCell();
            }
            case ANT: {
                updateAntCell();
            }
        }
    }

    /**
     * Updates an ant cell.
     */
    private void updateAntCell() {
        ForagingCell nextAntSpot = findNextAntSpot();

        dropPheromones();

        if (ant.isForaging() && nextAntSpot.isFood() || (! ant.isForaging()) && nextAntSpot.isNest()) {
            ant.switchForaging();
        } else {
            nextAntSpot.setNextAnt(ant);
            setNextAnt(null);
        }
    }

    /**
     * Updates a nest cell.
     */
    private void updateNestCell() {
        int index = random.nextInt(getCountMap().get(State.EMPTY));
        int count = 0;

        for (Cell cell : getCellMap().get(State.EMPTY)) {
            if (count == index) {
                cell.setNextState(State.ANT);
                ((ForagingCell) cell).setNextAnt(new Ant(myForagingPheromones, myReturningPheromones));
                break;
            }
        }
    }

    /**
     * Finds the ant spot based on whether the ant in foraging or not.
     *
     * @return          next cell where the ant will live
     */
    private ForagingCell findNextAntSpot() {
        ForagingCell nextAntSpot;
        if (ant.isForaging()) {
            ForagingCell right = (ForagingCell) getNeighborhood().getRight();
            ForagingCell down = (ForagingCell) getNeighborhood().getDown();
            nextAntSpot = getNextAntSpot(right, down);
        } else {
            ForagingCell left = (ForagingCell) getNeighborhood().getLeft();
            ForagingCell up = (ForagingCell) getNeighborhood().getUp();
            nextAntSpot = getNextAntSpot(left, up);
        }
        return nextAntSpot;
    }

    /**
     * Based on pheromones, gets the next ant spot either choosing the
     * left/right option or the up/down option.
     *
     * @param leftRight     left/right option cell
     * @param upDown        up/down option cell
     * @return              choice between the left/right and up/down cell
     */
    private ForagingCell getNextAntSpot(ForagingCell leftRight, ForagingCell upDown) {
        ForagingCell nextAntSpot;

        if (leftRight.getPheromones() > upDown.getPheromones()) {
            nextAntSpot = leftRight;

        } else if (leftRight.getPheromones() < upDown.getPheromones()) {
            nextAntSpot = upDown;

        } else {
            boolean isDown = random.nextBoolean();
            if (isDown) {
                nextAntSpot = upDown;
            } else {
                nextAntSpot = leftRight;
            }
        }
        return nextAntSpot;
    }

    /**
     * Gets the pheromones left at a spot in the cell.
     *
     * @return              Pheromones
     */
    public int getPheromones() {
        return pheromones;
    }

    /**
     * Sets the ant that will be moving to the cell at the
     * next step.
     *
     * @param next          Next ant
     */
    public void setNextAnt(Ant next) {
        nextAnt = next;
    }

    /**
     * Adds the pheromones of the current ant to the total
     * pheromones at a position.
     */
    public void dropPheromones() {
        pheromones += ant.pheromones();
    }

    /**
     * Returns whether or not a cell is a food cell.
     *
     * @return              Whether or not a cell is a food cell
     */
    public boolean isFood() {
        return food;
    }

    /**
     * Returns whether or not a cell is a nest cell.
     *
     * @return              Whether or not a cell is a nest cell
     */
    public boolean isNest() {
        return nest;
    }

    /**
     * Sets the next ant to the current ant.
     */
    private void stepAnt() {
        ant = nextAnt;
    }

    /**
     * Gets the next ant.
     *
     * @return              Next ant
     */
    private Ant getNextAnt() {
        return nextAnt;
    }

}
