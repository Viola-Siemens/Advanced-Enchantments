package com.hexagram2021.advanced_enchantments.utils;

import com.hexagram2021.advanced_enchantments.AdvancedEnchantments;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/7 23:12
 **/
public class AELogger {
    public static boolean isDeBug= FMLLog.log.isDebugEnabled();
    public static final Logger LOGGER= LogManager.getLogger(AdvancedEnchantments.MODID);
    public static void log(Level logLevel, Object object) {
        LOGGER.log(logLevel, String.valueOf(object));
    }

    public static void error(Object object) {
        log(Level.ERROR, object);
    }

    public static void info(Object object) {
        log(Level.INFO, object);
    }

    public static void warn(Object object) {
        log(Level.WARN, object);
    }

    public static void error(String message, Object... params) {
        LOGGER.log(Level.ERROR, message, params);
    }

    public static void info(String message, Object... params) {
        LOGGER.log(Level.INFO, message, params);
    }

    public static void warn(String message, Object... params) {
        LOGGER.log(Level.WARN, message, params);
    }

    public static void debug(Object object) {
        if(isDeBug) {
            info("[DEBUG:] " + object);
        }
    }

    public static void debug(String format, Object... params) {
        if(isDeBug) {
            info("[DEBUG:] " + format, params);
        }
    }
}
