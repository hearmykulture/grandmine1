package net.kulture.grandmine.client;

import net.kulture.grandmine.network.ClientToServerSkillUsePacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientKeyInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (Minecraft.getInstance().player == null) return;

        for (int i = 0; i < ClientSkillKeybinds.SKILL_KEYS.length; i++) {
            if (ClientSkillKeybinds.SKILL_KEYS[i].consumeClick()) {
                ClientToServerSkillUsePacket.send(i); // Send which slot was pressed
            }
        }
    }
}
