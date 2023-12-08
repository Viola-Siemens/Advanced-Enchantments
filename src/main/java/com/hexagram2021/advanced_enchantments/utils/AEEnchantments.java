package com.hexagram2021.advanced_enchantments.utils;

import com.hexagram2021.advanced_enchantments.AdvancedEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 23:16
 **/
public class AEEnchantments {
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Enchantment> event){
        event.getRegistry().register(DampEnchantment.INSTANCE);
    }
    public static class DampEnchantment extends Enchantment{
        public static final DampEnchantment INSTANCE=new DampEnchantment();

        private DampEnchantment() {
            super(Rarity.VERY_RARE,EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
            setRegistryName(new ResourceLocation(AdvancedEnchantments.MODID,"damp"));
        }

        @Override
        public boolean canApply(ItemStack stack) {
            return stack.getItem() instanceof ItemElytra || super.canApply(stack);
        }
    }
}
