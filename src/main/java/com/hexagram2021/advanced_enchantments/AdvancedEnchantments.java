package com.hexagram2021.advanced_enchantments;

import com.hexagram2021.advanced_enchantments.common.AEContent;
import com.hexagram2021.advanced_enchantments.common.config.AECommonConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
@Mod(AdvancedEnchantments.MODID)
public class AdvancedEnchantments {
	public static final String MODID = "advanced_enchantments";
	public static final String MODNAME = "Advanced Enchantments";
	public static final String VERSION = ModList.get().getModFileById(MODID).versionString();

	public AdvancedEnchantments() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		AEContent.modConstruction(bus);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AECommonConfig.getConfig());

		MinecraftForge.EVENT_BUS.register(this);
	}
}
