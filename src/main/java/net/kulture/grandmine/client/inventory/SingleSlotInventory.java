package net.kulture.grandmine.client.inventory;

import net.minecraft.world.item.ItemStack;

public class SingleSlotInventory {
    private ItemStack stack = ItemStack.EMPTY;

    public ItemStack getItem() {
        return stack;
    }

    public void setItem(ItemStack stack) {
        this.stack = stack;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
