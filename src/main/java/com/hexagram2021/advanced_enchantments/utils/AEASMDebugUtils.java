package com.hexagram2021.advanced_enchantments.utils;

import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/8 22:50
 **/
public class AEASMDebugUtils{
    public static File gameDir;
    public static final boolean saveTransformedClass=true;//publish turn false.
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
}
