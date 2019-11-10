package sample.bots;

import javafx.scene.paint.Color;
import sample.Territory;
import sample.tribe.TribesType;
import sample.World;

public class Attacker extends Bot{
    public Attacker(final Color color, String name, TribesType type, Territory territory, World world) {
        super(color,name,type,territory,world);
    }

    @Override
    public void update() {
        if (getEvolutionPoints() != 0) {
            double choice = Math.random();
            if (choice < 0.6) {
                addAttack();
            } else if (choice >= 0.6 && choice < 0.9) {
                addBorn();
            }
            else {
                addFoodProduction();
            }
        }
    }
}
