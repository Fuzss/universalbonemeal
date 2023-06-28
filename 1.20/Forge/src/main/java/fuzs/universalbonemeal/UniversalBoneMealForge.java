package fuzs.universalbonemeal;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.universalbonemeal.handler.BonemealHandler;
import fuzs.universalbonemeal.world.level.block.behavior.CoralBehavior;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(UniversalBoneMeal.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class UniversalBoneMealForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(UniversalBoneMeal.MOD_ID, UniversalBoneMeal::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        // since we compile tags into blocks we need to refresh whenever tags are reloaded
        // is fired on both sides, we only need server, but there doesn't seem to be an easy way to prevent triggering on client side
        MinecraftForge.EVENT_BUS.addListener((final TagsUpdatedEvent evt) -> {
            BonemealHandler.invalidate();
            CoralBehavior.invalidate();
        });
    }
}
