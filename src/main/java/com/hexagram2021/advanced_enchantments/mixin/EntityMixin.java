package com.hexagram2021.advanced_enchantments.mixin;

import com.hexagram2021.advanced_enchantments.common.init.AEEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "isInWaterOrRain", at = @At(value = "RETURN"), cancellable = true)
	private void checkDampEnchantment(CallbackInfoReturnable<Boolean> cir) {
		if(!cir.getReturnValue()) {
			Entity current = (Entity)(Object)this;
			for(ItemStack itemStack: current.getArmorSlots()) {
				if(itemStack.getEnchantmentLevel(AEEnchantments.DAMP.get()) > 0) {
					cir.setReturnValue(true);
					return;
				}
			}
		}
	}
}
