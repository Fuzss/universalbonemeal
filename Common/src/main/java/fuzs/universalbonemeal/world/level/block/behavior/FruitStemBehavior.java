package fuzs.universalbonemeal.world.level.block.behavior;

import fuzs.universalbonemeal.core.CommonAbstractions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FruitStemBehavior implements BonemealBehavior {

    @Override
    public boolean isValidBonemealTarget(LevelReader p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        // let vanilla run otherwise
        if (p_50899_.getValue(StemBlock.AGE) != 7) return false;
        // no need to check if attached to a fruit already, since attached stems are completely different block for some reason
        return Direction.Plane.HORIZONTAL.stream().anyMatch((p_153316_) -> {
            return this.canSustainPlant((LevelReader) p_50897_, p_50898_, p_50899_, p_153316_);
        });
    }

    private boolean canSustainPlant(LevelReader level, BlockPos sourcePos, BlockState sourceBlock, Direction direction) {
        BlockPos blockpos = sourcePos.relative(direction);
        BlockState blockstate = level.getBlockState(blockpos.below());
        Block block = blockstate.getBlock();
        return level.isEmptyBlock(blockpos) && (CommonAbstractions.INSTANCE.canSustainPlant(blockstate, level, blockpos.below(), Direction.UP, sourceBlock.getBlock()) || block == Blocks.FARMLAND || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.GRASS_BLOCK);
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, RandomSource p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_57021_, RandomSource p_57022_, BlockPos p_57023_, BlockState p_57024_) {
        p_57024_.randomTick(p_57021_, p_57023_, p_57021_.random);
    }
}
