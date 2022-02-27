package fuzs.universalbonemeal;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.config.ConfigHolderImpl;
import fuzs.universalbonemeal.config.ServerConfig;
import fuzs.universalbonemeal.handler.BonemealHandler;
import fuzs.universalbonemeal.world.level.block.behavior.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(UniversalBoneMeal.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class UniversalBoneMeal {
    public static final String MOD_ID = "universalbonemeal";
    public static final String MOD_NAME = "Universal Bone Meal";
    public static final Logger LOGGER = LogManager.getLogger(UniversalBoneMeal.MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder<AbstractConfig, ServerConfig> CONFIG = ConfigHolder.server(() -> new ServerConfig());

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ((ConfigHolderImpl<?, ?>) CONFIG).addConfigs(MOD_ID);
        registerHandlers();
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent evt) {
        BonemealHandler.registerBehavior(Blocks.CACTUS, SimpleGrowingPlantBehavior::new, () -> CONFIG.server().allowCactus);
        BonemealHandler.registerBehavior(Blocks.SUGAR_CANE, SimpleGrowingPlantBehavior::new, () -> CONFIG.server().allowSugarCane);
        BonemealHandler.registerBehavior(Blocks.VINE, VineBehavior::new, () -> CONFIG.server().allowVines);
        BonemealHandler.registerBehavior(Blocks.NETHER_WART, NetherWartBehavior::new, () -> CONFIG.server().allowNetherWart);
        BonemealHandler.registerBehavior(Blocks.MELON_STEM, StemBehavior::new, () -> CONFIG.server().allowMelonStem);
        BonemealHandler.registerBehavior(Blocks.PUMPKIN_STEM, StemBehavior::new, () -> CONFIG.server().allowPumpkinStem);
        BonemealHandler.registerBehavior(Blocks.LILY_PAD, () -> new SimpleSpreadBehavior(4, 3), () -> CONFIG.server().allowLilyPad);
        BonemealHandler.registerBehavior(Blocks.DEAD_BUSH, () -> new SimpleSpreadBehavior(4, 2), () -> CONFIG.server().allowDeadBush);
        BonemealHandler.registerBehavior(BlockTags.SMALL_FLOWERS, () -> new SimpleSpreadBehavior(3, 1), () -> CONFIG.server().allowSmallFlowers);
        BonemealHandler.registerBehavior(BlockTags.CORAL_PLANTS, CoralBehavior::new, () -> CONFIG.server().allowCorals);
        BonemealHandler.registerBehavior(Blocks.CHORUS_FLOWER, ChorusFlowerBehavior::new, () -> CONFIG.server().allowChorus);
        BonemealHandler.registerBehavior(Blocks.CHORUS_PLANT, ChorusPlantBehavior::new, () -> CONFIG.server().allowChorus);
        BonemealHandler.registerBehavior(Blocks.MYCELIUM, MyceliumBehavior::new, () -> CONFIG.server().allowMycelium);
    }

    private static void registerHandlers() {
        BonemealHandler bonemealHandler = new BonemealHandler();
        MinecraftForge.EVENT_BUS.addListener(bonemealHandler::onBonemeal);
    }
}
