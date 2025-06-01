package net.kulture.grandmine.item.custom;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import net.kulture.grandmine.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static net.kulture.grandmine.particles.ModParticles.GEORGE;


public class DebugStickItem extends Item {

    public DebugStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // Ray trace to get block player is looking at (up to 20 blocks)
        HitResult hitResult = player.pick(20.0D, 0.0F, false);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            BlockPos blockPos = blockHit.getBlockPos();

            if (level.isClientSide()) {
                // Show the particle effect client-side
                AAALevel.addParticle(level, false, GEORGE.clone().position(
                        blockPos.getX() + 0.5d,
                        blockPos.getY() + 1d,
                        blockPos.getZ() + 0.5d
                ));
                player.sendSystemMessage(Component.literal("Placed particle at: " + blockPos));
            } else {
                // Do the explosion server-side
                level.explode(
                        player,                             // Explosion source
                        blockPos.getX() + 0.5d,
                        blockPos.getY() + 1d,
                        blockPos.getZ() + 0.5d,
                        6.0f,                               // Explosion strength
                        Level.ExplosionInteraction.BLOCK    // Can destroy blocks
                );
            }
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }



}
