package com.hexagram2021.advanced_enchantments.utils;

import com.hexagram2021.advanced_enchantments.config.AEConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.trident.enchantment.TridentEnchantments;
import net.minecraft.trident.entity.EntityTrident;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
    public static boolean onlyOpsCanSetNbt(boolean isOK){
        return isOK && AEConfig.miscs.KEEP_ONLY_OPS_SET_NBT;
    }
    public static boolean checkDampEnchantment(boolean isWet, Entity entity){
        if (!isWet){
            for(ItemStack itemStack: entity.getEquipmentAndArmor()) {//Equipments, and the item held.
                if (EnchantmentHelper.getEnchantmentLevel(AEEnchantments.DampEnchantment.INSTANCE,itemStack)>0)return true;
            }
            return false;
        }else return true;
    }
    public static int hook$EnchantmentArrowInfinite$getMaxLevel(int level){
        if (AEConfig.enchantments.INFINITY){
            if (level<2)return 2;
            else return level;
        }else return level;
    }
    public static int hook$EnchantmentUntouching$getMaxLevel(int level){
        if (AEConfig.enchantments.SILK_TOUCH){
            if (level<2)return 2;
            else return level;
        }else return level;
    }
    public static int hook$EnchantmentArrowFire$getMaxLevel(int level){
        if (AEConfig.enchantments.FLAME){
            if (level<2)return 2;
            else return level;
        }else return level;
    }
    public static int hook$EnchantmentChanneling$getMaxLevel(int level){
        if (AEConfig.enchantments.CHANNELING){
            if (level<2)return 2;
            else return level;
        }else return level;
    }
    public static boolean proxyRedirect$EntityTrident$isThundering(boolean isOK,Entity entity){
        if (entity instanceof EntityTrident){
            EntityTrident trident=((EntityTrident)entity);
            if (EnchantmentHelper.getEnchantmentLevel(TridentEnchantments.CHANNELING, ReflectionHelper.getPrivateValue(EntityTrident.class, trident,"thrownStack")) <= 1)
                return isOK;
            else {
                return true;
            }
        }else return isOK;
    }

}
