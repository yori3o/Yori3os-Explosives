package com.yori3o.yo_bombs.common.util;


import com.yori3o.yo_bombs.impl.PlatformUtil;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



public class LoggerUtil {

    
    private static final Logger LOGGER = LogManager.getLogger("yo_bombs");


    // ==================
    // These methods are needed to add [yo_bombs] to logs.
    // ==================

    public static final void info(String message) {
        if (PlatformUtil.isFabric()) {
            LOGGER.info("[yo_bombs]: " + message);
        } else {
            LOGGER.info(message);
        }
    }

    public static final void warn(String message) {
        if (PlatformUtil.isFabric()) {
            LOGGER.warn("[yo_bombs]: " + message);
        } else {
            LOGGER.warn(message);
        }
    }

    public static final void error(String message) {
        if (PlatformUtil.isFabric()) {
            LOGGER.error("[yo_bombs]: " + message);
        } else {
            LOGGER.error(message);
        }
    }

    public static final void errorWithException(String message, Exception e) {
        if (PlatformUtil.isFabric()) {
            LOGGER.error("[yo_bombs]: " + message, e);
        } else {
            LOGGER.error(message, e);
        }
    }

}