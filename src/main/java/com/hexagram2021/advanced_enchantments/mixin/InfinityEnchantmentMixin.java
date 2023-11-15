package com.hexagram2021.advanced_enchantments.mixin;

import com.hexagram2021.advanced_enchantments.common.config.AECommonConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.ArrowInfiniteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArrowInfiniteEnchantment.class)
public class InfinityEnchantmentMixin extends Enchantment {
	protected InfinityEnchantmentMixin(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMaxLevel() {
		if(AECommonConfig.INFINITY.get()) {
			return 2;
		}
		return super.getMaxLevel();
	}

	@Inject(method = "getMinCost", at = @At(value = "RETURN"), cancellable = true)
	private void modifyMinCost(int level, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(cir.getReturnValue() + (level - 1) * 20);
	}

	@Inject(method = "getMaxCost", at = @At(value = "RETURN"), cancellable = true)
	private void modifyMaxCost(int level, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(cir.getReturnValue() + (level - 1) * 20);
	}
}
