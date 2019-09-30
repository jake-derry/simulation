package game.Simulation.Cell;

import game.Simulation.Cell.Agent.Ant;
import game.Simulation.State;

import java.util.Random;

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
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state Initial state of the Cell
     */
    public ForagingCell(State state, int foragingPheromones, int returningPheromones, double birthProbability) {
        super(state);
        pheromones = 0;
        random = new Random();

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
                if(getCountMap().get(State.EMPTY) != 0){
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
                break;
            }
            case ANT: {
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

                dropPheromones();

                if (ant.isForaging() && nextAntSpot.isFood() || (! ant.isForaging()) && nextAntSpot.isNest()) {
                    ant.switchForaging();
                } else {
                    nextAntSpot.setNextAnt(ant);
                    setNextAnt(null);
                }
                break;
            }
        }
    }

    private ForagingCell getNextAntSpot(ForagingCell right, ForagingCell down) {
        ForagingCell nextAntSpot;

        if (right.getPheromones() > down.getPheromones()) {
            nextAntSpot = right;

        } else if (right.getPheromones() < down.getPheromones()) {
            nextAntSpot = down;

        } else {
            boolean isDown = random.nextBoolean();
            if (isDown) {
                nextAntSpot = down;
            } else {
                nextAntSpot = right;
            }
        }
        return nextAntSpot;
    }

    /**
     *
     * @return
     */
    public int getPheromones() {
        return pheromones;
    }

    /**
     *
     * @param next
     */
    public void setNextAnt(Ant next) {
        nextAnt = next;
    }

    public void dropPheromones() {
        pheromones += ant.pheromones();
    }

    public boolean isFood() {
        return food;
    }

    public boolean isNest() {
        return nest;
    }

    private void stepAnt() {
        ant = nextAnt;
    }

    private Ant getNextAnt() {
        return nextAnt;
    }

}
