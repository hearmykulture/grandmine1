package net.kulture.grandmine.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kulture.grandmine.Grandmine;
import net.kulture.grandmine.client.ClientSkillKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Grandmine.MODID, value = Dist.CLIENT)
public class SkillHudRenderer {

    private static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(Grandmine.MODID, "textures/gui/slot_frame.png");
    private static final ResourceLocation OVERLAY_DIM = new ResourceLocation(Grandmine.MODID, "textures/gui/slot_overlay.png");

    private static final ResourceLocation[] SKILL_ICONS = new ResourceLocation[] {
            new ResourceLocation(Grandmine.MODID, "textures/gui/skill_0.png"),
            new ResourceLocation(Grandmine.MODID, "textures/gui/skill_1.png"),
            new ResourceLocation(Grandmine.MODID, "textures/gui/skill_2.png"),
            new ResourceLocation(Grandmine.MODID, "textures/gui/skill_3.png"),
            new ResourceLocation(Grandmine.MODID, "textures/gui/skill_4.png"),
    };

    @SubscribeEvent
    public static void onRenderHud(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();
        LocalPlayer player = mc.player;
        if (player == null) return;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int current = ClientSkillKeybinds.getCurrentSkillSlot();
        int previous = (current - 1 + SKILL_ICONS.length) % SKILL_ICONS.length;
        int next = (current + 1) % SKILL_ICONS.length;

        int baseX = 10;
        int baseY = screenHeight - 44; // Slightly lowered

        drawIcon(guiGraphics, SLOT_TEXTURE, SKILL_ICONS[current], baseX, baseY, 32, 32, 1.0f, false);
        drawIcon(guiGraphics, SLOT_TEXTURE, SKILL_ICONS[previous], baseX, baseY - 28, 32, 32, 0.75f, true);
        drawIcon(guiGraphics, SLOT_TEXTURE, SKILL_ICONS[next], baseX + 35, baseY + 12, 32, 32, 0.75f, true);
    }

    private static void drawIcon(GuiGraphics guiGraphics, ResourceLocation frame, ResourceLocation icon, int x, int y, int width, int height, float scale, boolean dimmed) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x, y, 0);
        guiGraphics.pose().scale(scale, scale, 1.0f);

        int drawX = 0;
        int drawY = 0;

        guiGraphics.blit(frame, drawX, drawY, 0, 0, width, height, width, height);
        guiGraphics.blit(icon, drawX, drawY, 0, 0, width, height, width, height);

        if (dimmed) {
            guiGraphics.blit(OVERLAY_DIM, drawX, drawY, 0, 0, width, height, width, height);
        }

        guiGraphics.pose().popPose();
    }
}
