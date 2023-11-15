package com.hexagram2021.advanced_enchantments.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {
	@Inject(method = "setEnchantmentEffectsFromEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;setSecondsOnFire(I)V", shift = At.Shift.AFTER))
	private void burnLongerTimeIfUsingFlameII(LivingEntity launcher, float strength, CallbackInfo ci) {
		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, launcher) > 1) {
			((AbstractArrow)(Object)this).setSecondsOnFire(240);
		}
	}

	@Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
	private void getEntityOnFireSeconds(Entity instance, int seconds) {
		instance.setSecondsOnFire(((AbstractArrow)(Object)this).getRemainingFireTicks() / 320);
	}
}
