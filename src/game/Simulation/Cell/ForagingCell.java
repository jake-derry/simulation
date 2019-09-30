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
        Random random = new Random();

        if (getState() == State.ANT) {
            ant = new Ant(foragingPheromones, returningPheromones);
        }

        nest = (getState() == State.NEST);
        food = (getState() == State.FOOD);
    }

    @Override
    public void stepState() {

    }

    @Override
    public void updateNext() {
        switch (getState()) {
            case NEST: {
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
            case ANT: {

            }
            case EMPTY: {

            }
        }
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
}
