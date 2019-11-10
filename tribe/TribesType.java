package sample.tribe;

import java.util.Random;

public enum TribesType {
    PREDATORS,
    HERBIVORES,
    OMNIVOROUS;

    public static TribesType getRandomType() {
        int index = new Random().nextInt(3);
        return TribesType.values()[index];
    }

}
