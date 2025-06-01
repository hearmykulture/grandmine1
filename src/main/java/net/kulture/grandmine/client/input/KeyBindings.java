package net.kulture.grandmine.client.input;

import net.kulture.grandmine.client.gui.MyFirstScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "grandmine", bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {
    public static KeyMapping openGuiKey;

    // Called during mod setup to define the key
    public static void register(RegisterKeyMappingsEvent event) {
        openGuiKey = new KeyMapping(
                "key.grandmine.open_gui", // translation key
                GLFW.GLFW_KEY_G, // default key: G
                "key.categories.misc" // category in Controls screen
        );
        event.register(openGuiKey);
    }
}
