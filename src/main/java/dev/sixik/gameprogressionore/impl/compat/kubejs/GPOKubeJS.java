package dev.sixik.gameprogressionore.impl.compat.kubejs;

import dev.sixik.gameprogressionore.api.OreStages;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class GPOKubeJS {

    private GPOKubeJS() { }

    /**
     * Restricts a block state id until the player unlocks the given numeric stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param blockStateId raw Minecraft block state id to restrict
     */
    public static void addStage(short stage, int blockStateId) {
        OreStages.addStage(stage, blockStateId, -1);
    }

    /**
     * Restricts a block state id until the player unlocks the given numeric stage.
     * While the stage is locked, the block is shown as the provided replacement state id.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param blockStateId raw Minecraft block state id to restrict
     * @param newBlockStateId raw Minecraft block state id used as the client-side replacement
     */
    public static void addStage(short stage, int blockStateId, int newBlockStateId) {
        OreStages.addStage(stage, blockStateId, newBlockStateId);
    }

    /**
     * Restricts a specific block state until the player unlocks the given numeric stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param blockState block state to restrict
     */
    public static void addStage(short stage, BlockState blockState) {
        OreStages.addStage(stage, blockState);
    }

    /**
     * Restricts a specific block state until the player unlocks the given numeric stage.
     * While the stage is locked, the block is shown as the provided replacement state.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param blockState block state to restrict
     * @param newblockState block state used as the client-side replacement
     */
    public static void addStage(short stage, BlockState blockState, BlockState newblockState) {
        OreStages.addStage(stage, blockState, newblockState);
    }

    /**
     * Restricts all possible states of the given block until the player unlocks the numeric stage.
     * Each state is replaced with the default fallback state while locked.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param block block whose states should be restricted
     */
    public static void addStage(short stage, Block block) {
        OreStages.addStage(stage, block);
    }

    /**
     * Restricts all possible states of the given block until the player unlocks the numeric stage.
     * Each state is shown as the provided replacement state while locked.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param block block whose states should be restricted
     * @param newblockState block state used as the client-side replacement
     */
    public static void addStage(short stage, Block block, BlockState newblockState) {
        OreStages.addStage(stage, block, newblockState);
    }

    /**
     * Restricts a block state id until the player unlocks the named stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param blockStateId raw Minecraft block state id to restrict
     */
    public static void addStage(String stage, int blockStateId) {
        OreStages.addStage(stage, blockStateId);
    }

    /**
     * Restricts a block state id until the player unlocks the named stage.
     * While the stage is locked, the block is shown as the provided replacement state id.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param blockStateId raw Minecraft block state id to restrict
     * @param newBlockStateId raw Minecraft block state id used as the client-side replacement
     */
    public static void addStage(String stage, int blockStateId, int newBlockStateId) {
        OreStages.addStage(stage, blockStateId, newBlockStateId);
    }

    /**
     * Restricts a specific block state until the player unlocks the named stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param blockState block state to restrict
     */
    public static void addStage(String stage, BlockState blockState) {
        OreStages.addStage(stage, blockState);
    }

    /**
     * Restricts a specific block state until the player unlocks the named stage.
     * While the stage is locked, the block is shown as the provided replacement state.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param blockState block state to restrict
     * @param newblockState block state used as the client-side replacement
     */
    public static void addStage(String stage, BlockState blockState, BlockState newblockState) {
        OreStages.addStage(stage, blockState, newblockState);
    }

    /**
     * Restricts all possible states of the given block until the player unlocks the named stage.
     * Each state is replaced with the default fallback state while locked.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param block block whose states should be restricted
     */
    public static void addStage(String stage, Block block) {
        OreStages.addStage(stage, block);
    }

    /**
     * Restricts all possible states of the given block until the player unlocks the named stage.
     * Each state is shown as the provided replacement state while locked.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param block block whose states should be restricted
     * @param newblockState block state used as the client-side replacement
     */
    public static void addStage(String stage, Block block, BlockState newblockState) {
        OreStages.addStage(stage, block, newblockState);
    }
}
