package dev.sixik.gameprogressionore.api;

import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import dev.sixik.gameprogressionore.impl.data.BlockRestrictionData;
import dev.sixik.gameprogressionore.impl.utils.GPOChunkUtils;
import dev.sixik.gpf.api.StageData;
import dev.sixik.gpf.impl.server.PlayerStageDataService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

public class BlockRestrictionHelper {

    public static int getReplacementBlock(int blockStateId) {
        StageData curData = GPOChunkUtils.currentPlayerDataPacket.get();
        if(curData == null) return blockStateId;

        return BlockRestrictionData.INSTANCE
                .getReplacementBlock(blockStateId, curData);
    }

    public static int getReplacementBlock(int blockStateId, ServerPlayer player) {
        return BlockRestrictionData.INSTANCE
                .getReplacementBlock(blockStateId, PlayerStageDataService.getOrCreate(player));
    }

    public static int getReplacementBlock(BlockState block) {
        final int blockStateId = BlockIdGetter.get(block).gpo$getFastId();
        return getReplacementBlock(blockStateId);
    }

    public static int getReplacementBlock(BlockState block, ServerPlayer player) {
        final int blockStateId = BlockIdGetter.get(block).gpo$getFastId();
        return getReplacementBlock(blockStateId, player);
    }
}
