package com.hexagram2021.advanced_enchantments.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AECommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	private static final ForgeConfigSpec.BooleanValue CHANNELING;
	private static final ForgeConfigSpec.BooleanValue SILK_TOUCH;
	private static final ForgeConfigSpec.BooleanValue FLAME;
	private static final ForgeConfigSpec.BooleanValue INFINITY;

	private AECommonConfig() {}

	static {
		BUILDER.push("advanced_enchantments-common-config");
			BUILDER.comment("You can determine each enchantment enabled or disabled.");
			BUILDER.push("enchantments");
				CHANNELING = BUILDER.define("CHANNELING", true);
				SILK_TOUCH = BUILDER.define("SILK_TOUCH", true);
				FLAME = BUILDER.define("FLAME", true);
				INFINITY = BUILDER.define("INFINITY", true);
			BUILDER.pop();
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
