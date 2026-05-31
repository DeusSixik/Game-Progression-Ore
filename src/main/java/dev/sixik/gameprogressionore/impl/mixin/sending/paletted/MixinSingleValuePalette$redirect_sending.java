package dev.sixik.gameprogressionore.impl.mixin.sending.paletted;

import dev.sixik.gameprogressionore.api.BlockRestrictionHelper;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.SingleValuePalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(SingleValuePalette.class)
public abstract class MixinSingleValuePalette$redirect_sending<T> implements Palette<T> {

    @Shadow
    @Nullable
    private T value;

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    public void gpo$write$replace_block(FriendlyByteBuf buf, CallbackInfo ci) {
        if(!(value instanceof BlockState state)) return;

        buf.writeVarInt(BlockRestrictionHelper.getReplacementBlock((BlockIdGetter.get(state).gpo$getFastId())));

        ci.cancel();
    }


    @Inject(method = "getSerializedSize", at = @At("HEAD"), cancellable = true)
    public void gpo$getSerializedSize$replace_block(CallbackInfoReturnable<Integer> cir) {
        if(!(this.value instanceof BlockState state)) return;
        cir.setReturnValue(VarInt.getByteSize(BlockRestrictionHelper.getReplacementBlock((BlockIdGetter.get(state).gpo$getFastId()))));
    }
}
