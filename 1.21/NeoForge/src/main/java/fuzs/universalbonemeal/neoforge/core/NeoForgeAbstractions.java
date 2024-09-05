package fuzs.universalbonemeal.neoforge.core;

import fuzs.universalbonemeal.core.CommonAbstractions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;

public class NeoForgeAbstractions implements CommonAbstractions {

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, Block plantable) {
        return state.canSustainPlant(level, pos, facing, (IPlantable) plantable);
    }
}
