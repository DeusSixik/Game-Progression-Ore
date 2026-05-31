package dev.sixik.gameprogressionore.impl.mixin.packets;

import dev.sixik.gameprogressionore.impl.utils.GPOChunkUtils;
import dev.sixik.gpf.api.StageData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientboundLevelChunkPacketData.class)
public abstract class MixinClientboundLevelChunkPacketData {

    @Shadow
    protected abstract ByteBuf getWriteBuffer();

    @Unique
    private StageData gpo$getPlayerData;

    @Inject(method = "<init>(Lnet/minecraft/world/level/chunk/LevelChunk;)V", at = @At(value = "NEW", target = "()Lnet/minecraft/nbt/CompoundTag;"))
    public void gpo$init$main(LevelChunk levelChunk, CallbackInfo ci) {
        gpo$getPlayerData = GPOChunkUtils.currentPlayerData;
    }

    @Redirect(method = "<init>(Lnet/minecraft/world/level/chunk/LevelChunk;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundLevelChunkPacketData;extractChunkData(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/world/level/chunk/LevelChunk;)V"))
    public void gpo$init$null_extract(FriendlyByteBuf levelchunksection, LevelChunk buffer) { }

    @Inject(method = "<init>(Lnet/minecraft/world/level/chunk/LevelChunk;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundLevelChunkPacketData;extractChunkData(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/world/level/chunk/LevelChunk;)V"))
    public void gpo$init(LevelChunk levelChunk, CallbackInfo ci) {
        gpo$extractChunkData(new FriendlyByteBuf(getWriteBuffer()), levelChunk);
    }

    @Unique
    private void gpo$extractChunkData(FriendlyByteBuf buffer, LevelChunk chunk) {
        GPOChunkUtils.setPacketStageData(gpo$getPlayerData);
        try {
            for(LevelChunkSection levelchunksection : chunk.getSections()) {
                levelchunksection.write(buffer);
            }
        } finally {
            GPOChunkUtils.clearPacketStageData();
        }
    }
}
