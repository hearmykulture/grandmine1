package net.kulture.grandmine.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class ClientCycleSkillKeys {

    public static KeyMapping NEXT_SKILL_KEY;
    public static KeyMapping PREV_SKILL_KEY;

    private static final int MAX_SLOTS = 5;
    private static int currentSkillSlot = 0;

    public static void register(RegisterKeyMappingsEvent event) {
        NEXT_SKILL_KEY = new KeyMapping("key.grandmine.next_skill",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "key.categories.grandmine");
        PREV_SKILL_KEY = new KeyMapping("key.grandmine.prev_skill",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                "key.categories.grandmine");

        event.register(NEXT_SKILL_KEY);
        event.register(PREV_SKILL_KEY);
    }

    public static void onClientTick(Minecraft mc) {
        if (mc.player == null || mc.screen != null) return;

        if (NEXT_SKILL_KEY.consumeClick()) {
            incrementSkillSlot();
            mc.player.displayClientMessage(Component.literal("Selected next skill slot: " + (currentSkillSlot + 1)), true);
        }

        if (PREV_SKILL_KEY.consumeClick()) {
            decrementSkillSlot();
            mc.player.displayClientMessage(Component.literal("Selected previous skill slot: " + (currentSkillSlot + 1)), true);
        }
    }

    private static void incrementSkillSlot() {
        currentSkillSlot = (currentSkillSlot + 1) % MAX_SLOTS;
    }

    private static void decrementSkillSlot() {
        currentSkillSlot = (currentSkillSlot - 1 + MAX_SLOTS) % MAX_SLOTS;
    }

    public static int getCurrentSkillSlot() {
        return currentSkillSlot;
    }
}
