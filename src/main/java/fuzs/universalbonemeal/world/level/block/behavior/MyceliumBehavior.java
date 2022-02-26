package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.Random;

public class MyceliumBehavior implements BonemealBehavior {
    private static final WeightedStateProvider MYCELIUM_VEGETATION_PROVIDER = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.RED_MUSHROOM.defaultBlockState(), 1).add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 1));

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_55064_, BlockPos p_55065_, BlockState p_55066_, boolean p_55067_) {
        return p_55064_.getBlockState(p_55065_.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_55069_, Random p_55070_, BlockPos p_55071_, BlockState p_55072_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState blockState) {
        BlockPos blockpos = pos.above();
        this.place(level, random, blockpos);
    }

    private boolean place(ServerLevel level, Random random, BlockPos pos) {
        BlockState blockstate = ((WorldGenLevel) level).getBlockState(pos.below());
        int spreadWidth = 3;
        int spreadHeight = 1;
        if (!blockstate.is(Blocks.MYCELIUM)) {
            return false;
        } else {
            int i = pos.getY();
            if (i >= level.getMinBuildHeight() + 1 && i + 1 < level.getMaxBuildHeight()) {
                int j = 0;
                for(int k = 0; k < spreadWidth * spreadWidth; ++k) {
                    BlockPos blockpos1 = pos.offset(random.nextInt(spreadWidth) - random.nextInt(spreadWidth), random.nextInt(spreadHeight) - random.nextInt(spreadHeight), random.nextInt(spreadWidth) - random.nextInt(spreadWidth));
                    BlockState blockstate1 = MYCELIUM_VEGETATION_PROVIDER.getState(random, blockpos1);
                    if (level.isEmptyBlock(blockpos1) && blockpos1.getY() > level.getMinBuildHeight() && blockstate1.canSurvive(level, blockpos1)) {
                        ((WorldGenLevel) level).setBlock(blockpos1, blockstate1, 2);
                        ++j;
                    }
                }
                return j > 0;
            } else {
                return false;
            }
        }
    }
}
