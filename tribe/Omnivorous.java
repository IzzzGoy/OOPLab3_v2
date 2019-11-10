package sample.tribe;

import sample.Territory;
import sample.World;

public class Omnivorous extends Tribe {

    private final double needPlants = 0.6;
    private final double needAnimals = 0.5;

    public Omnivorous(String name, Territory start, World world) {

        super(TribesType.OMNIVOROUS, name, start, world);
        food_production = 5.8;
        attack = 18.2;
    }


    @Override
    protected void eat() {
        for (Territory territory: getTribesTerritories()) {
            territory.EatPlants(needPlants);
            territory.EatAnimals(needAnimals);
        }
    }

    @Override
    public void addAttack() {
        attack += 1.3;
    }

    @Override
    public void addBorn() {
        born += 0.01;
    }

    @Override
    public void addFoodProduction() {
        food_production += 3.7;
    }

}
