# Game Progression Ore

Game Progression Ore (GPO) is a NeoForge 1.21.1 addon for Game Progression Framework (GPF) that hides blocks from players until they unlock the required stage.

Instead of changing the real world state, GPO sends per-player replacement block states to the client. This lets you gate ores, plants, leaves, or any other block behind progression without actually replacing those blocks on the server.

## What it does

- Hides blocks per player based on GPF stages.
- Sends fake replacement states only to players who do not have the required stage.
- Keeps the real block in the world unchanged on the server.
- Re-syncs nearby chunks when player stages change.
- Supports Java, CraftTweaker, and KubeJS registration.

## Typical use cases

- Hide ores until the player reaches a mining tier.
- Replace advanced machines with stone until a tech stage is unlocked.
- Hide biome resources, plants, or leaves until exploration progression is reached.
- Make late-game structures look ordinary until the player has the required stage.

## How it works

GPO hooks into chunk and block update packets and swaps restricted block states before they are sent to the client.

That means:

- the server still stores the original block
- mining and interaction checks can still respect the hidden state for that player
- different players can see different versions of the same block

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.x
- [Game Progression Framework](https://github.com/DeusSixik/Game-Progression-Framework) or the published dependency used by this project

## Public API

Main entry points:

- `E:\JavaProjects\GameProgressionOre\src\main\java\dev\sixik\gameprogressionore\api\OreStages.java`
  - Main registration API for block restrictions.
  - Use this to bind a stage to a block, block state, or raw block state id.
- `E:\JavaProjects\GameProgressionOre\src\main\java\dev\sixik\gameprogressionore\api\BlockRestrictionHelper.java`
  - Helper API for resolving the visible replacement block for a player or packet context.

Compat layers:

- CraftTweaker:
  - `E:\JavaProjects\GameProgressionOre\src\main\java\dev\sixik\gameprogressionore\impl\compat\crafttweaker\GPOCraftTweaker.java`
- KubeJS:
  - `E:\JavaProjects\GameProgressionOre\src\main\java\dev\sixik\gameprogressionore\impl\compat\kubejs\GPOKubeJS.java`
  - `E:\JavaProjects\GameProgressionOre\src\main\java\dev\sixik\gameprogressionore\impl\compat\kubejs\GPOKubeJSPlugin.java`

## Registration lifecycle

Because GPO depends on finalized GPF stage ids, restrictions should be registered only after stage registration is complete.

### 1. `StageRegisterEvent`

Use `StageRegisterEvent` only to declare stage names.

Example:

- `bronze_age`
- `ore_tier_2`
- `my_mod:deep_mining`

Do not register block restrictions here.

### 2. `StageRegisterFinalizeEvent`

Use `StageRegisterFinalizeEvent` to register GPO restrictions.

At this point:

- stage names are already known
- ids are finalized
- `Stages.getStageId(...)` is safe to use
- GPO can build its internal restriction table

In short:

- `StageRegisterEvent` = declare stage names
- `StageRegisterFinalizeEvent` = register block hiding rules

## Important behavior

If you do not specify a replacement block, GPO falls back to `minecraft:stone`.

That means these two forms are different:

- `OreStages.addStage("bronze_age", Blocks.DIAMOND_ORE)` -> hidden as stone
- `OreStages.addStage("bronze_age", Blocks.BIRCH_LEAVES, Blocks.STONE.defaultBlockState())` -> hidden as stone explicitly

When you register a `Block`, GPO applies the restriction to all possible block states of that block.

When you register a `BlockState`, GPO applies the restriction only to that exact state.

## Java usage

### Register stage names

```java
import dev.sixik.gpf.api.event.StageRegisterEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class MyStages {

    @SubscribeEvent
    public static void registerStages(StageRegisterEvent event) {
        event.registerStage("bronze_age");
        event.registerStage("deep_mining");
    }
}
```

### Register block restrictions after finalization

```java
import dev.sixik.gameprogressionore.api.OreStages;
import dev.sixik.gpf.api.event.StageRegisterFinalizeEvent;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;

public class MyRestrictions {

    @SubscribeEvent
    public static void registerRestrictions(StageRegisterFinalizeEvent event) {
        OreStages.addStage("bronze_age", Blocks.DIAMOND_ORE);
        OreStages.addStage("deep_mining", Blocks.DEEPSLATE_DIAMOND_ORE);
        OreStages.addStage("deep_mining", Blocks.BIRCH_LEAVES, Blocks.STONE.defaultBlockState());
    }
}
```

### Register a single block state

```java
OreStages.addStage(
        "deep_mining",
        Blocks.REDSTONE_ORE.defaultBlockState(),
        Blocks.STONE.defaultBlockState()
);
```

### Register by raw block state id

```java
short stageId = Stages.getStageId("bronze_age");
OreStages.addStage(stageId, someBlockStateId, replacementBlockStateId);
```

## CraftTweaker usage

ZenCode class:

- `mods.gpo.api.OreStages`

### Example

```zenscript
import mods.gpf.api.events.StageRegisterEvent;
import mods.gpf.api.events.StageRegisterFinalizeEvent;
import mods.gpo.api.OreStages;

events.register<StageRegisterEvent>(event => {
    event.registerStage("bronze_age");
});

events.register<StageRegisterFinalizeEvent>(event => {
    OreStages.addStage("bronze_age", <block:minecraft:diamond_ore>);
    OreStages.addStage("bronze_age", <block:minecraft:birch_leaves>, <blockstate:minecraft:stone>);
});
```

### CraftTweaker rules

- Register stage names in `StageRegisterEvent`.
- Register GPO restrictions in `StageRegisterFinalizeEvent`.
- Use `Block` if you want all states of that block hidden.
- Use `BlockState` if you want only one exact state hidden.

## KubeJS usage

KubeJS binding name:

- `GPOre`

### Example

```js
GPFEvents.stageRegister(event => {
  event.register('bronze_age')
})

GPFEvents.stageRegisterFinalize(event => {
  GPOre.addStage('bronze_age', Block.getBlock('minecraft:diamond_ore'))
})
```

### Example with replacement state

```js
GPFEvents.stageRegisterFinalize(event => {
  GPOre.addStage(
    'bronze_age',
    Block.getBlock('minecraft:birch_leaves'),
    Block.getBlock('minecraft:stone').defaultBlockState()
  )
})
```

## Runtime behavior

When a player's stages change, GPO re-sends nearby chunks so the client immediately updates visible blocks.

This is handled in:

- `E:\JavaProjects\GameProgressionOre\src\main\java\dev\sixik\gameprogressionore\impl\events\GPOEvents.java`

So after adding or removing a stage, hidden blocks around the player should refresh automatically.

## Notes

- GPO is visual and packet-based. The original world block remains unchanged.
- Different players can see different block states for the same position.
- If you use leaves or blocks with frequent updates, GPO still re-applies hidden states during chunk and block sync packets.
- Register restrictions only after stage finalization to avoid invalid or missing stage ids.

## Summary

Use GPO as a simple stage-driven visibility layer:

1. Register stage names in GPF.
2. Wait for `StageRegisterFinalizeEvent`.
3. Register hidden block rules through `OreStages`.
4. Let GPO send per-player replacement blocks automatically.

This keeps progression logic clean, preserves the real world state, and allows modpacks to hide content without expensive custom world mutation.
