package dev.sixik.gameprogressionore.impl.utils;

import dev.sixik.gameprogressionore.impl.mixin.accessors.ServerGamePacketListenerImplAccessor;
import dev.sixik.gpf.api.StageData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

public class GPOChunkUtils {

    @Nullable
    public static ServerPlayer currentPlayer;
    public static StageData currentPlayerData;

    public static ThreadLocal<StageData> currentPlayerDataPacket = new ThreadLocal<>();

    public static void setPacketStageData(@Nullable StageData stageData) {
        if (stageData == null) {
            currentPlayerDataPacket.remove();
            return;
        }

        currentPlayerDataPacket.set(stageData);
    }

    public static void clearPacketStageData() {
        currentPlayerDataPacket.remove();
    }

    public static void resendChunksForPlayer(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        ChunkPos center = player.chunkPosition();

        int viewDistance = level.getServer().getPlayerList().getViewDistance();
        PlayerChunkSender chunkSender = ((ServerGamePacketListenerImplAccessor) player.connection).getChunkSender();

        for (int x = -viewDistance; x <= viewDistance; x++) {
            for (int z = -viewDistance; z <= viewDistance; z++) {
                LevelChunk chunk = level.getChunkSource().getChunkNow(center.x + x, center.z + z);

                if (chunk != null) {
                    chunkSender.markChunkPendingToSend(chunk);
                }
            }
        }
    }
}
