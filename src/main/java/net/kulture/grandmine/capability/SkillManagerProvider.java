package net.kulture.grandmine.capability;

import net.kulture.grandmine.combat.player.SkillManager;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.CapabilityToken;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SkillManagerProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<SkillManager> SKILL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final SkillManager instance = new SkillManager();
    private final LazyOptional<SkillManager> optional = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == SKILL_CAPABILITY ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.save();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.loadFromNBT(nbt);
    }
}
