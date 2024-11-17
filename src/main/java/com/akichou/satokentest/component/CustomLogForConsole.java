package com.akichou.satokentest.component;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.log.SaLog;
import cn.dev33.satoken.util.StrFormatter;
import org.springframework.stereotype.Component;

@Component
public class CustomLogForConsole implements SaLog {

    // Log Levels
    public static final int trace = 1 ;
    public static final int debug = 2 ;
    public static final int info = 3 ;
    public static final int warn = 4 ;
    public static final int error = 5 ;
    public static final int fatal = 6 ;

    // Log Prefix
    public static String TRACE_PREFIX = "AkiChou [TRACE LOG] ----- 🔵 " ;
    public static String DEBUG_PREFIX = "AkiChou [DEBUG LOG] ----- 🔵 " ;
    public static String INFO_PREFIX  = "AkiChou [INFO LOG] ----- 🔵 " ;
    public static String WARN_PREFIX  = "AkiChou [WARN LOG] ----- 🔴 " ;
    public static String ERROR_PREFIX = "AkiChou [ERROR LOG] ----- 🔴 " ;
    public static String FATAL_PREFIX = "AkiChou [FATAL LOG] ----- 🔴 " ;

    // Log Color
    public static String TRACE_COLOR = "\033[39m" ;
    public static String DEBUG_COLOR = "\033[34m" ;
    public static String INFO_COLOR  = "\033[32m" ;
    public static String WARN_COLOR  = "\033[33m" ;
    public static String ERROR_COLOR = "\033[31m" ;
    public static String FATAL_COLOR = "\033[35m" ;

    public static String DEFAULT_COLOR = "\033[39m" ;

    @Override
    public void trace(String str, Object... args) {

        println(trace, TRACE_COLOR, TRACE_PREFIX, str, args) ;
    }

    @Override
    public void debug(String str, Object... args) {

        println(debug, DEBUG_COLOR, DEBUG_PREFIX, str, args) ;
    }

    @Override
    public void info(String str, Object... args) {

        println(info, INFO_COLOR, INFO_PREFIX, str, args) ;
    }

    @Override
    public void warn(String str, Object... args) {

        println(warn, WARN_COLOR, WARN_PREFIX, str, args) ;
    }

    @Override
    public void error(String str, Object... args) {

        println(error, ERROR_COLOR, ERROR_PREFIX, str, args) ;
    }

    @Override
    public void fatal(String str, Object... args) {

        println(fatal, FATAL_COLOR, FATAL_PREFIX, str, args) ;
    }

    /**
     * Print log to console
     * @param logLevel 紀錄級數
     * @param color 紀錄顯示顏色
     * @param prefix 紀錄前墜
     * @param str 訊息字串
     * @param args 參數
     */
    public void println(int logLevel, String color, String prefix, String str, Object... args) {

        SaTokenConfig config = SaManager.getConfig() ;

        if(config.getIsLog() && logLevel >= config.getLogLevelInt()) {

            if(config.getIsColorLog() == Boolean.TRUE) {

                System.out.println(color + prefix + StrFormatter.format(str, args) + DEFAULT_COLOR) ;

            } else {

                System.out.println(prefix + StrFormatter.format(str, args)) ;
            }
        }
    }

    /* Colors References :

                DEFAULT  	39
      			BLACK  		30
      			RED  		31
      			GREEN  		32
      			YELLOW  	33
      			BLUE  		34
      			MAGENTA  	35
      			CYAN  		36
      			WHITE  		37
      			BRIGHT_BLACK  	90
      			BRIGHT_RED  	91
      			BRIGHT_GREEN  	92
      			BRIGHT_YELLOW  	93
      			BRIGHT_BLUE  	94
      			BRIGHT_MAGENTA	95
      			BRIGHT_CYAN  	96
      			BRIGHT_WHITE  	97
     */
}
