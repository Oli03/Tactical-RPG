package Model;

import java.util.ArrayList;

public class EntitySpawner {
    GameBoard board;

    public EntitySpawner(GameBoard board) {
        this.board = board;
    }

    public void placeEntity(GameEntity entity, int x, int y) {
        // New entity in the game
        entity.entityCombatStats = new CombatStats(entity.isEnemy, entity.playZone);

        // Place the entity on the board
        entity.position = board.getPosition(x, y);
        entity.position.addCombatStats(entity.entityCombatStats);

        // Playable area for the entity
        calculateEntityZone(entity, x, y);
    }

    public void calculateEntityZone(GameEntity entity, int entityX, int entityY) {
        // Recalculate the play zone for the entity based on its current position
        ArrayList<Position> zone = entity.playZone;
        zone.clear();

        // Add positions in a cross pattern around the entity's current position
        for (int i = -2; i <= 2; i++) {
            board.addIfValid(zone, entityX + i, entityY);
            board.addIfValid(zone, entityX, entityY + i);
        }

        // Add diagonal positions around the entity's current position
        board.addIfValid(zone, entityX - 1, entityY - 1);
        board.addIfValid(zone, entityX - 1, entityY + 1);
        board.addIfValid(zone, entityX + 1, entityY - 1);
        board.addIfValid(zone, entityX + 1, entityY + 1);
    }

    public boolean inRestrictedArea(GameEntity entity, int x, int y) {
        // Verify if the position (x, y) is not from the playable zone of the entity
        return !entity.entityCombatStats.ownerZone(x, y);
    }
}
