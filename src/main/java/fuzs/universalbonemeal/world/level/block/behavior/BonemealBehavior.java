package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;

public interface BonemealBehavior extends BonemealableBlock {
    @FunctionalInterface
    interface Factory {
        BonemealBehavior create(Block block);
    }
}
