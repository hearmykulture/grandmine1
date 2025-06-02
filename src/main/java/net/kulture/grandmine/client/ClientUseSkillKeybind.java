package net.kulture.grandmine.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class ClientUseSkillKeybind {
    public static KeyMapping USE_SKILL_KEY;

    public static void register(RegisterKeyMappingsEvent event) {
        USE_SKILL_KEY = new KeyMapping("key.grandmine.use_skill", GLFW.GLFW_KEY_R, "key.categories.grandmine");
        event.register(USE_SKILL_KEY);
    }

    public static void onClientTick(Minecraft mc) {
        if (USE_SKILL_KEY.consumeClick()) {
            mc.player.displayClientMessage(Component.literal("Used skill: Geppo"), true);
            ClientSkillKeybinds.useCurrentSkill(); // your logic for Geppo or current skill
        }
    }
}

