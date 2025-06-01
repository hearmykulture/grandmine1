package net.kulture.grandmine.combat;

import net.minecraft.world.entity.player.Player;

public class AlwaysUnlockedRequirement implements UnlockRequirement {
    @Override
    public boolean isUnlocked(Player player) {
        return true;
    }
}
