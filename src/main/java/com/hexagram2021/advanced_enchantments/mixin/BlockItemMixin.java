package com.hexagram2021.advanced_enchantments.mixin;

import com.hexagram2021.advanced_enchantments.common.config.AECommonConfig;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public class BlockItemMixin {
	@Redirect(method = "updateCustomBlockEntityTag(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;onlyOpCanSetNbt()Z"))
	private static boolean disableOrOnlyOp(BlockEntity instance) {
		return AECommonConfig.KEEP_ONLY_OPS_SET_NBT.get() && instance.onlyOpCanSetNbt();
	}
}
