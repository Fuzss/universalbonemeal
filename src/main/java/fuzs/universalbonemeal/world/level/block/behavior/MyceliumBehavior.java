package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.Random;

public class MyceliumBehavior extends SpreadAroundBehavior {
    private static final BlockStateProvider MYCELIUM_VEGETATION_PROVIDER = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.RED_MUSHROOM.defaultBlockState(), 1).add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 1));

    public MyceliumBehavior() {
        super(MYCELIUM_VEGETATION_PROVIDER);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_55064_, BlockPos p_55065_, BlockState p_55066_, boolean p_55067_) {
        return p_55064_.getBlockState(p_55065_.above()).isAir();
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState blockState) {
        super.performBonemeal(level, random, pos.above(), blockState);
    }

    @Override
    protected int getSpreadWidth() {
        return 3;
    }

    @Override
    protected int getSpreadHeight() {
        return 1;
    }
}
