package dev.sixik.gameprogressionore.impl.mixin.sending;

import dev.sixik.gameprogressionore.impl.utils.GPOChunkUtils;
import dev.sixik.gpf.api.StageData;
import dev.sixik.gpf.impl.server.PlayerStageDataService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Connection.class)
public abstract class MixinConnection {

    @Shadow
    private volatile PacketListener packetListener;

    @Redirect(method = "doSendPacket", at = @At(value = "INVOKE", target = "Lio/netty/channel/Channel;writeAndFlush(Ljava/lang/Object;)Lio/netty/channel/ChannelFuture;"))
    private ChannelFuture gpo$writeAndFlushWithStageContext(Channel channel, Object packet) {
        GPOChunkUtils.setPacketStageData(gpo$getCurrentStageData());
        try {
            return channel.writeAndFlush(packet);
        } finally {
            GPOChunkUtils.clearPacketStageData();
        }
    }

    @Redirect(method = "doSendPacket", at = @At(value = "INVOKE", target = "Lio/netty/channel/Channel;write(Ljava/lang/Object;)Lio/netty/channel/ChannelFuture;"))
    private ChannelFuture gpo$writeWithStageContext(Channel channel, Object packet) {
        GPOChunkUtils.setPacketStageData(gpo$getCurrentStageData());
        try {
            return channel.write(packet);
        } finally {
            GPOChunkUtils.clearPacketStageData();
        }
    }

    @Unique
    private StageData gpo$getCurrentStageData() {
        if (this.packetListener instanceof ServerGamePacketListenerImpl gamePacketListener) {
            return PlayerStageDataService.getOrCreate(gamePacketListener.player);
        }

        return null;
    }
}
