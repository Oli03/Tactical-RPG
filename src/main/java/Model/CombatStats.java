package Model;

import java.util.ArrayList;

public class Character {
    boolean main_character;
    Integer life;
    boolean finish_turn;
    ArrayList<Position> movableZone;

    Character(boolean is_main_character, ArrayList<Position> movableZone) {
        this.movableZone = movableZone;
        this.main_character = is_main_character;
        this.life = 10;
        this.finish_turn = !is_main_character;
    }

    public boolean ownerZone(int x, int y) {
        for (Position pos : movableZone) {
            if (x == pos.getX() && y == pos.getY()) return true;
        }

        return false;
    }

    void receive_damage(Integer damage) {
        this.life -= damage;
    }

    public boolean hisTurn() {
        return !this.finish_turn;
    }

    public void switchTurn() {
        this.finish_turn = !this.finish_turn;
    }
}
