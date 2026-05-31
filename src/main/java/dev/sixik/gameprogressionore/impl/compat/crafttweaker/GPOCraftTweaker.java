package dev.sixik.gameprogressionore.impl.compat.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import dev.sixik.gameprogressionore.api.OreStages;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.gpo.api.OreStages")
public class GPOCraftTweaker {

    @ZenCodeType.Method
    public static void addStage(short stage, int blockStateId) {
        OreStages.addStage(stage, blockStateId);
    }

    @ZenCodeType.Method
    public static void addStage(short stage, int blockStateId, int newBlockStateId) {
        OreStages.addStage(stage, blockStateId, newBlockStateId);
    }

    @ZenCodeType.Method
    public static void addStage(short stage, BlockState blockState) {
        OreStages.addStage(stage, blockState);
    }

    @ZenCodeType.Method
    public static void addStage(short stage, BlockState blockState, BlockState newblockState) {
        OreStages.addStage(stage, blockState, newblockState);
    }

    @ZenCodeType.Method
    public static void addStage(short stage, Block block) {
        OreStages.addStage(stage, block);
    }

    @ZenCodeType.Method
    public static void addStage(short stage, Block block, BlockState newblockState) {
        OreStages.addStage(stage, block, newblockState);
    }

    @ZenCodeType.Method
    public static void addStage(String stage, int blockStateId) {
        OreStages.addStage(stage, blockStateId);
    }

    @ZenCodeType.Method
    public static void addStage(String stage, int blockStateId, int newBlockStateId) {
        OreStages.addStage(stage, blockStateId, newBlockStateId);
    }

    @ZenCodeType.Method
    public static void addStage(String stage, BlockState blockState) {
        OreStages.addStage(stage, blockState);
    }

    @ZenCodeType.Method
    public static void addStage(String stage, BlockState blockState, BlockState newblockState) {
        OreStages.addStage(stage, blockState, newblockState);
    }

    @ZenCodeType.Method
    public static void addStage(String stage, Block block) {
        OreStages.addStage(stage, block);
    }

    @ZenCodeType.Method
    public static void addStage(String stage, Block block, BlockState newblockState) {
        OreStages.addStage(stage, block, newblockState);
    }
}
