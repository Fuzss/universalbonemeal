package fuzs.universalbonemeal.handler;

import com.google.common.collect.Maps;
import fuzs.universalbonemeal.world.level.block.behavior.BonemealBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class BonemealHandler {
    private static final Map<Block, Pair<BonemealBehavior, BooleanSupplier>> BONE_MEAL_BEHAVIORS = Maps.newHashMap();

    @SubscribeEvent
    public void onBonemeal(final BonemealEvent evt) {
        BlockState block = evt.getBlock();
        if (!BONE_MEAL_BEHAVIORS.containsKey(block.getBlock())) return;
        Pair<BonemealBehavior, BooleanSupplier> behavior = BONE_MEAL_BEHAVIORS.get(block.getBlock());
        Level level = evt.getWorld();
        BlockPos pos = evt.getPos();
        if (behavior.getRight().getAsBoolean() && behavior.getLeft().isValidBonemealTarget(level, pos, block, level.isClientSide)) {
            if (level instanceof ServerLevel) {
                if (behavior.getLeft().isBonemealSuccess(level, level.random, pos, block)) {
                    behavior.getLeft().performBonemeal((ServerLevel) level, level.random, pos, block);
                }
            }
            evt.setResult(Event.Result.ALLOW);
        }
    }

    public static void registerBehavior(Block block, Supplier<BonemealBehavior> factory, BooleanSupplier config) {
        BONE_MEAL_BEHAVIORS.put(block, Pair.of(factory.get(), config));
    }
}
