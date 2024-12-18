package fuzs.universalbonemeal.init;

import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import fuzs.universalbonemeal.UniversalBoneMeal;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModRegistry {
    static final TagFactory TAGS = TagFactory.make(UniversalBoneMeal.MOD_ID);
    public static final TagKey<Block> FERTILIZER_RESISTANT_FLOWERS_BLOCK_TAG = TAGS.registerBlockTag(
            "fertilizer_resistant_flowers");

    public static void bootstrap() {
        // NO-OP
    }
}
