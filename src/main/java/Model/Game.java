package Model;

public class Game {
    private final GameBoard board;
    public EntitySpawner spawner;
    private final MovementController controller;
    private final CombatSystem combatSystem;

    public GameEntity playerEntity;
    public GameEntity enemyEntity;

    public Game(int width, int height) {
        this.playerEntity = new GameEntity(false);
        this.enemyEntity = new GameEntity(true);

        this.board = new GameBoard(width, height);
        this.spawner = new EntitySpawner(board);
        spawner.placeEntity(playerEntity, width - 1, height - 1);
        spawner.placeEntity(enemyEntity, 0, 0);
        this.controller = new MovementController(board, spawner);
        this.combatSystem = new CombatSystem();
    }

    public int getWidth() {
        return board.width;
    }

    public int getHeight() {
        return board.height;
    }

    public Position getEntityPosition(boolean isEnemy) {
        return isEnemy ? enemyEntity.position : playerEntity.position;
    }

    public boolean isFinished() {
        return !enemyEntity.position.hasCombatStats() || !playerEntity.position.hasCombatStats();
    }

    public boolean isOccupiedByEnemy(int x, int y) {
        return board.isOccupiedByEntity(enemyEntity, x, y);
    }

    public boolean isOccupiedByPlayer(int x, int y) {
        return board.isOccupiedByEntity(playerEntity, x, y);
    }

    public boolean movePlayer(int newX, int newY) {
        return controller.movePlayer(playerEntity, newX, newY);
    }

    public void moveEnemy() {
        controller.moveEnemy(playerEntity, enemyEntity);
    }

    public int attack(boolean playerAttack) {
        if (playerAttack) {
            return combatSystem.entityAttack(playerEntity, enemyEntity);
        }

        return combatSystem.entityAttack(enemyEntity, playerEntity);
    }
}
