package dev.sixik.gameprogressionore.impl.compat.kubejs;

import dev.sixik.gameprogressionore.api.OreStages;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class GPOKubeJS {

    private GPOKubeJS() { }

    public static void addStage(short stage, int blockStateId) {
        OreStages.addStage(stage, blockStateId, -1);
    }

    public static void addStage(short stage, int blockStateId, int newBlockStateId) {
        OreStages.addStage(stage, blockStateId, newBlockStateId);
    }

    public static void addStage(short stage, BlockState blockState) {
        OreStages.addStage(stage, blockState);
    }

    public static void addStage(short stage, BlockState blockState, BlockState newblockState) {
        OreStages.addStage(stage, blockState, newblockState);
    }

    public static void addStage(short stage, Block block) {
        OreStages.addStage(stage, block);
    }

    public static void addStage(short stage, Block block, BlockState newblockState) {
        OreStages.addStage(stage, block, newblockState);
    }

    public static void addStage(String stage, int blockStateId) {
        OreStages.addStage(stage, blockStateId);
    }

    public static void addStage(String stage, int blockStateId, int newBlockStateId) {
        OreStages.addStage(stage, blockStateId, newBlockStateId);
    }

    public static void addStage(String stage, BlockState blockState) {
        OreStages.addStage(stage, blockState);
    }

    public static void addStage(String stage, BlockState blockState, BlockState newblockState) {
        OreStages.addStage(stage, blockState, newblockState);
    }

    public static void addStage(String stage, Block block) {
        OreStages.addStage(stage, block);
    }

    public static void addStage(String stage, Block block, BlockState newblockState) {
        OreStages.addStage(stage, block, newblockState);
    }
}
