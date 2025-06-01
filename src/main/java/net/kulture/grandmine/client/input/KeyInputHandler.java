package net.kulture.grandmine.client.input;

import net.kulture.grandmine.client.gui.MyFirstScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "grandmine", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (KeyBindings.openGuiKey != null && KeyBindings.openGuiKey.consumeClick()) {
                Minecraft.getInstance().setScreen(new MyFirstScreen());
            }
        }
    }
}
