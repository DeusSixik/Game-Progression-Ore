package dev.sixik.gameprogressionore.impl.events;

import dev.sixik.gameprogressionore.impl.data.BlockRestrictionData;
import dev.sixik.gameprogressionore.impl.utils.GPOChunkUtils;
import dev.sixik.gpf.api.event.*;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class GPOEvents {

    @SubscribeEvent
    public static void onStageAddEvent(PlayerStageAddedEvent event) {
        ServerPlayer player = event.getPlayer();
        if(player == null) return;
        GPOChunkUtils.resendChunksForPlayer(player);
    }

    @SubscribeEvent
    public static void onStageRemoveEvent(PlayerStageRemovedEvent event) {
        ServerPlayer player = event.getPlayer();
        if(player == null) return;
        GPOChunkUtils.resendChunksForPlayer(player);
    }

    @SubscribeEvent
    public static void onStageClearEvent(PlayerStagesClearedEvent event) {
        ServerPlayer player = event.getPlayer();
        if(player == null) return;
        GPOChunkUtils.resendChunksForPlayer(player);
    }

    @SubscribeEvent
    public static void onStageChangedEvent(PlayerStagesChangedEvent event) {
        ServerPlayer player = event.getPlayer();
        if(player == null) return;
        GPOChunkUtils.resendChunksForPlayer(player);
    }

    @SubscribeEvent
    public static void onStageRegisterEvent(StageRegisterEvent event) {
        BlockRestrictionData.INSTANCE.clearData();
    }

    @SubscribeEvent
    public static void onStageRegisterFinalizeEvent(StageRegisterFinalizeEvent event) {
        BlockRestrictionData.INSTANCE.reloadData();
    }
}
