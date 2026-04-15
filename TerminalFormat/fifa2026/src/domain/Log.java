package domain;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.util.logging.Handler;

/**
 * 
 */
public class Log{
    public static String name="Fifa";
    
    public static void record(Exception e){
        record("Unexpected application error", e);
    }
    
    public static void record(String context, Throwable t){
        try{
            Logger logger=Logger.getLogger(name);
            logger.setUseParentHandlers(false);
            ensureFileHandler(logger);
            logger.log(Level.SEVERE, context, t);
        }catch (Exception oe){
            oe.printStackTrace();
        }
    }
    
    private static void ensureFileHandler(Logger logger) throws Exception{
        for (Handler handler : logger.getHandlers()){
            if (handler instanceof FileHandler){
                return;
            }
        }
        FileHandler file=new FileHandler(name+".log",true);
        file.setFormatter(new SimpleFormatter());
        logger.addHandler(file);
    }
}

    
