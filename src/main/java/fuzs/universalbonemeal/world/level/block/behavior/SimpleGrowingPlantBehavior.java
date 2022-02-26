package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class SimpleGrowingPlantBehavior extends GrowingPlantBehavior {
    public SimpleGrowingPlantBehavior() {
        super(Direction.UP);
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
    protected BlockState getGrownBlockState(Block sourceBlock, BlockState sourceState) {
        return sourceBlock.defaultBlockState();
    }
}
