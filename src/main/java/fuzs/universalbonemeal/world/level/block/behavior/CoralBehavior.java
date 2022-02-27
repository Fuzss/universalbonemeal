package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CommonLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

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
        this.growMushroom(p_54865_, p_54867_, p_54868_, p_54866_);
    }

    public boolean growMushroom(ServerLevel p_54860_, BlockPos p_54861_, BlockState p_54862_, Random p_54863_) {
        p_54860_.removeBlock(p_54861_, false);
        if (AquaticFeatures.WARM_OCEAN_VEGETATION.place(p_54860_, p_54860_.getChunkSource().getGenerator(), p_54863_, p_54861_)) {
            return true;
        } else {
            p_54860_.setBlock(p_54861_, p_54862_, 3);
            return false;
        }
    }
}
