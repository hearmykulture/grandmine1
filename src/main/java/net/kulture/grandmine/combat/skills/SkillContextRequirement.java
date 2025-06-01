package net.kulture.grandmine.combat.skills;

import net.minecraft.world.entity.player.Player;

public interface SkillContextRequirement {
    boolean isMet(Player player);
}