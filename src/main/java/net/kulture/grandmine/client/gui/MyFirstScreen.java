package net.kulture.grandmine.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MyFirstScreen extends Screen {

    public MyFirstScreen() {
        super(Component.literal("My First Screen!"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // Renders a default dark background
        this.renderBackground(graphics);

        // Draw the title
        graphics.drawCenteredString(this.font, "Hello GrandMine!", this.width / 2, this.height / 2, 0xFFFFFF);

        // Call super to render buttons/widgets
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
}
