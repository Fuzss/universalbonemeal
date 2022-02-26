package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public abstract class GrowingPlantBehavior implements BonemealBehavior {
    protected final Block block;
    protected final Direction growthDirection;

    public GrowingPlantBehavior(Block block, Direction growthDirection) {
        this.block = block;
        this.growthDirection = growthDirection;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_53900_, BlockPos p_53901_, BlockState p_53902_, boolean p_53903_) {
        BlockPos headPos = this.getHeadPos(p_53900_, p_53901_, p_53902_.getBlock());
        return this.canGrowInto(p_53900_.getBlockState(headPos.relative(this.growthDirection)));
    }

    @Override
    public boolean isBonemealSuccess(Level p_53944_, Random p_53945_, BlockPos p_53946_, BlockState p_53947_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos sourcePos, BlockState sourceState) {
        BlockPos topPos = this.getHeadPos(level, sourcePos, sourceState.getBlock());
        this.performBonemealTop(level, random, topPos, sourceState);
    }

    private void performBonemealTop(ServerLevel level, Random p_53935_, BlockPos topPos, BlockState sourceState) {
        BlockPos blockpos = topPos.relative(this.growthDirection);
        int j = this.getBlocksToGrowWhenBonemealed(p_53935_);
//        int i = Math.min(p_53937_.getValue(AGE) + 1, 25);
        for(int k = 0; k < j && this.canGrowInto(level.getBlockState(blockpos)); ++k) {
//            p_53934_.setBlockAndUpdate(blockpos, p_53937_.setValue(AGE, Integer.valueOf(i)));
            level.setBlockAndUpdate(blockpos, this.getNextBlockState(sourceState.getBlock(), sourceState));
            blockpos = blockpos.relative(this.growthDirection);
        }
//            i = Math.min(i + 1, 25);
    }

    private BlockPos getHeadPos(BlockGetter p_153323_, BlockPos p_153324_, Block p_153325_) {
        return getTopConnectedBlock(p_153323_, p_153324_, p_153325_, this.growthDirection);
    }

    public static BlockPos getTopConnectedBlock(BlockGetter level, BlockPos pos, Block block, Direction direction) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        BlockState blockstate;
        do {
            mutable.move(direction);
            blockstate = level.getBlockState(mutable);
        } while(blockstate.is(block));
        return mutable.move(direction.getOpposite());
    }

    protected abstract int getBlocksToGrowWhenBonemealed(Random random);

    protected abstract boolean canGrowInto(BlockState state);

    protected abstract BlockState getNextBlockState(Block sourceBlock, BlockState sourceState);
}
