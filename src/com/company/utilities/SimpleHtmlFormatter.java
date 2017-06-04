package com.company.utilities;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class SimpleHtmlFormatter extends Formatter {

    public String format(LogRecord rec) {
        StringBuilder buf = new StringBuilder(1000);
        buf.append("<tr>\n");

        if (rec.getLevel().intValue() == Level.SEVERE.intValue()) {
            buf.append("\t<td style=\"color:green\">");
            buf.append("<b>");
            buf.append(rec.getLevel());
            buf.append("</b>");
        } else if (rec.getLevel().intValue() == Level.WARNING.intValue()) {
            buf.append("\t<td style=\"color:red\">");
            buf.append("<b>");
            buf.append(rec.getLevel());
            buf.append("</b>");
        } else if (rec.getLevel().intValue() == Level.INFO.intValue()) {
            buf.append("\t<td style=\"color:black\">");
            buf.append("<b>");
            buf.append(rec.getLevel());
            buf.append("</b>");
        } else {
            buf.append("\t<td>");
            buf.append(rec.getLevel());
        }

        buf.append("</td>\n");
        buf.append("\t<td>");
        buf.append(DateStamp.getStringFromDate(new Date()));
        buf.append("</td>\n");
        buf.append("\t<td>");
        buf.append("<b>").append(rec.getLoggerName()).append(": ").append("</b>").append(formatMessage(rec));
        buf.append("</td>\n");
        buf.append("</tr>\n");

        return buf.toString();
    }

    public String getHead(Handler h) {
        return "<!DOCTYPE html>\n<head>\n<meta charset=\"UTF-8\">\n<style>\n"
                + "table { width: 100% }\n"
                + "th { font:bold 10pt Tahoma; }\n"
                + "td { font:normal 10pt Tahoma; }\n"
                + "h1 {font:normal 11pt Tahoma;}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>" + ("<b>Detailed report:</b> day " + Configuration.dayNumber) + "</h1>\n"
                + "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n"
                + "<tr align=\"left\">\n"
                + "\t<th style=\"width:10%\">Loglevel</th>\n"
                + "\t<th style=\"width:15%\">Time</th>\n"
                + "\t<th style=\"width:75%\">Log Message</th>\n"
                + "</tr>\n";
    }

    public String getTail(Handler h) {
        return "</table>\n</body>\n</html>";
    }
}