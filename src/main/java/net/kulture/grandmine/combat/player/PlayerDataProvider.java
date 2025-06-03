package net.kulture.grandmine.combat.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerDataProvider implements ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation ID = new ResourceLocation("grandmine", "player_data");

    public static final Capability<SkillManager> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final SkillManager skillManager = new SkillManager();
    private final LazyOptional<SkillManager> optional = LazyOptional.of(() -> skillManager);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CAPABILITY ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        skillManager.serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        skillManager.deserializeNBT(nbt);
    }

    public static SkillManager get(Player player) {
        return player.getCapability(CAPABILITY).orElseThrow(() ->
                new IllegalStateException("SkillManager capability not present for player: " + player.getName().getString()));
    }
}
