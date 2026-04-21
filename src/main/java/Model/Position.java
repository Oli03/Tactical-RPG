package Model;

public class Position {
    int x;
    int y;
    public CombatStats combatStats;

    Position(int x, int y, CombatStats combatStats) {
        this.x = x;
        this.y = y;
        this.combatStats = combatStats;
    }

    void addCombatStats(CombatStats combatStats) {
        // An entity is at this position
        this.combatStats = combatStats;
    }

    CombatStats removeCombatStats() {
        // This position doesn't have an entity
        if (this.combatStats == null) return null;

        // Auxiliary to return the combat stats and remove it from the position
        CombatStats temp = this.combatStats;
        this.combatStats = null;
        return temp;
    }

    boolean hasCombatStats() {
        // Check if the position contains an entity
        return this.combatStats != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
