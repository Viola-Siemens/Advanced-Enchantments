package com.hexagram2021.advanced_enchantments.core;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.hexagram2021.advanced_enchantments.AdvancedEnchantments;
import com.hexagram2021.advanced_enchantments.utils.AEASMUtils;
import com.hexagram2021.advanced_enchantments.utils.AEEnchantments;
import com.hexagram2021.advanced_enchantments.utils.AEForgeEventHandler;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 12:34
 **/
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.Name(AdvancedEnchantments.MODID)
public class AdvancedEnchantmentsPlugin implements IFMLLoadingPlugin{
    public static File source=null;
    public AdvancedEnchantmentsPlugin(){

    }
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "com.hexagram2021.advanced_enchantments.core.AdvancedEnchantmentTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "com.hexagram2021.advanced_enchantments.core.AdvancedEnchantmentsPlugin$Container";
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        source=(File)data.get("coremodLocation");
        AEASMUtils.gameDir=(File)data.get("mcLocation");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public static class Container extends DummyModContainer{
        public Container(){
            super(new ModMetadata());
            ModMetadata metadata=this.getMetadata();
            metadata.name="Advanced Enchantments";
            metadata.modId=AdvancedEnchantments.MODID;
            metadata.description="This mod adds advanced versions for some of the vanilla enchantments.\n" +
                    "\n" +
                    "Channeling II: causes a lightning bolt wherever and whenever it is after hitting an entity.\n" +
                    "Silk Touch II: you can mine spawners, reinforced deepslate, budding amethyst and some other blocks (with nbt!).\n" +
                    "Flame II: makes entities burns longer time after hit by arrows.\n" +
                    "Infinity II: also makes spectral arrows and tipped arrows infine.\n" +
                    "\n" +
                    "And it also adds an enchantment:\n" +
                    "\n" +
                    "Damp (for chestplates and elytras): you can use tridents with riptide enchantment, take the conduit power effect from conduit in any case, even not in water.\n" +
                    "\n" +
                    "Idealland - they provided this framework for dev-environment.";
            metadata.url="https://www.mcmod.cn/class/12832.html";
            metadata.authorList.add("Liu Dongyu");
            metadata.authorList.add("Mnibr");
            metadata.authorList.add("Hileb");
            metadata.logoFile="logo.png";
            metadata.version=AdvancedEnchantments.VERSION;
            metadata.credits="Hexagram";
        }

        @Override
        public boolean registerBus(EventBus bus, LoadController controller) {
            bus.register(this);
            return true;
        }
        @Override
        public File getSource() {
            return AdvancedEnchantmentsPlugin.source;
        }
        @Subscribe
        public void construct(FMLConstructionEvent event){
            ConfigManager.sync(this.getModId(), Config.Type.INSTANCE);
        }
        @Subscribe
        public void preInit(FMLPreInitializationEvent event){
            MinecraftForge.EVENT_BUS.register(AEEnchantments.class);
            MinecraftForge.EVENT_BUS.register(AEForgeEventHandler.class);
        }
    }
}
