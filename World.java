package sample;

import sample.tribe.Tribe;

import java.util.ArrayList;

public class World {
    private int size;
    private ArrayList<Territory> land;
    private ArrayList<Tribe> tribes = new ArrayList<>(4);

    public World() {
        System.out.println((int)Math.pow(size,2));
    }

    {
        size = 50;
        land = new ArrayList<>((int)Math.pow(size,2));
        for (int i = 0; i < (int)Math.pow(size,2); i++) {
            land.add(new Territory());
        }
    }

    public int size() {
        return land.size();
    }

    public int length() {
        return size;
    }

    public ArrayList<Territory> getLand() {
        return land;
    }

    public ArrayList<Integer> getNeighbors(Territory territory) {
        int position = land.indexOf(territory);
        ArrayList<Integer> neighbors = new ArrayList<>();
        if (position < 50) {
            neighbors.add(position + size);
        } else if (position > 2449) {
            neighbors.add(position - size);
        } else {
            neighbors.add(position + size);
            neighbors.add(position - size);
        }
        if (position % size != 0) {
            neighbors.add(position - 1);
        }
        if ((position + 1) % size != 0 ) {
            neighbors.add(position + 1);
        }
        return neighbors;
    }

    public void addTribe(Tribe tribe) {
        tribes.add(tribe);
    }

    public void update() {
        for (Territory territory: land) {
            territory.update();
        }
        for (Tribe tribe: tribes) {
            tribe.update();
        }
    }
}
