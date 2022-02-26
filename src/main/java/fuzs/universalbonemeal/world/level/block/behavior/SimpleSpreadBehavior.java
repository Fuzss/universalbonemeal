package fuzs.universalbonemeal.world.level.block.behavior;

import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class SimpleSpreadBehavior extends SpreadAroundBehavior {
    public SimpleSpreadBehavior(BlockStateProvider blockStateProvider) {
        super(blockStateProvider);
    }

    @Override
    protected int getSpreadWidth() {
        return 3;
    }

    @Override
    protected int getSpreadHeight() {
        return 3;
    }
}
