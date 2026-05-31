package dev.sixik.gameprogressionore.impl.utils;

import dev.sixik.gameprogressionore.GameProgressionOre;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FastBlockStateCache {

    private volatile static boolean initialized;

    public static BlockState[] STATES;

    private static int size;

    public static void init() {
        int registrySize = Block.BLOCK_STATE_REGISTRY.size();
        if (registrySize <= 1) {
            return;
        }

        if (initialized && size == registrySize && STATES != null) {
            return;
        }

        synchronized (FastBlockStateCache.class) {
            registrySize = Block.BLOCK_STATE_REGISTRY.size();
            if (registrySize <= 1) {
                return;
            }

            if (initialized && size == registrySize && STATES != null) {
                return;
            }

            BlockState air = Blocks.AIR.defaultBlockState();
            int maxStateId = 0;
            for (Block block : BuiltInRegistries.BLOCK) {
                for (BlockState possibleState : block.getStateDefinition().getPossibleStates()) {
                    maxStateId = Math.max(maxStateId, Block.getId(possibleState));
                }
            }

            int capacity = Math.max(registrySize, maxStateId + 1);
            BlockState[] states = new BlockState[capacity];


            for (int i = 0; i < capacity; i++) {
                states[i] = air;
            }

            for (Block block : BuiltInRegistries.BLOCK) {
                for (BlockState possibleState : block.getStateDefinition().getPossibleStates()) {
                    int fastId = Block.getId(possibleState);
                    if (fastId < 0 || fastId >= capacity) {
                        continue;
                    }
                    states[fastId] = possibleState;
                    BlockIdGetter.get(possibleState).gpo$setFastId(fastId);
                }
            }

            STATES = states;
            size = registrySize;

            GameProgressionOre.LOGGER.info("PLATFORM: NEOFORGE");
            GameProgressionOre.LOGGER.info("STATE REGISTRY SIZE: {}", size);
            GameProgressionOre.LOGGER.info("STATE CACHE CAPACITY: {}", capacity);

            initialized = true;
        }
    }

    public static BlockState getBlockState(int id) {
        if (id < 0) {
            return Blocks.AIR.defaultBlockState();
        }
        BlockState[] states = STATES;
        if (states == null) {
            init();
            states = STATES;
        }
        if (states == null || id >= states.length) {
            return Block.stateById(id);
        }

        return states[id];
    }

    public static int getSize() {
        return size;
    }
}
