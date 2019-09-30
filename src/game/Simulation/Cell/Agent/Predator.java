package game.Simulation.Cell.Agent;

import game.Simulation.State;

public class Predator extends Mover {
    private int myEnergy;
    private int myInitialEnergy;
    private int myFoodBoost;
    private int myOffspringThreshold;
    private boolean living;

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

    public void eat(Mover food) {
        if (food != null) {
            myEnergy += myFoodBoost;
            food.kill();
        }
    }
}
