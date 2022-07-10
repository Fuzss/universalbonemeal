package fuzs.universalbonemeal.api.event.entity.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface BonemealCallback {
    Event<BonemealCallback> EVENT = EventFactory.createArrayBacked(BonemealCallback.class, listeners -> (Level level, BlockPos pos, BlockState block, ItemStack stack) -> {
        for (BonemealCallback event : listeners) {
            InteractionResult result = event.onBonemeal(level, pos, block, stack);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    /**
     * called when a bone meal is used on a block by the player, a dispenser, or a farmer villager
     * useful for adding custom bone meal behavior to blocks, or for cancelling vanilla interactions
     * @param level level bone meal event occurs in
     * @param pos position bone meal is applied to
     * @param block block state bone meal is applied to
     * @param stack the bone meal stack
     * @return  {@link InteractionResult#PASS} to continue with vanilla,
     *          {@link InteractionResult#SUCCESS} to set as handled and let vanilla show particles + arm swing animation,
     *          {@link InteractionResult#FAIL} to don't do anything
     */
    InteractionResult onBonemeal(Level level, BlockPos pos, BlockState block, ItemStack stack);
}
