package com.hexagram2021.advanced_enchantments.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class AECommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.BooleanValue CHANNELING;
	public static final ForgeConfigSpec.BooleanValue SILK_TOUCH;
	public static final ForgeConfigSpec.BooleanValue SILK_TOUCH_WITH_NBT;
	public static final ForgeConfigSpec.BooleanValue FLAME;
	public static final ForgeConfigSpec.BooleanValue INFINITY;
	public static final ForgeConfigSpec.BooleanValue KEEP_ONLY_OPS_SET_NBT;

	private AECommonConfig() {
	}

	static {
		BUILDER.push("advanced_enchantments-common-config");
			BUILDER.comment("You can determine each enchantment enabled or disabled.");
			BUILDER.push("enchantments");
				CHANNELING = BUILDER.define("CHANNELING", true);
				SILK_TOUCH = BUILDER.define("SILK_TOUCH", true);
				SILK_TOUCH_WITH_NBT = BUILDER.define("SILK_TOUCH_WITH_NBT", false);
				FLAME = BUILDER.define("FLAME", true);
				INFINITY = BUILDER.define("INFINITY", true);
			BUILDER.pop();
			BUILDER.push("miscs");
				KEEP_ONLY_OPS_SET_NBT = BUILDER.comment("If true, some block entities (eg. spawner, lectern) can not be placed from itemstack with nbt. If false, this feature from vanilla will be disabled.").define("KEEP_ONLY_OPS_SET_NBT", true);
			BUILDER.pop();
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
