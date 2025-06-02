package net.kulture.grandmine.client;

public class SkillSlotManager {
    private static int selectedSlot = 0;
    private static final int MAX_SLOTS = 5;

    // Returns the current selected slot (0-based)
    public static int getSelectedSlot() {
        return selectedSlot;
    }

    // Returns the current selected slot as 1-based (for UI or debug)
    public static int getSelectedSlotOneBased() {
        return selectedSlot + 1;
    }

    // Select next slot cyclically
    public static void nextSlot() {
        selectedSlot = (selectedSlot + 1) % MAX_SLOTS;
        System.out.println("Selected skill slot: " + getSelectedSlotOneBased());
    }

    // Select previous slot cyclically
    public static void previousSlot() {
        selectedSlot = (selectedSlot - 1 + MAX_SLOTS) % MAX_SLOTS;
        System.out.println("Selected skill slot: " + getSelectedSlotOneBased());
    }

    // Set the selected slot explicitly if in valid range
    public static void setSelectedSlot(int slot) {
        if (slot >= 0 && slot < MAX_SLOTS) {
            selectedSlot = slot;
            System.out.println("Selected skill slot set to: " + getSelectedSlotOneBased());
        } else {
            System.out.println("Attempted to set invalid skill slot: " + slot);
        }
    }
}
