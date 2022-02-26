package fuzs.universalbonemeal.world.level.block.behavior;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ChorusPlantBehavior extends ChorusFlowerBehavior {
    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        for (BlockPos flowerPosition : this.getFlowerPositions(p_50897_, p_50898_)) {
            if (super.isValidBonemealTarget(p_50897_, flowerPosition, p_50897_.getBlockState(flowerPosition), p_50900_)) return true;
        }
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel p_50893_, Random p_50894_, BlockPos p_50895_, BlockState p_50896_) {
        for (BlockPos flowerPosition : this.getFlowerPositions(p_50893_, p_50895_)) {
            super.performBonemeal(p_50893_, p_50894_, flowerPosition, p_50893_.getBlockState(flowerPosition));
        }
    }

    private Collection<BlockPos> getFlowerPositions(BlockGetter level, BlockPos startPos) {
        Set<BlockPos> targets = Sets.newHashSet();
        getTopConnectedBlock(level, startPos.mutable(), Blocks.CHORUS_PLANT, Blocks.CHORUS_FLOWER, targets, Direction.DOWN, 64);
        return targets;
    }

    public static void getTopConnectedBlock(BlockGetter level, BlockPos.MutableBlockPos sourcePosition, Block sourceBlock, Block targetBlock, Collection<BlockPos> targets, Direction sourceDirection, int depth) {
        BlockState sourceState = level.getBlockState(sourcePosition);
        if (depth <= 0 || !sourceState.is(sourceBlock)) {
            if (sourceState.is(targetBlock)) targets.add(sourcePosition.immutable());
            return;
        }
        for (Map.Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
            Direction direction = entry.getKey();
            if (direction != Direction.DOWN && direction != sourceDirection) {
                if (sourceState.getValue(entry.getValue())) {
                    sourcePosition.move(direction);
                    getTopConnectedBlock(level, sourcePosition, sourceBlock, targetBlock, targets, direction.getOpposite(), depth - 1);
                    sourcePosition.move(direction.getOpposite());
                }
            }
        }
    }
}
