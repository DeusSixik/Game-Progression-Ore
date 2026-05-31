package dev.sixik.gameprogressionore.api.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/*
    From Generator Accelerator
 */
public interface BlockIdGetter {

    final class Fallback implements BlockIdGetter {
        private final BlockState state;

        private Fallback(BlockState state) {
            this.state = state;
        }

        @Override
        public int gpo$getFastId() {
            return Block.getId(this.state);
        }

        @Override
        public void gpo$setFastId(int id) {
            // Plain unit tests do not apply the mixin-backed fast-id field.
        }
    }

    static BlockIdGetter get(BlockState state) {
        if (state instanceof BlockIdGetter extension) {
            return extension;
        }
        return new Fallback(state);
    }

    int gpo$getFastId();

    void gpo$setFastId(int id);
}
