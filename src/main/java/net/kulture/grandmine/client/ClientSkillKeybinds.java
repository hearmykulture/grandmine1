package net.kulture.grandmine.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class ClientSkillKeybinds {

    public static KeyMapping USE_SKILL_KEY;
    public static KeyMapping NEXT_SLOT_KEY;
    public static KeyMapping PREV_SLOT_KEY;

    public static final int MAX_SLOTS = 5;

    private static int currentSkillSlot = 0;

    // This method should be called from FMLClientSetupEvent or a client initializer, not subscribed.
    public static void register(RegisterKeyMappingsEvent event) {
        USE_SKILL_KEY = new KeyMapping("key.grandmine.use_skill",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "key.categories.grandmine");
        event.register(USE_SKILL_KEY);

        NEXT_SLOT_KEY = new KeyMapping("key.grandmine.next_slot",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "key.categories.grandmine");
        event.register(NEXT_SLOT_KEY);

        PREV_SLOT_KEY = new KeyMapping("key.grandmine.prev_slot",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                "key.categories.grandmine");
        event.register(PREV_SLOT_KEY);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        if (USE_SKILL_KEY.consumeClick()) {
            mc.player.displayClientMessage(Component.literal("Used skill: " + (currentSkillSlot + 1)), true);
        }

        if (NEXT_SLOT_KEY.consumeClick()) {
            currentSkillSlot = (currentSkillSlot + 1) % MAX_SLOTS;
            mc.player.displayClientMessage(Component.literal("Switched to skill slot: " + (currentSkillSlot + 1)), true);
        }

        if (PREV_SLOT_KEY.consumeClick()) {
            currentSkillSlot = (currentSkillSlot - 1 + MAX_SLOTS) % MAX_SLOTS;
            mc.player.displayClientMessage(Component.literal("Switched to skill slot: " + (currentSkillSlot + 1)), true);
        }
    }

    public static void useCurrentSkill() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal("Used skill: " + (currentSkillSlot + 1)), true);
        }
    }

    public static int getCurrentSkillSlot() {
        return currentSkillSlot;
    }
}
