package net.kulture.grandmine.combat.skills;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface SkillEffect {
    void apply(Player player);
}
