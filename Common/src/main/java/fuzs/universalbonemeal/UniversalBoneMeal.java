package fuzs.universalbonemeal;

import com.google.common.collect.Sets;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.universalbonemeal.config.ServerConfig;
import fuzs.universalbonemeal.handler.BonemealHandler;
import fuzs.universalbonemeal.world.level.block.behavior.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversalBoneMeal implements ModConstructor {
    public static final String MOD_ID = "universalbonemeal";
    public static final String MOD_NAME = "Universal Bone Meal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CoreServices.FACTORIES.serverConfig(ServerConfig.class, () -> new ServerConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
    }

    @Override
    public void onCommonSetup() {
        registerBonemealBehaviors();
    }

    private static void registerBonemealBehaviors() {
        BonemealHandler.registerBehavior(Blocks.CACTUS, SimpleGrowingPlantBehavior::new, () -> CONFIG.get(ServerConfig.class).allowCactus);
        BonemealHandler.registerBehavior(Blocks.SUGAR_CANE, SimpleGrowingPlantBehavior::new, () -> CONFIG.get(ServerConfig.class).allowSugarCane);
        BonemealHandler.registerBehavior(Blocks.VINE, VineBehavior::new, () -> CONFIG.get(ServerConfig.class).allowVines);
        BonemealHandler.registerBehavior(Blocks.NETHER_WART, NetherWartBehavior::new, () -> CONFIG.get(ServerConfig.class).allowNetherWart);
        BonemealHandler.registerBehavior(Sets.newHashSet(Blocks.MELON_STEM, Blocks.PUMPKIN_STEM), FruitStemBehavior::new, () -> CONFIG.get(ServerConfig.class).allowFruitStems);
        BonemealHandler.registerBehavior(Blocks.LILY_PAD, () -> new SimpleSpreadBehavior(4, 3), () -> CONFIG.get(ServerConfig.class).allowLilyPad);
        BonemealHandler.registerBehavior(Blocks.DEAD_BUSH, () -> new SimpleSpreadBehavior(4, 2), () -> CONFIG.get(ServerConfig.class).allowDeadBush);
        BonemealHandler.registerBehavior(BlockTags.SMALL_FLOWERS, () -> new SimpleSpreadBehavior(3, 1), () -> CONFIG.get(ServerConfig.class).allowSmallFlowers);
        BonemealHandler.registerBehavior(BlockTags.CORAL_PLANTS, CoralBehavior::new, () -> CONFIG.get(ServerConfig.class).allowCorals);
        BonemealHandler.registerBehavior(Blocks.CHORUS_FLOWER, ChorusFlowerBehavior::new, () -> CONFIG.get(ServerConfig.class).allowChorus);
        BonemealHandler.registerBehavior(Blocks.CHORUS_PLANT, ChorusPlantBehavior::new, () -> CONFIG.get(ServerConfig.class).allowChorus);
        BonemealHandler.registerBehavior(Blocks.MYCELIUM, MyceliumBehavior::new, () -> CONFIG.get(ServerConfig.class).allowMycelium);
        BonemealHandler.registerBehavior(Sets.newHashSet(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.DIRT_PATH), DirtBehavior::new, () -> CONFIG.get(ServerConfig.class).allowDirt);
        BonemealHandler.registerBehavior(Blocks.PODZOL, PodzolBehavior::new, () -> CONFIG.get(ServerConfig.class).allowPodzol);
    }
}
