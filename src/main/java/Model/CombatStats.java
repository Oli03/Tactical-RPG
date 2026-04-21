package Model;

import java.util.ArrayList;

public class CombatStats {
    Integer life;
    boolean finish_turn;
    // The zone where the entity can move and interact in the current turn.
    ArrayList<Position> playZone;

    CombatStats(boolean isEnemy, ArrayList<Position> playZone) {
        this.playZone = playZone;
        this.life = 10;
        this.finish_turn = isEnemy;
    }

    public boolean ownerZone(int x, int y) {
        for (Position pos : playZone) {
            // Check that the position (x, y) is within the playable area.
            if (x == pos.getX() && y == pos.getY()) return true;
        }

        return false;
    }

    void receive_damage(Integer damage) {
        this.life -= damage;
    }

    public boolean hisTurn() {
        // Beginning of a new turn.
        return !this.finish_turn;
    }

    public void switchTurn() {
        this.finish_turn = !this.finish_turn;
    }
}
