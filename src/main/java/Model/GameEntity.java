package Model;

import java.util.ArrayList;

public class GameEntity {
    public Position position;
    public CombatStats entityCombatStats;
    public ArrayList<Position> playZone;
    public final boolean isEnemy;

    public GameEntity(boolean isEnemy) {
        this.isEnemy = isEnemy;
        this.playZone = new ArrayList<>();
    }
}
