package sample.bots;

import javafx.scene.paint.Color;
import sample.Player;
import sample.Territory;
import sample.World;
import sample.tribe.TribesType;

public abstract class Bot extends Player {
    public Bot(final Color color, String name, TribesType type, Territory territory, World world) {
        super(color, name, type, territory, world);
    }

    public abstract void update();
}
