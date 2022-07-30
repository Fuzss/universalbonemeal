package fuzs.universalbonemeal;

import fuzs.puzzleslib.core.CoreServices;
import fuzs.universalbonemeal.api.event.entity.player.BonemealCallback;
import fuzs.universalbonemeal.config.ServerConfig;
import fuzs.universalbonemeal.handler.BonemealHandler;
import fuzs.universalbonemeal.world.level.block.behavior.CoralBehavior;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.CloseableResourceManager;

public class UniversalBoneMealFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor(UniversalBoneMeal.MOD_ID).accept(new UniversalBoneMeal());
        registerHandlers();
    }

    private static void registerHandlers() {
        BonemealHandler bonemealHandler = new BonemealHandler();
        BonemealCallback.EVENT.register(bonemealHandler::onBonemeal);
        UniversalBoneMeal.CONFIG.getHolder(ServerConfig.class).accept(bonemealHandler::invalidate);
        // this is not triggered when initially loading data packs, but our values are invalid by default anyway, so that's fine
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((MinecraftServer server, CloseableResourceManager resourceManager, boolean success) -> {
            // since we compile tags into blocks we need to refresh whenever tags are reloaded
            bonemealHandler.invalidate();
            CoralBehavior.invalidate();
        });
    }
}
