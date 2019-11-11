package sample.bots;

import javafx.scene.paint.Color;
import sample.Territory;
import sample.World;
import sample.tribe.TribesType;

public class Defender extends Bot {
    public Defender(final Color color, String name, TribesType type, Territory territory, World world) {
        super(color, name, type, territory, world);
    }

    @Override
    public void update() {
        if (getEvolutionPoints() != 0) {
            double choice = Math.random();
            if (choice < 0.2) {
                addAttack();
            } else if (choice >= 0.2 && choice < 0.6) {
                addBorn();
            } else {
                addFoodProduction();
            }
        }
    }
}
