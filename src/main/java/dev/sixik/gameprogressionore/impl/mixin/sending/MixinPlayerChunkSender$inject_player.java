package dev.sixik.gameprogressionore.impl.mixin.sending;

import dev.sixik.gameprogressionore.impl.utils.GPOChunkUtils;
import dev.sixik.gpf.impl.server.PlayerStageDataService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.PlayerChunkSender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerChunkSender.class)
public class MixinPlayerChunkSender$inject_player {

    @Inject(method = "sendNextChunks", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;serverLevel()Lnet/minecraft/server/level/ServerLevel;"))
    public void gpo$sendNextChunks$start(ServerPlayer player, CallbackInfo ci) {
        GPOChunkUtils.currentPlayer = player;
        GPOChunkUtils.currentPlayerData = PlayerStageDataService.getOrCreate(player);
    }
}
