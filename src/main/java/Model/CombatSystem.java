package Model;

import java.util.concurrent.ThreadLocalRandom;

public class CombatSystem {

    public CombatSystem() {}

    public int entityAttack(GameEntity attacker, GameEntity victim) {
        if (closeToEnemy(attacker, victim)) {
            return applyDamage(victim);
        }
        return -1; // No damage if not close to the enemy
    }

    private boolean closeToEnemy(GameEntity player, GameEntity enemy) {
        return (Math.abs(player.position.x - enemy.position.x)
                + Math.abs(player.position.y - enemy.position.y)) == 1;
    }

    private int applyDamage(GameEntity victim) {
        int damage = ThreadLocalRandom.current().nextInt(1, 6); // Random damage between 1 and 5

        victim.entityCombatStats.receive_damage(damage);
        if (victim.entityCombatStats.life <= 0) {
            // The victim was defeated.
            victim.position.removeCombatStats();
        }

        return damage;
    }
}
