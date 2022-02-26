package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public abstract class SpreadAroundBehavior implements BonemealBehavior {
    @Override
    public boolean isValidBonemealTarget(BlockGetter p_56091_, BlockPos p_56092_, BlockState p_56093_, boolean p_56094_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level p_56096_, Random p_56097_, BlockPos p_56098_, BlockState p_56099_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos sourcePos, BlockState sourceState) {
        if (!sourceState.canSurvive(level, sourcePos)) return;
        int j = 1;
        int l = 0;
        int spreadRange = this.getSpreadRange();
        int halfSpreadRange = spreadRange / 2;
        int i1 = sourcePos.getX() - halfSpreadRange;
        int j1 = 0;
        for(int k1 = 0; k1 < spreadRange; ++k1) {
            for(int l1 = 0; l1 < j; ++l1) {
                int startY = 2 + sourcePos.getY() - 1;
                for(int currentY = startY - 2; currentY < startY; ++currentY) {
                    BlockPos blockpos = new BlockPos(i1 + k1, currentY, sourcePos.getZ() - j1 + l1);
                    if (blockpos != sourcePos && random.nextInt(this.getSpreadChance()) == 0 && this.canGrowInto(level.getBlockState(blockpos))) {
                        BlockState blockState = this.getGrownBlockState(sourceState.getBlock(), sourceState);
                        if (blockState.canSurvive(level, blockpos)) {
                            level.setBlock(blockpos, blockState, 3);
                        }
                    }
                }
            }
            if (l < halfSpreadRange) {
                j += halfSpreadRange;
                ++j1;
            } else {
                j -= halfSpreadRange;
                --j1;
            }
            ++l;
        }
    }

    protected abstract boolean canGrowInto(BlockState p_54321_);

    protected abstract BlockState getGrownBlockState(Block sourceBlock, BlockState sourceState);

    protected abstract int getSpreadRange();

    protected abstract int getSpreadChance();
}
