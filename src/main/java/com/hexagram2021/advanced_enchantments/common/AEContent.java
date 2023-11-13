package com.hexagram2021.advanced_enchantments.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

import static com.hexagram2021.advanced_enchantments.AdvancedEnchantments.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AEContent {
	public static void modConstruction(IEventBus bus) {
	}

	public static void init() {
	}

	@SubscribeEvent
	public static void onRegister(RegisterEvent event) {

	}
}
