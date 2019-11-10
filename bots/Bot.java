package sample.bots;

import javafx.scene.paint.Color;
import sample.Player;
import sample.Territory;
import sample.tribe.TribesType;
import sample.World;

public abstract class Bot extends Player {
    public Bot(final Color color, String name, TribesType type, Territory territory, World world) {
        super(color,name,type,territory,world);
    }
    public abstract void update();
}
