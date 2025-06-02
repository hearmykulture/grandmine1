package net.kulture.grandmine.client;

import net.kulture.grandmine.network.ClientToServerSkillUsePacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientKeyInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (Minecraft.getInstance().player == null) return;

        // Check your use skill key (R) to activate selected slot
        if (ClientUseSkillKeybind.USE_SKILL_KEY.consumeClick()) {
            int slot = SkillSlotManager.getSelectedSlot();
            ClientToServerSkillUsePacket.send(slot);
            System.out.println("Used skill slot: " + slot);
        }

        // Optional: keys to cycle slots with F and G
        if (ClientCycleSkillKeys.NEXT_SKILL_KEY.consumeClick()) {
            SkillSlotManager.nextSlot();
        }
        if (ClientCycleSkillKeys.PREV_SKILL_KEY.consumeClick()) {
            SkillSlotManager.previousSlot();
        }
    }
}
