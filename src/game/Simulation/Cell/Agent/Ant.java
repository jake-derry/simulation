package game.Simulation.Cell.Agent;

/**
 * Ant class used in ForagingCell to model the
 * ants behavior as it moves through the grid.
 * Stores pheromones which it can release and
 * can either forage or return home.
 *
 * @author Jake Derry
 */
public class Ant {
    private boolean foraging;
    private int myForagingPheromones;
    private int myReturningPheromones;

    /**
     * Constructor for Ant. Initializes Ant object with the
     * amount of pheromones that are left behind while foraging
     * and while returning. Initially, the ant is foraging.
     *
     * Generally, returningPheromones are greater than foragingPheromones,
     * but this is not enforced.
     *
     * @param foragingPheromones        The number of pheromones left while foraging
     * @param returningPheromones       The number of pheromones left while returning
     */
    public Ant(int foragingPheromones, int returningPheromones) {
        foraging = true;
        myForagingPheromones = foragingPheromones;
        myReturningPheromones = returningPheromones;
    }

    /**
     * Return the number of pheromones that should currently
     * be released depending on whether the ant is foraging
     * or not.
     *
     * @return                          Number of pheromones that should be released
     */
    public int pheromones() {
        return isForaging() ? myForagingPheromones : myReturningPheromones;
    }

    /**
     * Switches foraging to the opposite value. Helpful
     * when the ant reaches the nest or the food.
     */
    public void switchForaging() {
        foraging = !(foraging);
    }

    /**
     * Returns whether the ant is foraging or returning to the
     * nest.
     *
     * @return                          Whether the ant is foraging or returning to the nest
     */
    public boolean isForaging() {
        return foraging;
    }
}
