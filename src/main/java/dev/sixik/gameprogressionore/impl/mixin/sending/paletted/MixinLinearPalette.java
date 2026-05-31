package dev.sixik.gameprogressionore.impl.mixin.sending.paletted;

import dev.sixik.gameprogressionore.api.BlockRestrictionHelper;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LinearPalette;
import net.minecraft.world.level.chunk.Palette;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LinearPalette.class)
public abstract class MixinLinearPalette<T> implements Palette<T> {

    @Shadow
    @Final
    private T[] values;

    @Shadow
    private int size;

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    public void gpo$write$replace_block(FriendlyByteBuf buf, CallbackInfo ci) {
        if(this.values.length == 0 || !(this.values[0] instanceof BlockState)) return;

        buf.writeVarInt(this.size);

        for (int i = 0; i < this.size; ++i) {
            buf.writeVarInt(BlockRestrictionHelper.getReplacementBlock(BlockIdGetter.get((BlockState) this.values[i]).gpo$getFastId()));
        }

        ci.cancel();
    }


    @Inject(method = "getSerializedSize", at = @At("HEAD"), cancellable = true)
    public void gpo$getSerializedSize$replace_block(CallbackInfoReturnable<Integer> cir) {
        if(this.values.length == 0 || !(this.values[0] instanceof BlockState)) return;
        int resultSize = VarInt.getByteSize(this.getSize());
        for (int index = 0; index < this.getSize(); ++index) {
            resultSize += VarInt.getByteSize(
                    BlockRestrictionHelper.getReplacementBlock(
                            BlockIdGetter.get((BlockState) this.values[index]).gpo$getFastId()
                    )
            );
        }
        cir.setReturnValue(resultSize);
    }
}
