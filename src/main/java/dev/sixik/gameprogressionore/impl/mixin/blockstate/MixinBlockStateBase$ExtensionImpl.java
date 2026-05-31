package dev.sixik.gameprogressionore.impl.mixin.blockstate;

import com.mojang.serialization.MapCodec;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import dev.sixik.gameprogressionore.impl.utils.FastBlockStateCache;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase$ExtensionImpl extends StateHolder<Block, BlockState> implements BlockIdGetter {

    @Unique
    private int bts$generatorFastId = -1;

    private MixinBlockStateBase$ExtensionImpl(Block owner, Reference2ObjectArrayMap<Property<?>, Comparable<?>> values, MapCodec<BlockState> propertiesCodec) {
        super(owner, values, propertiesCodec);
    }

    @Override
    public int gpo$getFastId() {
        if (bts$generatorFastId == -1) {
            FastBlockStateCache.init();
            if (bts$generatorFastId == -1) {
                bts$generatorFastId = Block.getId((BlockState) (Object) this);
            }
        }

        return bts$generatorFastId;
    }

    @Override
    public void gpo$setFastId(int id) {
        bts$generatorFastId = id;
    }
}