package dev.sixik.gameprogressionore;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(GameProgressionOre.MODID)
public class GameProgressionOre {
    public static final String MODID = "game_progression_ore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GameProgressionOre(IEventBus modEventBus, ModContainer modContainer) {

    }
}
