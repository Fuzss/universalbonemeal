package fuzs.universalbonemeal.mixin;

import fuzs.universalbonemeal.api.event.entity.player.BonemealCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin extends Item {
    public BoneMealItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "growCrop", at = @At("HEAD"), cancellable = true)
    private static void growCrop$head(ItemStack itemStack, Level level, BlockPos blockPos, CallbackInfoReturnable<Boolean> callbackInfo) {
        InteractionResult result = BonemealCallback.EVENT.invoker().onBonemeal(level, blockPos, level.getBlockState(blockPos), itemStack);
        if (result != InteractionResult.PASS) {
            if (result == InteractionResult.SUCCESS) {
                if (!level.isClientSide) {
                    itemStack.shrink(1);
                }
                callbackInfo.setReturnValue(true);
            } else {
                callbackInfo.setReturnValue(false);
            }
        }
    }
}
