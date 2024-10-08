package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class SimpleGrowingPlantBehavior extends GrowingPlantBehavior {

    public SimpleGrowingPlantBehavior() {
        super(Direction.UP);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos blockPos, BlockState blockState) {
        if (this.getConnectedPlantHeight(level, blockPos, blockState.getBlock()) < this.getMaxHeightAtPosition(
                blockPos.getX(), blockPos.getZ())) {
            return super.isValidBonemealTarget(level, blockPos, blockState);
        }
        return false;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1 + random.nextInt(2);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected BlockState getGrownBlockState(Block sourceBlock, BlockState sourceState) {
        return sourceBlock.defaultBlockState();
    }

    private int getConnectedPlantHeight(BlockGetter blockGetter, BlockPos pos, Block block) {
        BlockPos pos1 = getTopConnectedBlock(blockGetter, pos, block, this.growthDirection);
        BlockPos pos2 = getTopConnectedBlock(blockGetter, pos, block, this.growthDirection.getOpposite());
        return Math.abs(pos1.getY() - pos2.getY());
    }

    private int getMaxHeightAtPosition(int posX, int posZ) {
        // always use 0 seed, as client does not have access to world seed
        return 12 + WorldgenRandom.seedSlimeChunk(posX, posZ, 0, 987234911L).nextInt(5);
    }
}
