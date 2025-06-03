package net.kulture.grandmine.item.custom;

import net.kulture.grandmine.combat.CombatStyles;
import net.kulture.grandmine.combat.player.PlayerDataProvider;
import net.kulture.grandmine.combat.player.SkillManager;
import net.kulture.grandmine.combat.skills.Skill;
import net.kulture.grandmine.combat.skills.SkillRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DebugStickItem extends Item {

    public DebugStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            SkillManager skillManager = PlayerDataProvider.get(player);

            if (skillManager != null) {
                // Set combat style to Brawler
                skillManager.setCurrentCombatStyle(CombatStyles.BRAWLER);
                System.out.println("Combat style set to: " + skillManager.getCurrentCombatStyle().getId());

                // Register and learn Punch skill
                Skill punchSkill = SkillRegistry.PUNCH;
                String punchId = punchSkill.getId();

                skillManager.learnSkill(SkillRegistry.PUNCH);
                skillManager.equipSkill(0, SkillRegistry.PUNCH);

                // Try to use the skill via the manager
                boolean success = skillManager.useSkill(0);

                if (success) {
                    player.displayClientMessage(Component.literal("Punch skill used!"), false);
                } else {
                    player.displayClientMessage(Component.literal("Punch skill is on cooldown."), false);
                }

            } else {
                player.displayClientMessage(Component.literal("SkillManager not found."), false);
            }
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
