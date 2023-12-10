package com.hexagram2021.advanced_enchantments.utils;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/8 22:50
 **/
public class AEASMUtil {
    public static File gameDir;
    public static final boolean saveTransformedClass=true;
    public static byte[] push(String rawName,byte[] clazz){
        if (saveTransformedClass){
            final File outRoot=new File(gameDir,"clazzs/");
            final File outFile = new File(outRoot, rawName.replace('.', File.separatorChar) + ".class");
            final File outDir = outFile.getParentFile();

            if (!outRoot.exists()) {
                outRoot.mkdirs();
            }
            if (!outDir.exists()) {
                outDir.mkdirs();
            }

            if (outFile.exists()) {
                outFile.delete();
            }

            try {
                final OutputStream output = new FileOutputStream(outFile);
                output.write(clazz);
                output.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return clazz;
    }
    public static AbstractInsnNode injectBeforeUniqueInsnNode(InsnList method, InsnList hook, int code){
        AbstractInsnNode ren=null;
        Iterator<AbstractInsnNode> iterator=method.iterator();
        while (iterator.hasNext()){
            ren=iterator.next();
            if (ren.getOpcode()==code){
                break;
            }
        }
        method.insertBefore(ren,hook);
        return ren;
    }
    public static void injectBeforeAllInsnNode(InsnList method, InsnList hook, int code){
        LinkedList<AbstractInsnNode> nodes=new LinkedList<>();
        ListIterator<AbstractInsnNode> iterator=method.iterator();
        AbstractInsnNode node;
        while (iterator.hasNext()){
            node=iterator.next();
            if (node.getOpcode()==code){
                nodes.add(node);
            }
        }
        for(AbstractInsnNode node1:nodes){
            method.insertBefore(node1,hook);
        }
    }
}
