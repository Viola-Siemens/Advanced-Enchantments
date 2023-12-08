package com.hexagram2021.advanced_enchantments.config;

import com.hexagram2021.advanced_enchantments.AdvancedEnchantments;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 12:36
 **/
@Config(modid = AdvancedEnchantments.MODID)
public class AEConfig {
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(AdvancedEnchantments.MODID)) {
            ConfigManager.sync(AdvancedEnchantments.MODID, Config.Type.INSTANCE);
        }
    }
    @Config.Comment("You can determine each enchantment enabled or disabled.")
    public static final PEnchantment enchantments=new PEnchantment();
    public static class PEnchantment{
        public boolean CHANNELING=true;
        public boolean SILK_TOUCH=true;
        public boolean SILK_TOUCH_WITH_NBT=false;
        public boolean FLAME=true;
        public boolean INFINITY=true;
    }
    public static final PMiscs miscs=new PMiscs();
    public static class PMiscs{
        @Config.Comment("If true, some block entities (eg. spawner, lectern) can not be placed from itemstack with nbt. If false, this feature from vanilla will be")
        public boolean KEEP_ONLY_OPS_SET_NBT=true;
    }
}
