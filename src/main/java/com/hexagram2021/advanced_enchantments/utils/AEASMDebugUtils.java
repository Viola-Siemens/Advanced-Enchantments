package com.hexagram2021.advanced_enchantments.utils;

import java.io.File;
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
    public static byte[] push(String name,byte[] clazz){
        if (saveTransformedClass){
            File file=new File(gameDir,"class/"+name+".class");
            try {
                OutputStream stream= Files.newOutputStream(file.toPath());
                stream.write(clazz);
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            file.mkdirs();
        }
        return clazz;
    }
}
