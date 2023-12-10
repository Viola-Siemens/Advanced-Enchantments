package com.hexagram2021.advanced_enchantments.core;

import com.hexagram2021.advanced_enchantments.utils.AEASMUtil;
import com.hexagram2021.advanced_enchantments.utils.MethodName;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 23:15
 **/
@SuppressWarnings("unused")
public class AdvancedEnchantmentTransformer implements IClassTransformer {
    public static final HashMap<String, Function<ClassNode,Integer>> transformers=new HashMap<>();
    static {
        transformers.put("net.minecraft.entity.projectile.EntityArrow",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        /**
                         *public void setEnchantmentEffectsFromEntity(EntityLivingBase p_190547_1_, float p_190547_2_) {
                         *AEHooks.onArrowImpact(this, p_190547_1_);
                         **/
                        if (MethodName.m_190547.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,0));
                            hook.add(new IntInsnNode(Opcodes.ALOAD,1));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","onArrowImpact","(Lnet/minecraft/entity/projectile/EntityArrow;Lnet/minecraft/entity/EntityLivingBase;)V",false));
                            AEASMUtil.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.RETURN);
                        }
                        //It is not exist in 1.12.2. but hard code.
//                      @Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
//                       private void getEntityOnFireSeconds(Entity instance, int seconds) {
//                          instance.setSecondsOnFire(((AbstractArrow)(Object)this).getRemainingFireTicks() / 320);
//                      }
                        /**
                         * if (this.isBurning() && !(entity instanceof EntityEnderman)) {
                         *      AEHooks.setFire(entity, 5, this);
                         * }
                         * **/
                        if (MethodName.m_184549.is(mn)){
                            ListIterator<AbstractInsnNode> iterator=mn.instructions.iterator();
                            AbstractInsnNode ren;
                            while (iterator.hasNext()){
                                ren=iterator.next();
                                if (ren.getType()==AbstractInsnNode.METHOD_INSN){
                                    MethodInsnNode methodInsnNode=(MethodInsnNode) ren;
                                    if (methodInsnNode.getOpcode()==Opcodes.INVOKEVIRTUAL){
                                        if ("net/minecraft/entity/Entity".equals(methodInsnNode.owner) || "vg".equals(methodInsnNode.owner)){
                                            if (MethodName.m_70015.is(methodInsnNode.name,methodInsnNode.desc)){
                                                iterator.remove();
                                                iterator.add(new IntInsnNode(Opcodes.ALOAD,0));
                                                iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","setFire","(Lnet/minecraft/entity/Entity;ILnet/minecraft/entity/projectile/EntityArrow;)V",false));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        transformers.put("net.minecraft.item.ItemArrow",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        /**
                         * public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
                         *         int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
                         *         return AEHooks.isInfinite(enchant <= 0 ? false : this.getClass() == ItemArrow.class, enchant);
                         * }
                         * **/
                        if (MethodName.m_isInfinite.is(mn)){
                            ListIterator<AbstractInsnNode> iterator=mn.instructions.iterator();
                            while (iterator.hasNext()){
                                if (iterator.next().getOpcode()==Opcodes.IRETURN){
                                    iterator.remove();
                                    iterator.add(new IntInsnNode(Opcodes.ILOAD,4));
                                    iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","isInfinite","(ZI)Z",false));
                                    iterator.add(new InsnNode(Opcodes.IRETURN));
                                }
                            }
                        }
                        /**
                         *  public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter)
                         *         return AEHooks.createArrow(entitytippedarrow, shooter);
                         * **/
                        if (MethodName.m_185052.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,3));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","createArrow","(Lnet/minecraft/entity/projectile/EntityArrow;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/entity/projectile/EntityArrow;",false));
                            AEASMUtil.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        transformers.put("net.minecraft.item.ItemBlock",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        /**
                         * if (!worldIn.isRemote && AEHooks.onlyOpsCanSetNbt(tileentity.onlyOpsCanSetNbt()) && (player == null || !player.canUseCommandBlock())) {
                         *        return false;
                         * }
                         * **/
                        if (MethodName.m_179224.is(mn)){
                            AbstractInsnNode ren=null;
                            ListIterator<AbstractInsnNode> iterator=mn.instructions.iterator();
                            while (iterator.hasNext()){
                                ren=iterator.next();
                                if (ren.getType()==AbstractInsnNode.METHOD_INSN){
                                    MethodInsnNode methodInsnNode=(MethodInsnNode) ren;
                                    if (methodInsnNode.getOpcode()==Opcodes.INVOKEVIRTUAL){
                                        if ("net/minecraft/tileentity/TileEntity".equals(methodInsnNode.owner) || "avj".equals(methodInsnNode.owner)){
                                            if (MethodName.m_183000.is(methodInsnNode.name,methodInsnNode.desc)){
                                                iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","onlyOpsCanSetNbt","(Z)Z",false));
                                                return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        transformers.put("net.minecraft.entity.Entity",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        /**
                         * public boolean isWet() {
                         *         if (this.inWater) {
                         +             return AEHooks.checkDampEnchantment(true, this);
                         *         } else {
                         *             BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain(this.posX, this.posY, this.posZ);
                         *             if (!this.world.isRainingAt(blockpos$pooledmutableblockpos) && !this.world.isRainingAt(blockpos$pooledmutableblockpos.setPos(this.posX, this.posY + (double)this.height, this.posZ))) {
                         *                 blockpos$pooledmutableblockpos.release();
                         +                 return AEHooks.checkDampEnchantment(false, this);
                         *             } else {
                         *                 blockpos$pooledmutableblockpos.release();
                         +                return AEHooks.checkDampEnchantment(true, this);
                         *             }
                         *         }
                         *     }
                         * **/
                        if (MethodName.m_70026.is(mn)){
                            ListIterator<AbstractInsnNode> iterator=mn.instructions.iterator();
                            while (iterator.hasNext()){
                                if (iterator.next().getOpcode()==Opcodes.IRETURN){
                                    iterator.remove();
                                    iterator.add(new IntInsnNode(Opcodes.ALOAD,0));
                                    iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","checkDampEnchantment","(ZLnet/minecraft/entity/Entity;)Z",false));
                                    iterator.add(new InsnNode(Opcodes.IRETURN));
                                }
                            }
                            return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        //in 1.12.2, cost is judged by anvil, not enchantment.//modifyMinCost//modifyMaxCost
        transformers.put("net.minecraft.enchantment.EnchantmentArrowFire",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        /**
                         * public int getMaxLevel() {
                         *         return AEHooks.hook$EnchantmentArrowFire$getMaxLevel(1);
                         * }
                         * **/
                        if (MethodName.m_77325.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","hook$EnchantmentArrowFire$getMaxLevel","(I)I",false));
                            AEASMUtil.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.IRETURN);
                            return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        transformers.put("net.minecraft.enchantment.EnchantmentArrowInfinite",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        /**
                         * public int getMaxLevel() {
                         *         return AEHooks.hook$EnchantmentArrowInfinite$getMaxLevel(1);
                         * }
                         * **/
                        if (MethodName.m_77325.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","hook$EnchantmentArrowInfinite$getMaxLevel","(I)I",false));
                            AEASMUtil.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.IRETURN);
                            return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        transformers.put("net.minecraft.enchantment.EnchantmentUntouching",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        if (MethodName.m_77325.is(mn)){
                            /**
                             * public int getMaxLevel() {
                             *          return AEHooks.hook$EnchantmentUntouching$getMaxLevel(1);
                             * }
                             * **/
                            InsnList hook=new InsnList();
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","hook$EnchantmentUntouching$getMaxLevel","(I)I",false));
                            AEASMUtil.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.IRETURN);
                            return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        //Trident compat
        transformers.put("net.minecraft.trident.enchantment.EnchantmentChanneling",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        /**
                         *  public int getMaxLevel() {
                         *         return AEHooks.Trident.hook$EnchantmentChanneling$getMaxLevel(1);
                         *  }
                         * **/
                        if (MethodName.m_77325.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks$Trident","hook$EnchantmentChanneling$getMaxLevel","(I)I",false));
                            AEASMUtil.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.IRETURN);
                            return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });
        transformers.put("net.minecraft.trident.entity.EntityTrident",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        /**
                         * float volume = 1.0F;
                         *if (AEHooks.Trident.proxyRedirect$EntityTrident$isThundering(this.world.isThundering(), this) && TridentEnchantments.hasChanneling(this.thrownStack)) {
                         *
                         * **/
                        if ("onHitEntity".equals(mn.name)){//only name
                            AbstractInsnNode ren=null;
                            ListIterator<AbstractInsnNode> iterator=mn.instructions.iterator();
                            while (iterator.hasNext()){
                                ren=iterator.next();
                                if (ren.getType()==AbstractInsnNode.METHOD_INSN){
                                    MethodInsnNode methodInsnNode=(MethodInsnNode) ren;
                                    if (methodInsnNode.getOpcode()==Opcodes.INVOKEVIRTUAL){
                                        if ("net/minecraft/world/World".equals(methodInsnNode.owner) || "amu".equals(methodInsnNode.owner)){
                                            if (MethodName.m_72911.is(methodInsnNode.name,methodInsnNode.desc)){
                                                iterator.add(new IntInsnNode(Opcodes.ALOAD,0));
                                                iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks$Trident","proxyRedirect$EntityTrident$isThundering","(ZLnet/minecraft/trident/entity/EntityTrident;)Z",false));
                                                return ClassWriter.COMPUTE_MAXS;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES;
                });

    }
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass!=null && basicClass.length>0){
            try{
                if (transformers.containsKey(transformedName)){
                    ClassReader classReader=new ClassReader(basicClass);
                    ClassNode cn=new ClassNode();
                    classReader.accept(cn, 0);

                    int flags=transformers.get(transformedName).apply(cn);

                    ClassWriter classWriter=new ClassWriter(classReader,flags);
                    cn.accept(classWriter);
                    return AEASMUtil.push(transformedName,classWriter.toByteArray());
                }else return basicClass;
            }catch (Exception e){
                e.printStackTrace();
                return basicClass;
            }
        }
        return basicClass;
    }
}
