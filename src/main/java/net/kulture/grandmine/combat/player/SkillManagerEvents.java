package net.kulture.grandmine.combat.player;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

@Mod.EventBusSubscriber
public class SkillManagerEvents {

    private static final String SKILL_MANAGER_TAG = "GrandMineSkillManager";

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CompoundTag persistentData = player.getPersistentData();

        if (persistentData.contains(SKILL_MANAGER_TAG)) {
            CompoundTag skillTag = persistentData.getCompound(SKILL_MANAGER_TAG);
            SkillManager manager = SkillManagerProvider.get(player);
            manager.loadFromNBT(skillTag);
        } else {
            // Initialize skill manager if no data yet
            SkillManagerProvider.get(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player original = event.getOriginal();
        Player clone = event.getEntity();

        SkillManager originalManager = SkillManagerProvider.get(original);
        SkillManager cloneManager = SkillManagerProvider.get(clone);

        cloneManager.loadFromNBT(originalManager.save());
    }


    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        SkillManagerProvider.remove(player);
    }

    @SubscribeEvent
    public static void onPlayerTick(net.minecraftforge.event.TickEvent.PlayerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;

        Player player = event.player;
        SkillManager manager = SkillManagerProvider.get(player);

        manager.getData().tick(); // We'll add this method next
    }
}
