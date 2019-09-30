package game.Simulation.Cell.Agent;

public class Ant {
    private boolean foraging;
    private int myForagingPheromones;
    private int myReturningPheromones;

    public Ant(int foragingPheromones, int returningPheromones) {
        foraging = true;
        myForagingPheromones = foragingPheromones;
        myReturningPheromones = returningPheromones;
    }

    public int pheromones() {
        return foraging ? myForagingPheromones : myReturningPheromones;
    }

    public void switchForaging() {
        foraging = !(foraging);
    }
}
