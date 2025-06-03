package net.kulture.grandmine.combat.player;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillHandler {
    private static final Map<UUID, Integer> skillCooldowns = new HashMap<>();

    public static boolean isOnCooldown(Player player) {
        return skillCooldowns.getOrDefault(player.getUUID(), 0) > 0;
    }

    public static void setCooldown(Player player, int ticks) {
        skillCooldowns.put(player.getUUID(), ticks);
    }

    public static void tickCooldowns() {
        skillCooldowns.replaceAll((uuid, ticks) -> Math.max(ticks - 1, 0));
    }
}

