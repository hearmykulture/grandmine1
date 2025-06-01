package net.kulture.grandmine.event;

import net.kulture.grandmine.combat.player.SkillManager;
import net.kulture.grandmine.combat.player.SkillManagerProvider;
import net.kulture.grandmine.combat.skills.Skill;
import net.kulture.grandmine.combat.skills.SkillRegistry;
import net.kulture.grandmine.combat.CombatStyles;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraft.commands.arguments.EntityArgument.getPlayer;

public class PlayerJoinHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        SkillManager skillManager = SkillManagerProvider.get(player);

        // Set combat style to Brawler
        skillManager.setCurrentCombatStyle(CombatStyles.BRAWLER);

        // Get Geppo skill
        Skill geppo = SkillRegistry.getSkillById("geppo");
        if (geppo != null) {
            // Learn if not learned
            if (!skillManager.getData().hasLearned(geppo.getId())) {
                skillManager.learnSkill(geppo);
            }

            // Equip in slot 0
            skillManager.equipSkill(0, geppo);
        }
    }
}
