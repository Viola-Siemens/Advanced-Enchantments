package com.hexagram2021.advanced_enchantments.common;

import com.hexagram2021.advanced_enchantments.common.init.AEEnchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

import static com.hexagram2021.advanced_enchantments.AdvancedEnchantments.MODID;

public class AEContent {
	public static void modConstruction(IEventBus bus) {
		AEEnchantments.init(bus);
	}

	public static void init() {
	}
}
