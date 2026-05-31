package dev.sixik.gameprogressionore.impl.mixin.blockstate;

import com.mojang.serialization.MapCodec;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase$Extension extends StateHolder<Block, BlockState> implements BlockIdGetter {

    private MixinBlockStateBase$Extension(Block owner, Reference2ObjectArrayMap<Property<?>, Comparable<?>> values, MapCodec<BlockState> propertiesCodec) {
        super(owner, values, propertiesCodec);
    }
}
