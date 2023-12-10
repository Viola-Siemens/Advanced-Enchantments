package com.hexagram2021.advanced_enchantments.utils;

import com.hexagram2021.advanced_enchantments.config.AEConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.trident.enchantment.TridentEnchantments;
import net.minecraft.trident.entity.EntityTrident;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 23:32
 **/
public class AEHooks {
    public static void onArrowImpact(EntityArrow arrow,EntityLivingBase livingBase){
        int level=EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, livingBase);
        if (level > 1)
        {
            arrow.setFire(240);
            arrow.getEntityData().setInteger("ae.fireII",level);
        }
    }
    public static boolean isInfinite(boolean isOK,int ench)
    {
       return isOK || ench>1;
    }
    public static void setFire(Entity entity,int ignored,EntityArrow _this){
        if (_this.getEntityData().hasKey("ae.fireII",3)){
            int level=_this.getEntityData().getInteger("ae.fireII");
            if (level>1){//give player some unexpected.
                entity.setFire(level*5 + 2);
            }else entity.setFire(5);
        }
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
    public static void onSilkIIDrops(Block block, World worldIn, BlockPos pos, IBlockState state){

    }

}
