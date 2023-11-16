package com.hexagram2021.advanced_enchantments.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DampEnchantment extends Enchantment {
	public DampEnchantment() {
		this(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST);
	}

	@SuppressWarnings("SameParameterValue")
	protected DampEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMinCost(int level) {
		return level * 10;
	}

	@Override
	public int getMaxCost(int level) {
		return level * 30 + 20;
	}

	@Override
	public boolean canEnchant(ItemStack itemStack) {
		return itemStack.getItem() instanceof ElytraItem || super.canEnchant(itemStack);
	}
}
