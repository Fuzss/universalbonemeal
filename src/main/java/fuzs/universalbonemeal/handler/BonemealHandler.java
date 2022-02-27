package fuzs.universalbonemeal.handler;

import com.google.common.collect.ImmutableSet;
import fuzs.universalbonemeal.world.level.block.behavior.BonemealBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class BonemealHandler {
    private static final List<AbstractBehaviorData> BONE_MEAL_BEHAVIORS = Lists.newArrayList();

    @SubscribeEvent
    public void onBonemeal(final BonemealEvent evt) {
        BlockState block = evt.getBlock();
        BonemealBehavior behavior = null;
        for (AbstractBehaviorData data : BONE_MEAL_BEHAVIORS) {
            if (data.allow() && data.appliesTo(block.getBlock())) {
                behavior = data.getBehavior();
                break;
            }
        }
        if (behavior != null) {
            Level level = evt.getWorld();
            BlockPos pos = evt.getPos();
            if (behavior.isValidBonemealTarget(level, pos, block, level.isClientSide)) {
                if (level instanceof ServerLevel) {
                    if (behavior.isBonemealSuccess(level, level.random, pos, block)) {
                        behavior.performBonemeal((ServerLevel) level, level.random, pos, block);
                    }
                }
                evt.setResult(Event.Result.ALLOW);
            }
        }
    }

    public static void registerBehavior(Block block, Supplier<BonemealBehavior> factory, BooleanSupplier config) {
        BONE_MEAL_BEHAVIORS.add(new BlockBehaviorData(block, factory, config));
    }

    public static void registerBehavior(Set<Block> blocks, Supplier<BonemealBehavior> factory, BooleanSupplier config) {
        BONE_MEAL_BEHAVIORS.add(new MultiBlockBehaviorData(blocks, factory, config));
    }

    public static void registerBehavior(Tag<Block> tag, Supplier<BonemealBehavior> factory, BooleanSupplier config) {
        BONE_MEAL_BEHAVIORS.add(new BlockTagBehaviorData(tag, factory, config));
    }

    private abstract static class AbstractBehaviorData {
        private final BonemealBehavior behavior;
        private final BooleanSupplier config;

        public AbstractBehaviorData(Supplier<BonemealBehavior> factory, BooleanSupplier config) {
            this.behavior = factory.get();
            this.config = config;
        }

        public abstract boolean appliesTo(Block block);

        public BonemealBehavior getBehavior() {
            return this.behavior;
        }

        public boolean allow() {
            return this.config.getAsBoolean();
        }
    }

    private static class BlockBehaviorData extends AbstractBehaviorData {
        private final Block block;

        public BlockBehaviorData(Block block, Supplier<BonemealBehavior> factory, BooleanSupplier config) {
            super(factory, config);
            this.block = block;
        }

        @Override
        public boolean appliesTo(Block block) {
            return this.block == block;
        }
    }

    private static class MultiBlockBehaviorData extends AbstractBehaviorData {
        private final Set<Block> targets;

        public MultiBlockBehaviorData(Set<Block> targets, Supplier<BonemealBehavior> factory, BooleanSupplier config) {
            super(factory, config);
            this.targets = ImmutableSet.copyOf(targets);
        }

        @Override
        public boolean appliesTo(Block block) {
            return this.targets.contains(block);
        }
    }

    private static class BlockTagBehaviorData extends AbstractBehaviorData {
        private final Tag<Block> tag;

        public BlockTagBehaviorData(Tag<Block> tag, Supplier<BonemealBehavior> factory, BooleanSupplier config) {
            super(factory, config);
            this.tag = tag;
        }

        @Override
        public boolean appliesTo(Block block) {
            return this.tag.contains(block);
        }
    }
}
