package sample.tribe;

import sample.Territory;
import sample.World;

import java.util.ArrayList;

public abstract class Tribe {

    private TribesType type;
    private String name;
    private ArrayList<Territory> tribesTerritories;
    private World world;

    protected double attack;
    protected double born;
    protected double food_production;

    public Tribe(TribesType type, String nameOfTribe, Territory startTerritory, World world)  {
        this.type = type;
        name = nameOfTribe;
        tribesTerritories = new ArrayList<>();
        tribesTerritories.add(startTerritory);
        born = 0.05;
        this.world = world;
    }

    public double getFood_production() {
        return food_production;
    }

    public void Exodus (Territory territory) {
        tribesTerritories.remove(territory);
    }

    protected abstract void eat();

    public TribesType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void update() {

        eat();
        grow();

        for (int i = 0; i < tribesTerritories.size(); i++) {
            Territory startTerritory = tribesTerritories.get(i);
            ArrayList<Integer> neighbours = world.getNeighbors(startTerritory);
            for (Integer neighbour : neighbours) {
                colonise(startTerritory, world.getLand().get(neighbour));
                attack(startTerritory, world.getLand().get(neighbour));
                migration(startTerritory, world.getLand().get(neighbour));
            }
        }
    }

    public abstract void addAttack();

    public abstract void addBorn();

    public abstract void addFoodProduction();

    private void grow() {                                                                   //Увеличение популяции
        for (Territory territory : tribesTerritories) {
            territory.changePopulation((int) (territory.getPopulation() * born));
        }
    }

    private void attack(final Territory startTerritory, Territory endTerritory) {
        if (tribesTerritories.contains(endTerritory) || endTerritory.getOwner() == null) {
            return;
        }
        int warriors = (int)(attack * 1/5 * startTerritory.getPopulation());
        if (endTerritory.Conquest(this,warriors / 10)) {
            tribesTerritories.add(endTerritory);
            startTerritory.changePopulation(-((1/5 * startTerritory.getPopulation())/10));
        }
        else {
            startTerritory.changePopulation(-(1/5 * startTerritory.getPopulation())/20);
        }
    }

    private void colonise(Territory startTerritory, Territory endTerritory) {
        if (tribesTerritories.contains(endTerritory) || endTerritory.getOwner() != null) {
            return;
        }
        if (startTerritory.getPopulation() > 200) {
            if((int) endTerritory.getPlants() == 0 || (int) endTerritory.getAnimals() == 0) {
                return;
            }
            endTerritory.Colonization(this, 100);
            tribesTerritories.add(endTerritory);
            startTerritory.changePopulation(-100);
        }
    }

    private void migration(Territory startTerritory, Territory endTerritory) {
        if (!tribesTerritories.contains(endTerritory) || endTerritory.getOwner() == null) {
            return;
        }
        if (endTerritory.getPopulation() != endTerritory.getWater() && (startTerritory.getPopulation() - (endTerritory.getWater() - endTerritory.getPopulation()) / 20) > 0) {
            startTerritory.changePopulation(-(endTerritory.getWater() - endTerritory.getPopulation()) / 20);
            endTerritory.changePopulation((endTerritory.getWater() - endTerritory.getPopulation()) / 20);
        }
    }

    public final ArrayList<Territory> getTribesTerritories() {
        return tribesTerritories;
    }


    public double getAttack() {
        return attack;
    }

    public double getBorn() {
        return born;
    }
}
