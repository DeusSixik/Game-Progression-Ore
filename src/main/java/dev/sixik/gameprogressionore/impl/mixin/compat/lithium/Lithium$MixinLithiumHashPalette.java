package dev.sixik.gameprogressionore.impl.mixin.compat.lithium;

import dev.sixik.gameprogressionore.api.BlockRestrictionHelper;
import net.caffeinemc.mods.lithium.common.world.chunk.LithiumHashPalette;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.Palette;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LithiumHashPalette.class)
public abstract class Lithium$MixinLithiumHashPalette<T> implements Palette<T> {


    @Shadow
    private int size;

    @Shadow
    @Final
    private IdMap<T> idList;

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    public void gpo$write$replace_block(FriendlyByteBuf buf, CallbackInfo ci) {
        final int size_ = size;
        if(size_ == 0 || !(idList.byId(0) instanceof BlockState)) return;

        buf.writeVarInt(size_);
        for (int index = 0; index < size_; ++index) {
            buf.writeVarInt(
                    BlockRestrictionHelper.getReplacementBlock(
                            this.idList.getId(this.valueFor(index))
                    )
            );
        }

        ci.cancel();
    }


    @Inject(method = "getSerializedSize", at = @At("HEAD"), cancellable = true)
    public void gpo$getSerializedSize$replace_block(CallbackInfoReturnable<Integer> cir) {
        final int size_ = size;
        if(size_ == 0 || !(idList.byId(0) instanceof BlockState)) return;

        int outSize = VarInt.getByteSize(size_);
        for (int index = 0; index < size_; ++index) {
            outSize += VarInt.getByteSize(
                    BlockRestrictionHelper.getReplacementBlock(
                            this.idList.getId(this.valueFor(index))
                    )
            );
        }
        cir.setReturnValue(outSize);
    }
}
