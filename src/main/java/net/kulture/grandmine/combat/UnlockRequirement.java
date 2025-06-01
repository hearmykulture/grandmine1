package net.kulture.grandmine.combat;

import net.minecraft.world.entity.player.Player;

/**
 * Interface representing an unlock condition for combat styles or skills.
 */
public interface UnlockRequirement {

    /**
     * Determines if a player satisfies this unlock requirement.
     * @param player the player instance
     * @return true if unlocked
     */
    boolean isUnlocked(Player player);
}
