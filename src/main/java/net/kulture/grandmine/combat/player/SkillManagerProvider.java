package net.kulture.grandmine.combat.player;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillManagerProvider {

    private static final Map<UUID, SkillManager> managers = new HashMap<>();

    public static SkillManager get(Player player) {
        return managers.computeIfAbsent(player.getUUID(), uuid -> new SkillManager(player));
    }
}
