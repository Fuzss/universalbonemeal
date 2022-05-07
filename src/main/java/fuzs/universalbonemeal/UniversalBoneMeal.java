package fuzs.universalbonemeal;

import com.google.common.collect.Sets;
import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.config.ConfigHolderImpl;
import fuzs.universalbonemeal.config.ServerConfig;
import fuzs.universalbonemeal.handler.BonemealHandler;
import fuzs.universalbonemeal.world.level.block.behavior.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(UniversalBoneMeal.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class UniversalBoneMeal {
    public static final String MOD_ID = "universalbonemeal";
    public static final String MOD_NAME = "Universal Bone Meal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder<AbstractConfig, ServerConfig> CONFIG = ConfigHolder.server(() -> new ServerConfig());

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ((ConfigHolderImpl<?, ?>) CONFIG).addConfigs(MOD_ID);
        registerHandlers();
    }

    private static void registerHandlers() {
        BonemealHandler bonemealHandler = new BonemealHandler();
        MinecraftForge.EVENT_BUS.addListener(bonemealHandler::onBonemeal);
        CONFIG.addServerCallback(bonemealHandler::invalidate);
        // TagsUpdatedEvent below should do the same without having to worry about any concurrency issues
//        MinecraftForge.EVENT_BUS.addListener((final AddReloadListenerEvent evt) -> evt.addListener((PreparableReloadListener.PreparationBarrier p_10638_, ResourceManager p_10639_, ProfilerFiller p_10640_, ProfilerFiller p_10641_, Executor p_10642_, Executor p_10643_) -> {
//            // since we compile tags into blocks we need to refresh whenever tags are reloaded
//            return p_10638_.wait(Unit.INSTANCE).thenRunAsync(() -> {
//                bonemealHandler.invalidate();
//                CoralBehavior.invalidate();
//            }, p_10643_);
//        }));
        // since we compile tags into blocks we need to refresh whenever tags are reloaded
        // is fired on both sides, we only need server, but there doesn't seem to be an easy way to prevent triggering on client side
        MinecraftForge.EVENT_BUS.addListener((final TagsUpdatedEvent evt) -> {
            bonemealHandler.invalidate();
            CoralBehavior.invalidate();
        });
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent evt) {
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
