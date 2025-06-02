package net.kulture.grandmine.network;

import net.kulture.grandmine.combat.player.SkillManagerProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientToServerSkillUsePacket {
    private final int slot;

    public ClientToServerSkillUsePacket(int slot) {
        this.slot = slot;
    }

    public static void send(int slot) {
        NetworkHandler.sendToServer(new ClientToServerSkillUsePacket(slot));
    }

    public static ClientToServerSkillUsePacket decode(FriendlyByteBuf buf) {
        return new ClientToServerSkillUsePacket(buf.readInt());
    }

    public static void encode(ClientToServerSkillUsePacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.slot);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                var data = SkillManagerProvider.get(player).getData();
                String skillId = data.getEquippedSkill(slot);
                if (skillId != null && !skillId.isEmpty()) {
                    if (data.tryActivateSkill(skillId, 60)) {
                        player.sendSystemMessage(Component.literal("Used " + skillId));
                        // TODO: Trigger skill logic (particles, effects, etc.)
                    } else {
                        player.sendSystemMessage(Component.literal(skillId + " is still cooling down."));
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

