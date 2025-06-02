package net.kulture.grandmine.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSkillKeybinds {

    public static final KeyMapping[] SKILL_KEYS = new KeyMapping[5];

    public static void register(RegisterKeyMappingsEvent event) {
        for (int i = 0; i < SKILL_KEYS.length; i++) {
            SKILL_KEYS[i] = new KeyMapping("key.grandmine.skill_" + (i + 1),
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_1 + i,
                    "key.categories.grandmine");
            event.register(SKILL_KEYS[i]);
        }
    }
}

