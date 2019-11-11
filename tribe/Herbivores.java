package sample.tribe;

import sample.Territory;
import sample.World;

public class Herbivores extends Tribe {

    private final double needPlants = 0.8;

    public Herbivores(String name, Territory start, World world) {
        super(TribesType.HERBIVORES, name, start, world);
        food_production = 12.4;
        attack = 10.8;
    }

    @Override
    protected void eat() {
        for (Territory territory : getTribesTerritories()) {
            territory.eatPlants(needPlants);
        }
    }

    @Override
    public void addAttack() {
        attack += 3.8;
    }

    @Override
    public void addBorn() {
        born += 0.03;
    }

    @Override
    public void addFoodProduction() {
        food_production += 5.2;
    }

}
