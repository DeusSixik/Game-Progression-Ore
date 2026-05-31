package dev.sixik.gameprogressionore.api;

import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import dev.sixik.gameprogressionore.impl.data.BlockRestrictionData;
import dev.sixik.gpf.api.Stages;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class OreStages {

    public static void addStage(short stage, int blockStateId) {
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage, blockStateId, -1);
    }

    public static void addStage(short stage, int blockStateId, int newBlockStateId) {
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage, blockStateId, newBlockStateId);
    }

    public static void addStage(short stage, BlockState blockState) {
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage, BlockIdGetter.get(blockState).gpo$getFastId(), -1);
    }

    public static void addStage(short stage, BlockState blockState, BlockState newblockState) {
        Stages.checkInsideEndEvent();
        BlockRestrictionData.INSTANCE.addNewStage(stage,
                BlockIdGetter.get(blockState).gpo$getFastId(),
                BlockIdGetter.get(newblockState).gpo$getFastId()
        );
    }

    public static void addStage(short stage, Block block) {
        Stages.checkInsideEndEvent();
        for (BlockState possibleState : block.getStateDefinition().getPossibleStates()) {
            BlockRestrictionData.INSTANCE.addNewStage(stage, BlockIdGetter.get(possibleState).gpo$getFastId(), -1);
        }
    }

    public static void addStage(short stage, Block block, BlockState newblockState) {
        Stages.checkInsideEndEvent();
        final int newState = BlockIdGetter.get(newblockState).gpo$getFastId();
        for (BlockState possibleState : block.getStateDefinition().getPossibleStates()) {
            BlockRestrictionData.INSTANCE.addNewStage(stage, BlockIdGetter.get(possibleState).gpo$getFastId(), newState);
        }
    }

    public static void addStage(String stage, int blockStateId) {
        addStage(Stages.getStageId(stage), blockStateId);
    }

    public static void addStage(String stage, int blockStateId, int newBlockStateId) {
        addStage(Stages.getStageId(stage), blockStateId, newBlockStateId);
    }

    public static void addStage(String stage, BlockState blockState) {
        addStage(Stages.getStageId(stage), blockState);
    }

    public static void addStage(String stage, BlockState blockState, BlockState newblockState) {
        addStage(Stages.getStageId(stage), blockState, newblockState);
    }

    public static void addStage(String stage, Block block) {
        addStage(Stages.getStageId(stage), block);
    }

    public static void addStage(String stage, Block block, BlockState newblockState) {
        addStage(Stages.getStageId(stage), block, newblockState);
    }
}
