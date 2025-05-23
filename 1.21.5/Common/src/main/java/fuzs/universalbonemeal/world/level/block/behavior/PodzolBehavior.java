package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class PodzolBehavior implements BoneMealBehavior {
    private static final BlockStateProvider PODZOL_VEGETATION_PROVIDER = new WeightedStateProvider(WeightedList.<BlockState>builder()
            .add(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3), 2)
            .add(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 2), 4)
            .add(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 1), 8)
            .add(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 0), 12)
            .add(Blocks.FERN.defaultBlockState(), 120)
            .add(Blocks.DEAD_BUSH.defaultBlockState(), 1));

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos blockPos, BlockState blockState) {
        return level.getBlockState(blockPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos blockPos, BlockState blockState) {
        BlockPos sourcePosition = blockPos.above();
        BlockState fernState = Blocks.FERN.defaultBlockState();

        label:
        for (int i = 0; i < 128; ++i) {
            BlockPos randomPosition = sourcePosition;

            for (int j = 0; j < i / 16; ++j) {
                randomPosition = randomPosition.offset(random.nextInt(3) - 1,
                        (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                        random.nextInt(3) - 1);
                if (!level.getBlockState(randomPosition.below()).is(Blocks.PODZOL) ||
                        level.getBlockState(randomPosition).isCollisionShapeFullBlock(level, randomPosition)) {
                    continue label;
                }
            }

            BlockState stateAtRandomPosition = level.getBlockState(randomPosition);
            if (stateAtRandomPosition.is(fernState.getBlock()) && random.nextInt(10) == 0) {

                ((BonemealableBlock) fernState.getBlock()).performBonemeal(level,
                        random,
                        randomPosition,
                        stateAtRandomPosition);
            }

            if (stateAtRandomPosition.isAir()) {
                if (random.nextInt(5) == 0) {

                    if (level.isEmptyBlock(randomPosition) && randomPosition.getY() > level.getMinY()) {
                        BlockState stateToPlace = PODZOL_VEGETATION_PROVIDER.getState(random, randomPosition);
                        level.setBlock(randomPosition, stateToPlace, 2);
                    }
                }

            }
        }

    }
}
