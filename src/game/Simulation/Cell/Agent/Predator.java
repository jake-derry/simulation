package game.Simulation.Cell.Agent;

import game.Simulation.State;

/**
 * Predator class used to model Predator behavior during a Predator-Prey
 * Simulation. Implements methods in Mover interface and can kill.
 */
public class Predator implements Mover {
    private int myEnergy;
    private int myInitialEnergy;
    private int myFoodBoost;
    private int myOffspringThreshold;
    private boolean living;

    /**
     * Constructor for Predator. Initializes energy level, boost in energy
     * given from consuming prey, and a threshold at which the predator produces
     * offspring.
     *
     * @param initialEnergy             Initial energy level
     * @param foodBoost                 Boost in energy when prey is consumed
     * @param offspringThreshold        Energy threshold at which the predator
     *                                  produces offspring
     */
    public Predator(int initialEnergy, int foodBoost, int offspringThreshold) {
        myEnergy = initialEnergy;
        myInitialEnergy = initialEnergy;
        myFoodBoost = foodBoost;
        myOffspringThreshold = offspringThreshold;
        living = true;
    }


    @Override
    public boolean readyToBreed() {
        return (myEnergy >= myOffspringThreshold);
    }

    @Override
    public Mover offspring() {
        return new Predator(myInitialEnergy, myFoodBoost,
                myOffspringThreshold);
    }

    @Override
    public void step() {
        myEnergy--;
    }

    @Override
    public boolean isLiving() {
        return (myEnergy > 0);
    }

    @Override
    public State moverState() {
        return State.PREDATOR;
    }

    @Override
    public void kill() {
        living = false;
    }

    /**
     * Kills food and boosts energy.
     *
     * @param food                          A mover that the predator is killing
     */
    public void eat(Mover food) {
        if (food != null) {
            myEnergy += myFoodBoost;
            food.kill();
        }
    }
}
