package com.hexagram2021.advanced_enchantments.core;

import com.hexagram2021.advanced_enchantments.utils.AEASMDebugUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 23:15
 **/
public class AdvancedEnchantmentTransformer implements IClassTransformer {
    public static final HashMap<String, Consumer<ClassNode>> transformers=new HashMap<>();
    static {
        transformers.put("net.minecraft.entity.projectile.EntityArrow",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        if ("func_190547_a".equals(mn.name) || "setEnchantmentEffectsFromEntity".equals(mn.name)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,0));
                            hook.add(new IntInsnNode(Opcodes.ALOAD,1));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","onArrowImpact","(Lnet/minecraft/entity/projectile/EntityArrow;Lnet/minecraft/entity/EntityLivingBase;)V"));
                            injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.RETURN);
                        }
                        //TODO:find out what should I use in 1.12.2?
//                      @Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
//                       private void getEntityOnFireSeconds(Entity instance, int seconds) {
//                          instance.setSecondsOnFire(((AbstractArrow)(Object)this).getRemainingFireTicks() / 320);
//                      }
                    }
                });
        transformers.put("net.minecraft.item.ItemArrow",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        if ("isInfinite".equals(mn.name)){//TODO:find out srg name;
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,4));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","isInfinite","(Lnet/minecraft/entity/player/EntityPlayer;)Z"));
                            injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                        else if ("func_185052_a".equals(mn.name) || "createArrow".equals(mn.name)){
                            InsnList hook=new InsnList();
                            hook.add(new IntInsnNode(Opcodes.ALOAD,3));
                            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","createArrow","(Lnet/minecraft/entity/projectile/EntityArrow;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/entity/projectile/EntityArrow;"));
                            injectBeforeUniqueInsnNode(mn.instructions,hook,Opcodes.ARETURN);
                        }
                    }
                });
        transformers.put("net.minecraft.item.ItemBlock",
                (cn)->{
                    for(MethodNode mn:cn.methods){
                        if ("setTileEntityNBT".equals(mn.name)){//TODO:find out srg name;
                            AbstractInsnNode ren=null;
                            Iterator<AbstractInsnNode> iterator=mn.instructions.iterator();
                            while (iterator.hasNext()){
                                ren=iterator.next();
                                if (ren instanceof MethodInsnNode){
                                    MethodInsnNode methodInsnNode=(MethodInsnNode) ren;
                                    if (methodInsnNode.getOpcode()==Opcodes.INVOKEVIRTUAL && ("net/minecraft/tileentity/TileEntity".equals(methodInsnNode.owner) || "avj".equals(methodInsnNode.owner)) && ("onlyOpsCanSetNbt".equals(methodInsnNode.name)|| "func_183000_F".equals(methodInsnNode.name))){
                                        break;
                                    }
                                }
                            }
                            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"com/hexagram2021/advanced_enchantments/utils/AEHooks","createArrow","(Lnet/minecraft/entity/projectile/EntityArrow;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/entity/projectile/EntityArrow;"));
                        }
                    }
                });

    }
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass!=null){
            if (transformers.containsKey(transformedName)){
                ClassReader classReader=new ClassReader(basicClass);
                ClassNode cn=new ClassNode();
                classReader.accept(cn, 0);

                transformers.get(transformedName).accept(cn);

                ClassWriter classWriter=new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
                cn.accept(classWriter);
                return AEASMDebugUtils.push(transformedName,classWriter.toByteArray());
            }else return basicClass;
        }
        return basicClass;
    }
    public static AbstractInsnNode injectBeforeUniqueInsnNode(InsnList list,InsnList inject,int code){
        AbstractInsnNode ren=null;
        Iterator<AbstractInsnNode> iterator=list.iterator();
        while (iterator.hasNext()){
            ren=iterator.next();
            if (ren.getOpcode()==code){
                break;
            }
        }
        list.insertBefore(ren,inject);
        return ren;
    }
}
