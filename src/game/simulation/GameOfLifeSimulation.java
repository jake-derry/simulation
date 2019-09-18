package game.simulation;


/**
 * This simulation runs Conway's Game of Life which
 * has rules that simulate life.
 */
public class GameOfLifeSimulation extends Simulation {
    int DEAD = 0;
    int LIVE = 1;

    @Override
    public void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); i++) {
                getCell(i, j).setNextState(nextState(i, j));
            }
        }
    }

    private int nextState(int i, int j) {
        int[] neighborStates = getNeighborStates(i, j);
        int count = countLiveNeighbors(neighborStates);
        int state = getCell(i, j).getState();
        if (state == DEAD) {
            if (count == 3) {
                return LIVE;
            }
            else {
                return DEAD;
            }
        }

        else {
            if (count < 2 || count > 3) {
                return DEAD;
            }
            else {
                return LIVE;
            }
        }
    }

    private int countLiveNeighbors(int[] neighborStates) {
        int count = 0;
        for (int i = 0; i < neighborStates.length; i++) {
            if (neighborStates[i] == LIVE) count++;
        }
        return count;
    }

    private int[] getNeighborStates(int i, int j) {
        int[] neighborStates = new int[8];
        neighborStates[0] = getCell(i - 1, j).getState();
        neighborStates[1] = getCell(i + 1, j).getState();
        neighborStates[2] = getCell(i - 1, j - 1).getState();
        neighborStates[3] = getCell(i + 1, j - 1).getState();
        neighborStates[4] = getCell(i, j - 1).getState();
        neighborStates[5] = getCell(i - 1, j + 1).getState();
        neighborStates[6] = getCell(i + 1, j + 1).getState();
        neighborStates[7] = getCell(i, j + 1).getState();
        return neighborStates;
    }
}
