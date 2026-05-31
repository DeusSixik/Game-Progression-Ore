package dev.sixik.gameprogressionore.api;

import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import dev.sixik.gameprogressionore.impl.data.BlockRestrictionData;
import dev.sixik.gpf.api.Stages;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class OreStages {

    /**
     * Restricts a block state id until the player unlocks the given numeric stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param blockStateId raw Minecraft block state id to restrict
     */
    public static void addStage(short stage, int blockStateId) {
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage, blockStateId, -1);
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
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage, blockStateId, newBlockStateId);
    }

    /**
     * Restricts a specific block state until the player unlocks the given numeric stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param blockState block state to restrict
     */
    public static void addStage(short stage, BlockState blockState) {
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage, BlockIdGetter.get(blockState).gpo$getFastId(), -1);
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
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage,
                BlockIdGetter.get(blockState).gpo$getFastId(),
                BlockIdGetter.get(newblockState).gpo$getFastId()
        );
    }

    /**
     * Restricts all possible states of the given block until the player unlocks the numeric stage.
     * Each state is replaced with the default fallback state while locked.
     *
     * @param stage numeric stage id from Game Progression Framework
     * @param block block whose states should be restricted
     */
    public static void addStage(short stage, Block block) {
        Stages.checkInsideEndEvent();
        for (BlockState possibleState : block.getStateDefinition().getPossibleStates()) {
            BlockRestrictionData.INSTANCE.addNewStage(stage, BlockIdGetter.get(possibleState).gpo$getFastId(), -1);
        }
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
        Stages.checkInsideEndEvent();
        final int newState = BlockIdGetter.get(newblockState).gpo$getFastId();
        for (BlockState possibleState : block.getStateDefinition().getPossibleStates()) {
            BlockRestrictionData.INSTANCE.addNewStage(stage, BlockIdGetter.get(possibleState).gpo$getFastId(), newState);
        }
    }

    /**
     * Restricts a block state id until the player unlocks the named stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param blockStateId raw Minecraft block state id to restrict
     */
    public static void addStage(String stage, int blockStateId) {
        addStage(Stages.getStageId(stage), blockStateId);
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
        addStage(Stages.getStageId(stage), blockStateId, newBlockStateId);
    }

    /**
     * Restricts a specific block state until the player unlocks the named stage.
     * The hidden block is replaced with the default fallback state.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param blockState block state to restrict
     */
    public static void addStage(String stage, BlockState blockState) {
        addStage(Stages.getStageId(stage), blockState);
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
        addStage(Stages.getStageId(stage), blockState, newblockState);
    }

    /**
     * Restricts all possible states of the given block until the player unlocks the named stage.
     * Each state is replaced with the default fallback state while locked.
     *
     * @param stage stage name registered in Game Progression Framework
     * @param block block whose states should be restricted
     */
    public static void addStage(String stage, Block block) {
        addStage(Stages.getStageId(stage), block);
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
        addStage(Stages.getStageId(stage), block, newblockState);
    }
}
