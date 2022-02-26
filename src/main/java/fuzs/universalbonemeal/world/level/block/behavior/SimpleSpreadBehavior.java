package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleSpreadBehavior extends SpreadAroundBehavior {
    @Override
    protected boolean canGrowInto(BlockState p_54321_) {
        return p_54321_.isAir();
    }

    @Override
    protected BlockState getGrownBlockState(Block sourceBlock, BlockState sourceState) {
        return sourceBlock.defaultBlockState();
    }

    @Override
    protected int getSpreadRange() {
        return 7;
    }

    @Override
    protected int getSpreadChance() {
        return 6;
    }
}
