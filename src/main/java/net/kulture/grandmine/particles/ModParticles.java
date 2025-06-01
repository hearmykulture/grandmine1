package net.kulture.grandmine.particles;

import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.resources.ResourceLocation;

public class ModParticles {
    public static ParticleEmitterInfo GEORGE;

    public static void registerParticles() {
        GEORGE = new ParticleEmitterInfo(
                new ResourceLocation("grandmine", "george")
        );
    }
}