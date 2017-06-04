package com.company.utilities;

import java.io.IOException;
import java.util.logging.*;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;

public class BakeryLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    static private FileHandler fileHTML;
    static private Formatter formatterHTML;
    static private Logger logger;

    static public void setup() throws IOException {

        logger = Logger.getLogger("com.company");

        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Configuration.LOG_LEVEl);
        fileTxt = new FileHandler("log.txt");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

    }

    static private void removeOldHtmlHandler(){
        Handler[] handlers = logger.getHandlers();
        if(handlers.length > 1) {
            if (handlers[1] instanceof FileHandler) {
                handlers[1].close();
                logger.removeHandler(handlers[1]);
            }
        }
    }

    static public void setupHtmlLogger(String pattern) throws IOException {

        removeOldHtmlHandler();
        fileHTML = new FileHandler(pattern);
        formatterHTML = new SimpleHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }
}