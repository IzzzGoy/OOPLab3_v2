package sample.tribe;

import sample.Territory;
import sample.World;

public class Predators extends Tribe {

    private final double needAnimals = 0.7;

    public Predators(String name, Territory terr, World world) {
        super(TribesType.PREDATORS,name, terr, world);
        food_production = 8.6;
        attack = 25.0;
    }

    @Override
    protected void eat() {
        for (Territory territory: getTribesTerritories()) {
            territory.EatAnimals(needAnimals);
        }
    }

    @Override
    public void addAttack() {
        attack += 4.7;
    }

    @Override
    public void addBorn() {
        born += 0.005;
    }

    @Override
    public void addFoodProduction() {
        food_production += 1.5;
    }

}
