package sample.bots;

import java.util.Random;

public enum BotsType {
    ATTACKER,
    POPULATION_MASTER,
    DEFENDER,
    MONSTER;

    public static BotsType getRandomType() {
        int rand = new Random().nextInt(3);
        BotsType[] type = BotsType.values();
        return type[rand];
    }
}



