package com.hexagram2021.advanced_enchantments.common.init;

import com.hexagram2021.advanced_enchantments.common.enchantments.DampEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.advanced_enchantments.AdvancedEnchantments.MODID;

public final class AEEnchantments {
	private static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);

	public static final RegistryObject<DampEnchantment> DAMP = REGISTER.register("damp", DampEnchantment::new);

	private AEEnchantments() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
