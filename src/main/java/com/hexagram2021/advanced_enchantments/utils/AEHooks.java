package com.hexagram2021.advanced_enchantments.utils;

import com.hexagram2021.advanced_enchantments.config.AEConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 23:32
 **/
public class AEHooks {
    public static void onArrowImpact(EntityArrow arrow,EntityLivingBase livingBase){
        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.INFINITY, livingBase) > 1)
        {
            arrow.setFire(240);
        }
    }
    public static boolean isInfinite(boolean rv,EntityPlayer player)
    {
        return rv || EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME,player.getHeldItem(player.getActiveHand()))>=1;
    }
    public static EntityArrow createArrow(EntityArrow arrow,EntityLivingBase shooter){
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME,shooter.getHeldItem(shooter.getActiveHand()))>1){
            arrow.setFire(240);
        }
        return arrow;
    }
    public static boolean onlyOpsCanSetNbt(TileEntity entity){
        return entity.onlyOpsCanSetNbt() && AEConfig.miscs.KEEP_ONLY_OPS_SET_NBT;
    }
}
