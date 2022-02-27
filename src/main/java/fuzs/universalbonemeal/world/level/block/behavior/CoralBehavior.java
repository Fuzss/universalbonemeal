package fuzs.universalbonemeal.world.level.block.behavior;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CommonLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class CoralBehavior implements BonemealBehavior {
    @Override
    public boolean isValidBonemealTarget(BlockGetter p_54870_, BlockPos p_54871_, BlockState p_54872_, boolean p_54873_) {
        return Objects.equals(((CommonLevelAccessor) p_54870_).getBiomeName(p_54871_), Optional.of(Biomes.WARM_OCEAN));
    }

    @Override
    public boolean isBonemealSuccess(Level p_54875_, Random p_54876_, BlockPos p_54877_, BlockState p_54878_) {
        return (double) p_54876_.nextFloat() < 0.4D;
    }

    @Override
    public void performBonemeal(ServerLevel p_54865_, Random p_54866_, BlockPos p_54867_, BlockState p_54868_) {
        this.place(p_54865_, p_54867_, p_54868_, p_54866_);
    }

    private boolean place(ServerLevel p_54860_, BlockPos p_54861_, BlockState p_54862_, Random p_54863_) {
        p_54860_.removeBlock(p_54861_, false);
        if (this.placeFeature(p_54860_, p_54863_, p_54861_, this.getBlockEquivalent(p_54862_, p_54863_).defaultBlockState())) {
            return true;
        } else {
            p_54860_.setBlock(p_54861_, p_54862_, 3);
            return false;
        }
    }

    private Block getBlockEquivalent(BlockState blockState, Random random) {
        // hopeful this will be enough for mod compat with e.g. upgrade aquatic
        String name = ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).getPath();
        name = name.substring(0, name.indexOf("_coral"));
        for (Block block : BlockTags.CORAL_BLOCKS.getValues()) {
            if (ForgeRegistries.BLOCKS.getKey(block).getPath().contains(name)) {
                return block;
            }
        }
        return BlockTags.CORAL_BLOCKS.getRandomElement(random);
    }

    private boolean placeFeature(LevelAccessor level, Random random, BlockPos pos, BlockState blockState) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        int trunkHeight = random.nextInt(3) + 1;
        if (!this.isValidPosition(level, pos, trunkHeight)) return false;
        for(int j = 0; j < trunkHeight; ++j) {
            if (!this.placeCoralBlock(level, random, mutable, blockState, j == trunkHeight - 1)) {
                return j != 0;
            }
            mutable.move(Direction.UP);
        }
        BlockPos blockpos = mutable.immutable();
        int arms = random.nextInt(3) + 2;
        List<Direction> list = Lists.newArrayList(Direction.Plane.HORIZONTAL);
        Collections.shuffle(list, random);
        for (Direction direction : list.subList(0, arms)) {
            mutable.set(blockpos);
            mutable.move(direction);
            int armLength = random.nextInt(5) + 2;
            int i1 = 0;
            for (int j1 = 0; j1 < armLength && this.placeCoralBlock(level, random, mutable, blockState, true); ++j1) {
                ++i1;
                mutable.move(Direction.UP);
                if (j1 == 0 || i1 >= 2 && random.nextFloat() < 0.25F) {
                    mutable.move(direction);
                    i1 = 0;
                }
            }
        }
        return true;
    }

    private boolean isValidPosition(LevelAccessor p_65099_, BlockPos p_65100_, int height) {
        int i = p_65100_.getY();
        if (i >= p_65099_.getMinBuildHeight() + 1 && i + height + 1 < p_65099_.getMaxBuildHeight()) {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for(int j = 0; j <= height + 4; ++j) {
                int k = j < height ? 0 : (j - height) / 2 + 1;
                for(int l = -k; l <= k; ++l) {
                    for(int i1 = -k; i1 <= k; ++i1) {
                        BlockState blockstate1 = p_65099_.getBlockState(mutable.setWithOffset(p_65100_, l, j, i1));
                        if (!this.isCoralReplaceable(blockstate1)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isCoralReplaceable(BlockState blockstate1) {
        return blockstate1.is(Blocks.WATER) || blockstate1.is(BlockTags.CORAL_BLOCKS) || blockstate1.is(BlockTags.CORALS) || blockstate1.is(BlockTags.WALL_CORALS);
    }

    private boolean placeCoralBlock(LevelAccessor level, Random random, BlockPos pos, BlockState blockState, boolean decorateTop) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = level.getBlockState(pos);
        // allows coral trees to grow into each other, just like actual trees
        if (this.isCoralReplaceable(blockstate) && this.isCoralReplaceable(level.getBlockState(blockpos))) {
            level.setBlock(pos, blockState, 3);
            // vanilla always decorates top, resulting in trunks sometimes being cut off
            if (decorateTop) {
                if (random.nextFloat() < 0.25F) {
                    level.setBlock(blockpos, BlockTags.CORALS.getRandomElement(random).defaultBlockState(), 2);
                } else if (random.nextFloat() < 0.05F) {
                    level.setBlock(blockpos, Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.PICKLES, random.nextInt(4) + 1), 2);
                }
            }
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (random.nextFloat() < 0.2F) {
                    BlockPos blockpos1 = pos.relative(direction);
                    if (level.getBlockState(blockpos1).is(Blocks.WATER)) {
                        BlockState blockstate1 = BlockTags.WALL_CORALS.getRandomElement(random).defaultBlockState();
                        if (blockstate1.hasProperty(BaseCoralWallFanBlock.FACING)) {
                            blockstate1 = blockstate1.setValue(BaseCoralWallFanBlock.FACING, direction);
                        }
                        level.setBlock(blockpos1, blockstate1, 2);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
