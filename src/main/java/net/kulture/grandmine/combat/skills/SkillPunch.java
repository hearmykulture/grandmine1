package net.kulture.grandmine.combat.skills;

import net.kulture.grandmine.combat.CombatStyles;
import net.kulture.grandmine.combat.player.SkillManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class SkillPunch extends Skill {

    public SkillPunch() {
        super(
                "grandmine:punch",                        // example skill id
                "Punch",                        // name
                "Basic punch attack",           // description
                SkillType.OFFENSIVE,               // skill type enum
                List.of(CombatStyles.BRAWLER), // list of applicable combat styles
                1.0f,                          // damage multiplier
                1.0f,                          // speed multiplier
                1.0f,                          // range multiplier
                0,                             // some int param
                new ResourceLocation("grandmine", "textures/icons/punch.png"), // icon
                List.of("melee", "basic"),     // tags or keywords
                null       // custom effect instance
        );
    }


    @Override
    public boolean execute(Player player, SkillManager manager) {
        if (player == null) {
            System.err.println("SkillPunch.execute called with null player!");
            return false;
        }
        Level level = player.level();


        // Basic punch effect (for now, just knockback + cooldown)
        player.swing(player.getUsedItemHand(), true);

        // Example: Add a basic forward knockback to entities in front
        float range = 2.5f;
        float knockback = 1.5f;

        level.getEntities(player, player.getBoundingBox().inflate(range), e -> e != player)
                .forEach(entity -> {
                    double dx = entity.getX() - player.getX();
                    double dz = entity.getZ() - player.getZ();
                    double mag = Math.sqrt(dx * dx + dz * dz);
                    if (mag > 0) {
                        entity.setDeltaMovement(dx / mag * knockback, 0.4, dz / mag * knockback);
                        entity.hurt(player.damageSources().playerAttack(player), 3.0f);
                    }
                });

        // Set cooldown (example: 1.5 seconds)
        manager.setCooldown(this.getId(), 30);


        player.sendSystemMessage(Component.literal("Punch skill used!"));
        manager.getData().setCooldown(getId(), 40); // 2 seconds
        return true;
    }
}
