package fuzs.universalbonemeal;

import com.google.common.collect.Sets;
import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.Services;
import fuzs.universalbonemeal.config.ServerConfig;
import fuzs.universalbonemeal.handler.BonemealHandler;
import fuzs.universalbonemeal.world.level.block.behavior.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversalBoneMeal {
    public static final String MOD_ID = "universalbonemeal";
    public static final String MOD_NAME = "Universal Bone Meal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder<AbstractConfig, ServerConfig> CONFIG = Services.FACTORIES.serverConfig(() -> new ServerConfig());

    public static void onConstructMod() {
        CONFIG.loadConfigs(MOD_ID);
    }

    public static void onCommonSetup() {
        registerBonemealBehaviors();
    }

    private static void registerBonemealBehaviors() {
        BonemealHandler.registerBehavior(Blocks.CACTUS, SimpleGrowingPlantBehavior::new, () -> CONFIG.server().allowCactus);
        BonemealHandler.registerBehavior(Blocks.SUGAR_CANE, SimpleGrowingPlantBehavior::new, () -> CONFIG.server().allowSugarCane);
        BonemealHandler.registerBehavior(Blocks.VINE, VineBehavior::new, () -> CONFIG.server().allowVines);
        BonemealHandler.registerBehavior(Blocks.NETHER_WART, NetherWartBehavior::new, () -> CONFIG.server().allowNetherWart);
        BonemealHandler.registerBehavior(Sets.newHashSet(Blocks.MELON_STEM, Blocks.PUMPKIN_STEM), FruitStemBehavior::new, () -> CONFIG.server().allowFruitStems);
        BonemealHandler.registerBehavior(Blocks.LILY_PAD, () -> new SimpleSpreadBehavior(4, 3), () -> CONFIG.server().allowLilyPad);
        BonemealHandler.registerBehavior(Blocks.DEAD_BUSH, () -> new SimpleSpreadBehavior(4, 2), () -> CONFIG.server().allowDeadBush);
        BonemealHandler.registerBehavior(BlockTags.SMALL_FLOWERS, () -> new SimpleSpreadBehavior(3, 1), () -> CONFIG.server().allowSmallFlowers);
        BonemealHandler.registerBehavior(BlockTags.CORAL_PLANTS, CoralBehavior::new, () -> CONFIG.server().allowCorals);
        BonemealHandler.registerBehavior(Blocks.CHORUS_FLOWER, ChorusFlowerBehavior::new, () -> CONFIG.server().allowChorus);
        BonemealHandler.registerBehavior(Blocks.CHORUS_PLANT, ChorusPlantBehavior::new, () -> CONFIG.server().allowChorus);
        BonemealHandler.registerBehavior(Blocks.MYCELIUM, MyceliumBehavior::new, () -> CONFIG.server().allowMycelium);
        BonemealHandler.registerBehavior(Sets.newHashSet(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.DIRT_PATH), DirtBehavior::new, () -> CONFIG.server().allowDirt);
        BonemealHandler.registerBehavior(Blocks.PODZOL, PodzolBehavior::new, () -> CONFIG.server().allowPodzol);
    }
}
