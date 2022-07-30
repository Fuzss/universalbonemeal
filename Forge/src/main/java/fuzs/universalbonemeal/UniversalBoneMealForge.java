package fuzs.universalbonemeal;

import fuzs.puzzleslib.core.CoreServices;
import fuzs.universalbonemeal.config.ServerConfig;
import fuzs.universalbonemeal.handler.BonemealHandler;
import fuzs.universalbonemeal.world.level.block.behavior.CoralBehavior;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(UniversalBoneMeal.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class UniversalBoneMealForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(UniversalBoneMeal.MOD_ID).accept(new UniversalBoneMeal());
        registerHandlers();
    }

    private static void registerHandlers() {
        BonemealHandler bonemealHandler = new BonemealHandler();
        MinecraftForge.EVENT_BUS.addListener((final BonemealEvent evt) -> {
            InteractionResult result = bonemealHandler.onBonemeal(evt.getLevel(), evt.getPos(), evt.getBlock(), evt.getStack());
            if (result == InteractionResult.SUCCESS) {
                evt.setResult(Event.Result.ALLOW);
            }
        });
        UniversalBoneMeal.CONFIG.getHolder(ServerConfig.class).accept(bonemealHandler::invalidate);
        // since we compile tags into blocks we need to refresh whenever tags are reloaded
        // is fired on both sides, we only need server, but there doesn't seem to be an easy way to prevent triggering on client side
        MinecraftForge.EVENT_BUS.addListener((final TagsUpdatedEvent evt) -> {
            bonemealHandler.invalidate();
            CoralBehavior.invalidate();
        });
    }
}
