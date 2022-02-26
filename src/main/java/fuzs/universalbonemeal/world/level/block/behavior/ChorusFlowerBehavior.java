package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ChorusFlowerBehavior implements BonemealBehavior {
    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return p_50899_.getValue(ChorusFlowerBlock.AGE) < 5;
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_57021_, Random p_57022_, BlockPos p_57023_, BlockState p_57024_) {
        p_57024_.randomTick(p_57021_, p_57023_, p_57021_.random);
    }
}
