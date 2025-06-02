package net.kulture.grandmine.combat.events;

import net.kulture.grandmine.combat.player.SkillManagerProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerCombatEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            SkillManagerProvider.get(event.player).getData().tick();  // ← ticks cooldowns
        }
    }
}
