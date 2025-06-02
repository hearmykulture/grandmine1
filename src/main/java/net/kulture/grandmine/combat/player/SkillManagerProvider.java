package net.kulture.grandmine.combat.player;

import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SkillManagerProvider {

    private static final Map<UUID, SkillManager> managers = new ConcurrentHashMap<>();

    public static SkillManager get(Player player) {
        return managers.computeIfAbsent(player.getUUID(), uuid -> new SkillManager(player));
    }

    public static void remove(Player player) {
        managers.remove(player.getUUID());
    }
}
