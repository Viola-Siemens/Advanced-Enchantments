package com.hexagram2021.advanced_enchantments.common;

import com.hexagram2021.advanced_enchantments.common.init.AEEnchantments;
import net.minecraftforge.eventbus.api.IEventBus;

public class AEContent {
	public static void modConstruction(IEventBus bus) {
		AEEnchantments.init(bus);
	}
}
