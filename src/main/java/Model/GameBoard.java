package Model;

import java.util.ArrayList;

public class GameBoard {
    public int width;
    public int height;
    private final Position[][] map;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new Position[width][height];

        initMap();
    }

    private void initMap() {
        // Initialize the map with empty positions
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = new Position(x, y, null);
            }
        }
    }

    public void addIfValid(ArrayList<Position> zone, int x, int y) {
        if (isValidPosition(x, y)) {
            zone.add(map[x][y]);
        }
    }

    public boolean isOccupiedByEntity(GameEntity entity, int x, int y) {
        if (!isValidPosition(x, y)) return false;
        return map[x][y] == entity.position;
    }

    public Position getPosition(int x, int y) {
        if (!isValidPosition(x, y)) {
            throw new IllegalArgumentException("Invalid board position: (" + x + ", " + y + ")");
        }
        return map[x][y];
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
