package sample;

import javafx.scene.paint.Color;
import sample.tribe.Tribe;

import java.util.Random;

public class Territory {
    private double fertility;
    private double recourse;
    private double plants;
    private int water;
    private double animals;
    private int population;
    private double exhaustion = 1.2;
    private Random random;

    private final double animalsBorningCoefficient = 0.5;
    private final double animalsNeedPlants = 1.6;
    private final double plantGrowing = 1.6;

    private Tribe owner = null;

    public Territory() {
        random = new Random();
        population = 0;
        fertility = (Math.random() + 1);
        recourse = Math.random() * 200;
        plants = random.nextInt(700) + 250;
        water = random.nextInt(2000) + 200;
        animals = random.nextInt(100) + 100;
    }

    public int getPopulation() {
        return population;
    }

    public void changePopulation(int population) {
        this.population += population;
        if (this.population > water) {
            this.population = water;
        }
    }

    public void update() {
        System.out.println(exhaustion);
        if (owner == null) {
            plants += plants * plantGrowing * fertility;
            plants -= animals * animalsNeedPlants;
            if (plants < 0) {
                animals -= plants / animalsNeedPlants;
                plants = 1;
            }
            animals += animals * animalsBorningCoefficient;
            if (exhaustion > 1.2)exhaustion -= 0.5;
            return;
        }
            if ((int) animals <= 0 || (int) plants <= 0) {
                animals = Math.max(animals,0);
                plants = Math.max(plants,0);
                owner.Exodus(this);
                population = 0;
                owner = null;
                return;
            }
            switch (owner.getType()) {
                case OMNIVOROUS:
                    plants += plants * plantGrowing * fertility * owner.getFood_production();
                    plants -= animals * animalsNeedPlants;
                    if (plants < 0) {
                        animals -= plants / animalsNeedPlants;
                        plants = 1;
                    }
                    animals += animals * animalsBorningCoefficient * owner.getFood_production();
                    plants = Math.max(plants, 0);
                    break;
                case HERBIVORES:
                    plants += plants * plantGrowing * fertility * owner.getFood_production() * 1.5;
                    plants -= animals * animalsNeedPlants;
                    if (plants < 0) {
                        animals -= plants / animalsNeedPlants;
                        plants = 1;
                    }
                    animals += animals * animalsBorningCoefficient;
                    plants = Math.max(plants, 0);
                    break;
                case PREDATORS:
                    plants += plants * plantGrowing * fertility;
                    plants -= animals * animalsNeedPlants;
                    if (plants < 0) {
                        animals -= plants / animalsNeedPlants;
                        plants = 1;
                    }
                    animals += animals * animalsBorningCoefficient * owner.getFood_production();
                    plants = Math.max(plants, 0);
                default:
                    plants = plants * plantGrowing * fertility;
                    plants -= animals * animalsNeedPlants;
                    if (plants < 0) {
                        animals -= plants / animalsNeedPlants;
                        plants = 1;
                    }
                    animals = animals * animalsBorningCoefficient * 1.2;
                    plants = Math.max(plants, 0);
                    break;
            }
        plants /= exhaustion;
        animals /= exhaustion;
        exhaustion += 0.05;
    }

    public void EatPlants(double coefficient) {
        plants -= population * coefficient;
        if (plants < 0) {
            population += (int)(plants / coefficient);
            population = Math.max(population,0);
            plants = 1;
        }
    }

    public void EatAnimals(double coefficient) {
        animals -= population * coefficient;
        if (animals < 0) {
            population += (int)(animals / coefficient);
            population = Math.max(population,0);
            animals = 2;
        }
    }

    public double getAnimals() {
        return animals;
    }

    public double getPlants() {
        return plants;
    }

    public int getWater() {
        return water;
    }

    public double getRecourse() {
        return recourse;
    }

    public String getOwnerName() {
        if (owner != null) {
            return owner.getName();
        }
        return "Nobody";
    }

    public void Colonization(Tribe colonist , int numberOfColonist) {
        if (owner == null) {
            owner = colonist;
            population = numberOfColonist;
        }
    }

    public Tribe getOwner() {
        return owner;
    }

    public boolean Conquest(Tribe invader, int warriors) {
        population -= warriors;
        if (population < 0) {
            population = Math.abs(population);
            Territory territory = this;
            owner.Exodus(territory);
            owner = invader;
            return true;
        }
        return false;
    }


}
