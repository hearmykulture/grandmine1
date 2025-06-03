package net.kulture.grandmine.client;

public class SkillSlotManager {
    private static int selectedSlot = 0;
    private static int maxSlots = 5; // default value

    // Returns the current selected slot (0-based)
    public static int getSelectedSlot() {
        return selectedSlot;
    }

    // Returns the current selected slot as 1-based (for UI/debug)
    public static int getSelectedSlotOneBased() {
        return selectedSlot + 1;
    }

    // Update max slots dynamically from combat style
    public static void setMaxSlots(int slots) {
        maxSlots = Math.max(1, slots);
        if (selectedSlot >= maxSlots) {
            selectedSlot = 0;
        }
    }

    // Get current max slots
    public static int getMaxSlots() {
        return maxSlots;
    }

    // Select next slot cyclically
    public static void nextSlot() {
        selectedSlot = (selectedSlot + 1) % maxSlots;
        System.out.println("Selected skill slot: " + getSelectedSlotOneBased());
    }

    // Select previous slot cyclically
    public static void previousSlot() {
        selectedSlot = (selectedSlot - 1 + maxSlots) % maxSlots;
        System.out.println("Selected skill slot: " + getSelectedSlotOneBased());
    }

    // Set the selected slot explicitly if in valid range
    public static void setSelectedSlot(int slot) {
        if (slot >= 0 && slot < maxSlots) {
            selectedSlot = slot;
            System.out.println("Selected skill slot set to: " + getSelectedSlotOneBased());
        } else {
            System.out.println("Attempted to set invalid skill slot: " + slot);
        }
    }
}
