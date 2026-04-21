package Model;

public class MovementController {
    private final GameBoard board;
    private final EntitySpawner spawner;

    public MovementController(GameBoard board, EntitySpawner spawner) {
        this.board = board;
        this.spawner = spawner;
    }

    public boolean movePlayer(GameEntity player, int newX, int newY) {
        // Check if the new position is valid, is within the playable zone and does not contain an enemy
        if (!board.isValidPosition(newX, newY) || spawner.inRestrictedArea(player, newX, newY) || board.getPosition(newX, newY).hasCombatStats()) return false;

        // Change position
        CombatStats mainCombatStats = player.position.removeCombatStats();
        player.position = board.getPosition(newX, newY);
        player.position.addCombatStats(mainCombatStats);

        return true;
    }

    public void moveEnemy(GameEntity player, GameEntity enemy) {
        Pair<Integer, Integer> newPosition = calculateEnemyDirection(player, enemy);

        // Set new position
        int newX = enemy.position.getX() + newPosition.first();
        int newY = enemy.position.getY() + newPosition.second();

        // Check if the new position is valid, is within the playable zone and does not contain the player
        if (!board.isValidPosition(newX, newY) || spawner.inRestrictedArea(enemy, newX, newY) || board.getPosition(newX, newY).hasCombatStats()) return;

        // Change position
        CombatStats enemyStats = enemy.position.removeCombatStats();
        enemy.position = board.getPosition(newX, newY);
        enemy.position.addCombatStats(enemyStats);

        // Calculate new playable zone for the enemy
        spawner.calculateEntityZone(enemy, newX, newY);
    }

    private Pair<Integer, Integer> calculateEnemyDirection(GameEntity player, GameEntity enemy) {
        // Calculate the difference in position between the player and the enemy
        int differenceX = player.position.getX() - enemy.position.getX();
        int differenceY = player.position.getY() - enemy.position.getY();

        // Calculate the absolute values of the differences
        int absDx = Math.abs(differenceX);
        int absDy = Math.abs(differenceY);

        int directionX = 0;
        int directionY = 0;

        if (absDx > 2 || absDy > 2) {
            // If the player is far away, move in the direction of the player with a step of 2
            if (absDx >= absDy) {
                directionX = 2 * Integer.signum(differenceX);
            } else {
                directionY = 2 * Integer.signum(differenceY);
            }
        } else if (absDx == 2 && differenceY == 0) {
            // If the player is exactly 2 steps away horizontally, move 1 step in that direction
            directionX = Integer.signum(differenceX);
        } else if (differenceX == 0 && absDy == 2) {
            // If the player is exactly 2 steps away vertically, move 1 step in that direction
            directionY = Integer.signum(differenceY);
        } else if (absDx == 1 && absDy == 1) {
            // If the player is diagonally adjacent, move towards the player
            directionX = Integer.signum(differenceX);
        } else {
            // In any case, the enemy moves diagonally one step towards the player
            directionX = Integer.signum(differenceX);
            directionY = Integer.signum(differenceY);
        }

        return new Pair<>(directionX, directionY);
    }
}
