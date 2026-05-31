package dev.sixik.gameprogressionore.impl.mixin.packets;

import dev.sixik.gameprogressionore.api.BlockRestrictionHelper;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientboundSectionBlocksUpdatePacket.class)
public class MixinClientboundSectionBlocksUpdatePacket {

    @Shadow
    @Final
    private SectionPos sectionPos;

    @Shadow
    @Final
    private short[] positions;

    @Shadow
    @Final
    private BlockState[] states;

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    private void gpo$write$replaceHiddenBlocks(FriendlyByteBuf buffer, CallbackInfo ci) {
        buffer.writeLong(this.sectionPos.asLong());
        buffer.writeVarInt(this.positions.length);

        for (int i = 0; i < this.positions.length; ++i) {
            final int blockId = BlockIdGetter.get(this.states[i]).gpo$getFastId();
            final int replacementId = BlockRestrictionHelper.getReplacementBlock(blockId);

            buffer.writeVarLong(((long) replacementId << 12) | (long) this.positions[i]);
        }

        ci.cancel();
    }
}
