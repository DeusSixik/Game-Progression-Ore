package dev.sixik.gameprogressionore.impl.data;

import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import dev.sixik.gpf.api.StageData;
import dev.sixik.gpf.api.Stages;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.List;

public final class BlockRestrictionData {

    public static final BlockRestrictionData INSTANCE = new BlockRestrictionData();

    private volatile long[][] blockRestrictions = new long[0][];

    private static int STONE_ID;

    private ObjectArrayList<BlockRestriction> restrictionsQueu = new ObjectArrayList<>();

    private BlockRestrictionData() { }

    public synchronized void clearData() {
        STONE_ID = BlockIdGetter.get(Blocks.STONE.defaultBlockState()).gpo$getFastId();
        restrictionsQueu.clear();
        this.blockRestrictions = new long[0][];
    }

    public synchronized void reloadData() {
        reloadData(restrictionsQueu);
        restrictionsQueu.clear();
    }

    private void reloadData(List<BlockRestriction> newRules) {
        int maxStateId = Block.BLOCK_STATE_REGISTRY.size();
        long[][] newBlockRestrictions = new long[maxStateId][];

        for (int i = 0; i < newRules.size(); i++) {
            BlockRestriction rule = newRules.get(i);
            int originalId = rule.blockOriginal();
            if (originalId >= 0 && originalId < maxStateId) {
                long packedRule = pack(rule.stage(), rule.newBlock());

                if (newBlockRestrictions[originalId] == null) {
                    long[] newData = new long[1];
                    newData[0] = packedRule;
                    newBlockRestrictions[originalId] = newData;
                } else {
                    long[] oldData = newBlockRestrictions[originalId];
                    long[] newData = Arrays.copyOf(oldData, oldData.length + 1);
                    newData[oldData.length] = packedRule;
                    newBlockRestrictions[originalId] = newData;
                }
            }
        }
        this.blockRestrictions = newBlockRestrictions;
    }

    /**
     * @return ID оригинального блока, либо ID блока-заглушки (если нет прав)
     */
    public int getReplacementBlock(int originalBlockId, StageData playerProgression) {
        final long[][] localRestrictions = this.blockRestrictions;

        if (originalBlockId >= 0 && originalBlockId < localRestrictions.length) {
            final long[] rules = localRestrictions[originalBlockId];

            if (rules != null) {
                for (int i = 0; i < rules.length; i++) {
                    final long rule = rules[i];
                    final short requiredStage = unpackStage(rule);

                    if (!playerProgression.hasStage(requiredStage)) {
                        return unpackBlock(rule);
                    }
                }
            }
        }

        return originalBlockId;
    }

    private static long pack(short stage, int newBlockId) {
        return ((long) stage << 32) | (newBlockId & 0xFFFFFFFFL);
    }

    private static short unpackStage(long packed) {
        return (short) (packed >>> 32);
    }

    private static int unpackBlock(long packed) {
        return (int) packed;
    }

    public void addNewStage(short stage, int oldState, int newState) {
        restrictionsQueu.add(new BlockRestriction(stage, oldState, newState != -1 ? newState : STONE_ID));
    }
}
