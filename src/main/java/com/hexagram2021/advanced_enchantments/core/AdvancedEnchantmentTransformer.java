package com.hexagram2021.advanced_enchantments.core;

import com.hexagram2021.advanced_enchantments.utils.AEASMUtils;
import com.hexagram2021.advanced_enchantments.utils.MethodName;
import net.minecraft.block.BlockAnvil;
import net.minecraft.init.Enchantments;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 23:15
 **/
@SuppressWarnings("unused")
public class AdvancedEnchantmentTransformer implements IClassTransformer {
    public static final HashMap<String, Consumer<ClassNode>> transformers=new HashMap<>();
    static {
        transformers.put("net.minecraft.entity.projectile.EntityArrow",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        if (MethodName.m_190547.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,0));
                            hook.add(new IntInsnNode(Opcodes.ALOAD,1));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","onArrowImpact","(Lnet/minecraft/entity/projectile/EntityArrow;Lnet/minecraft/entity/EntityLivingBase;)V",false));
                            AEASMUtils.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.RETURN);
                        }
                        //It is not exist in 1.12.2.
//                      @Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
//                       private void getEntityOnFireSeconds(Entity instance, int seconds) {
//                          instance.setSecondsOnFire(((AbstractArrow)(Object)this).getRemainingFireTicks() / 320);
//                      }
                    }
                });
        transformers.put("net.minecraft.item.ItemArrow",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        if (MethodName.m_isInfinite.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,4));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","isInfinite","(Lnet/minecraft/entity/player/EntityPlayer;)Z",false));
                            AEASMUtils.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                        else if (MethodName.m_185052.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,3));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","createArrow","(Lnet/minecraft/entity/projectile/EntityArrow;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/entity/projectile/EntityArrow;",false));
                            AEASMUtils.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                    }
                });
        transformers.put("net.minecraft.item.ItemBlock",
                (cn)->{
                    for(MethodNode mn:cn.methods){
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
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
        //TODO:TridentChannelingEnchantment
        transformers.put("net.minecraft.entity.Entity",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        if (MethodName.m_70026.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,0));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","checkDampEnchantment","(ZLnet/minecraft/entity/Entity;)Z",false));
                            AEASMUtils.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                    }
                });
        //in 1.12.2, cost is judged by anvil, not enchantment.//modifyMinCost//modifyMaxCost
        transformers.put("net.minecraft.enchantment.EnchantmentArrowInfinite",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        if (MethodName.m_77325.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","hook$EnchantmentArrowInfinite$getMaxLevel","(I)I",false));
                            AEASMUtils.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                    }
                });
        transformers.put("net.minecraft.enchantment.EnchantmentUntouching",
                (cn)->{
                    for(MethodNode mn: cn.methods){
                        if (MethodName.m_77325.is(mn)){
                            InsnList hook=new InsnList();
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","hook$EnchantmentUntouching$getMaxLevel","(I)I",false));
                            AEASMUtils.injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                    }
                });
        //TODO:CHANNELING
        
    }
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass!=null && basicClass.length>0){
            if (transformers.containsKey(transformedName)){
                ClassReader classReader=new ClassReader(basicClass);
                ClassNode cn=new ClassNode();
                classReader.accept(cn, 0);

                transformers.get(transformedName).accept(cn);

                ClassWriter classWriter=new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
                cn.accept(classWriter);
                return AEASMUtils.push(transformedName,classWriter.toByteArray());
            }else return basicClass;
        }
        return basicClass;
    }
}
