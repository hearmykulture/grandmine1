package net.kulture.grandmine.combat.skills.requirements;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.kulture.grandmine.combat.skills.SkillContextRequirement;

public class RequiresSword implements SkillContextRequirement {
    @Override
    public boolean isMet(Player player) {
        return player.getMainHandItem().getItem() instanceof SwordItem;
    }
}