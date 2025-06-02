package net.kulture.grandmine.capability;

import net.kulture.grandmine.combat.player.SkillManager;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class defines and registers the SkillManager capability.
 */
public class CapabilitySkillManager {

    public static final Capability<SkillManager> SKILL_MANAGER_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<SkillManager>() {
            });
}
