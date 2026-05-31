package dev.sixik.gameprogressionore.impl.mixin.sending.paletted;

import dev.sixik.gameprogressionore.api.BlockRestrictionHelper;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.HashMapPalette;
import net.minecraft.world.level.chunk.Palette;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HashMapPalette.class)
public abstract class MixinHashMapPalette<T> implements Palette<T> {

    @Shadow
    @Final
    private CrudeIncrementalIntIdentityHashBiMap<T> values;

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    public void gpo$write$replace_block(FriendlyByteBuf buf, CallbackInfo ci) {
        if(this.values.size() == 0 || !(values.byId(0) instanceof BlockState)) return;

        final int i = this.getSize();
        buf.writeVarInt(i);

        for (int j = 0; j < i; ++j) {
            buf.writeVarInt(BlockRestrictionHelper.getReplacementBlock(BlockIdGetter.get((BlockState) this.values.byId(j)).gpo$getFastId()));
        }

        ci.cancel();
    }


    @Inject(method = "getSerializedSize", at = @At("HEAD"), cancellable = true)
    public void gpo$getSerializedSize$replace_block(CallbackInfoReturnable<Integer> cir) {
        if(this.values.size() == 0 || !(values.byId(0) instanceof BlockState)) return;

        int outSize = VarInt.getByteSize(this.getSize());
        for (int index = 0; index < this.getSize(); ++index) {
            outSize += VarInt.getByteSize(
                    BlockRestrictionHelper.getReplacementBlock(
                            BlockIdGetter.get((BlockState) this.values.byId(index)).gpo$getFastId()
                    )
            );
        }
        cir.setReturnValue(outSize);
    }
}
