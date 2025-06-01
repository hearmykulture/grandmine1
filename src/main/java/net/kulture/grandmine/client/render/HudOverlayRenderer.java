package net.kulture.grandmine.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kulture.grandmine.Grandmine;
import net.kulture.grandmine.client.inventory.SingleSlotInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Grandmine.MODID, value = Dist.CLIENT)
public class HudOverlayRenderer {

    private static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(Grandmine.MODID, "textures/gui/single_slot.png");

    // Our internal inventory instance
    private static final SingleSlotInventory inventory = new SingleSlotInventory();



    @SubscribeEvent
    public static void onRenderHud(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        inventory.setItem(Minecraft.getInstance().player.getInventory().getSelected());

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x = screenWidth - 20;
        int y = screenHeight - 20;

        guiGraphics.blit(SLOT_TEXTURE, x, y, 0, 0, 18, 18, 18, 18);

        if (!inventory.isEmpty()) {
            guiGraphics.renderItem(inventory.getItem(), x + 1, y + 1);
        }
    }
}
