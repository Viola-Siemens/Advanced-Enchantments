package com.hexagram2021.advanced_enchantments.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ThrownTrident.class, priority = 985)
public class ThrownTridentMixin {
	@Shadow
	private ItemStack tridentItem;

	@Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isThundering()Z"))
	private boolean ignoreOtherWeatherIfUsingChannelingII(Level instance) {
		return this.tridentItem.getEnchantmentLevel(Enchantments.CHANNELING) > 1 || instance.isThundering();
	}

	@Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;canSeeSky(Lnet/minecraft/core/BlockPos;)Z"))
	private boolean ignoreSeeSkyIfUsingChannelingII(Level instance, BlockPos blockPos) {
		return this.tridentItem.getEnchantmentLevel(Enchantments.CHANNELING) > 1 || instance.canSeeSky(blockPos);
	}
}
