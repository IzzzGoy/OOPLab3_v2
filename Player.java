package sample;

import javafx.scene.paint.Color;
import sample.tribe.*;

public class Player {

    private Tribe tribe;
    private Color color;
    private int evolutionPoints;

    public Player(final Color color, String name, TribesType type, Territory territory, World world) {
        switch (type) {
            case OMNIVOROUS:
                tribe = new Omnivorous(name, territory, world);
                break;
            case HERBIVORES:
                tribe = new Herbivores(name, territory, world);
                break;
            case PREDATORS:
                tribe = new Predators(name, territory, world);
                break;
        }
        this.color = color;
        evolutionPoints = 0;
        territory.colonization(tribe, 100);
        world.addTribe(tribe);
    }

    public int getEvolutionPoints() {
        return evolutionPoints;
    }

    public long getPopulation() {
        long population = 0;
        for (Territory territory : tribe.getTribesTerritories()) {
            if ( territory != null) {
                population += territory.getPopulation();
            }
        }
        return population;
    }

    public void addEvolutionPoints() {
        evolutionPoints++;
    }

    public void addAttack() {
        if (evolutionPoints > 0) {
            tribe.addAttack();
            evolutionPoints--;
        }
    }

    public String getName() {
        return tribe.getName();
    }

    public void addFoodProduction() {
        if (evolutionPoints > 0) {
            tribe.addFoodProduction();
            evolutionPoints--;
        }
    }

    public void addBorn() {
        if (evolutionPoints > 0) {
            tribe.addBorn();
            evolutionPoints--;
        }
    }

    public Color getColor() {
        return color;
    }

    public Tribe getTribe() {
        return tribe;
    }
}
