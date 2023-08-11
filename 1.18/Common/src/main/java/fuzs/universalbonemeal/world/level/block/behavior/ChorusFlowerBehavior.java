package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ChorusFlowerBehavior implements BonemealBehavior {

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState blockState, boolean isClient) {
        return blockState.hasProperty(ChorusFlowerBlock.AGE) && blockState.getValue(ChorusFlowerBlock.AGE) < 5;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState blockState) {
        blockState.randomTick(level, pos, random);
    }
}
