package com.hexagram2021.advanced_enchantments.utils;

import org.objectweb.asm.tree.MethodNode;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/9 12:38
 **/
public class MethodName {
    public static final MethodName m_190547=of()
            .mcp("setEnchantmentEffectsFromEntity","(Lnet/minecraft/entity/EntityLivingBase;F)V")
            .srg("func_190547_a","(Lnet/minecraft/entity/EntityLivingBase;F)V")
            .notch("a","(Lvp;F)V")
            .build();
    public static final MethodName m_185052=of()
            .mcp("createArrow","(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/entity/projectile/EntityArrow;")
            .srg("func_185052_a","(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/entity/projectile/EntityArrow;")
            .notch("a","(Lamu;Laip;Lvp;)Laeh;")
            .build();
    public static final MethodName m_isInfinite=of()//TODO:find out srg name;
            .mcp("isInfinite","(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;)Z")
            .build();
    public static final MethodName m_179224=of()
            .mcp("setTileEntityNBT","(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)Z")
            .srg("func_179224_a","(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)Z")
            .notch("a","(Lamu;Laed;Let;Laip;)Z")
            .build();
    public static final MethodName m_183000=of()
            .mcp("onlyOpsCanSetNbt","()Z")
            .srg("func_183000_F","()Z")
            .notch("C","()Z")
            .build();
    public static final MethodName m_70026=of()
            .mcp("isWet","()Z")
            .srg("func_70026_G","()Z")
            .notch("an","()Z")
            .build();
    public static final MethodName m_77325=of()
            .mcp("getMaxLevel","()I")
            .srg("func_77325_b","()I")
            .notch("b","()I")
            .build();

















    //////___________________________//////
    public String mcpName;public String mcpDesc;
    public String srgName;public String srgDesc;
    public String notchName;public String notchDesc;
    MethodName(){}
    public static Builder of(){
        return new Builder();
    }
    public boolean is(MethodNode mn){
        return is(mn.name,mn.desc);
    }
    public boolean is(String name,String desc){
        if (srgName.equals(name))return true;
        else if (mcpName.equals(name) && mcpDesc.equals(desc))return true;
        else return notchName.equals(name) && notchDesc.equals(desc);
    }
    private static class Builder{
        MethodName methodName;
        public Builder(){
            methodName=new MethodName();
            methodName.notchName="<null>";
            methodName.mcpName="<null>";
            methodName.srgName="<null>";
            methodName.notchDesc="<null>";
            methodName.mcpDesc="<null>";
            methodName.srgDesc="<null>";
        }
        public Builder mcp(String name,String desc){
            methodName.mcpName=name;
            methodName.mcpDesc=desc;
            return this;
        }
        public Builder srg(String name,String desc){
            methodName.srgName=name;
            methodName.srgDesc=desc;
            return this;
        }
        public Builder notch(String name,String desc){
            methodName.notchName=name;
            methodName.notchDesc=desc;
            return this;
        }
        public MethodName build(){
            return methodName;
        }
    }
}
