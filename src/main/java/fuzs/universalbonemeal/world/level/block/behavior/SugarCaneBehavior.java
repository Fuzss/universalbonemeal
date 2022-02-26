package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class SugarCaneBehavior extends GrowingPlantBehavior {
    public SugarCaneBehavior(Block block) {
        super(block, Direction.UP);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(Random random) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected BlockState getNextBlockState(Block sourceBlock, BlockState sourceState) {
        return sourceBlock.defaultBlockState();
    }
}
