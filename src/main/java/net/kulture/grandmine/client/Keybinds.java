package net.kulture.grandmine.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.kulture.grandmine.network.ClientToServerSkillUsePacket;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

// Register ONLY to the MOD event bus for things like RegisterKeyMappingsEvent
@Mod.EventBusSubscriber(modid = "grandmine", bus = Mod.EventBusSubscriber.Bus.MOD, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class Keybinds {

    public static final KeyMapping TEST_SKILL_KEY = new KeyMapping(
            "key.grandmine.test_skill",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "key.categories.grandmine"
    );

    // This is fine on the MOD bus (it fires during mod loading)
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(TEST_SKILL_KEY);
    }

    // Call this during client setup to listen for runtime input events
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }

    // Separate handler for runtime key input — registered on the FORGE bus
    public static class KeyInputHandler {
        @SubscribeEvent
        public void onKeyInput(InputEvent.Key event) {
            if (TEST_SKILL_KEY.isDown()) {
                ClientToServerSkillUsePacket.send(0); // Trigger skill for slot 0
                System.out.println("Pressed TEST_SKILL_KEY!"); // Debug message to confirm it's working
            }
        }
    }
}
