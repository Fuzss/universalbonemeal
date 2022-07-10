package fuzs.universalbonemeal.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface CommonAbstractions {

    boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, Block plantable);
}
