/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.logForDebug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Fenil Jariwala
 */
public class CommonMember {

    private static String filepath = "M:\\log\\kaambanega_";

    public static void appendLogFile(String message) {
        FileWriter fw = null;
        try {
            Calendar c = Calendar.getInstance();
            File f = new File(getFileName(c));
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f, true);
            fw.append(messageTime(c));
            fw.append(message + "\n");
        } catch (IOException ex) {
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                }
            }
        }

    }

    public static void appendLogFileException(String message, Exception e, boolean bigLog) {
        FileWriter fw = null;
        try {
            Calendar c = Calendar.getInstance();
            File f = new File(getFileName(c));
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f, true);
            fw.append(messageTime(c));
            fw.append(message + "\n");
            if (bigLog) {
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement ste : e.getStackTrace()) {

                    sb.append("File Name    ::").append(ste.getFileName()).append("\n");
                    sb.append("Class Name   ::").append(ste.getClass()).append("\n");
                    sb.append("Method Name  ::").append(ste.getMethodName()).append("\n");
                    sb.append("Line Number  ::").append(ste.getLineNumber()).append("\n");
                    fw.append(sb.toString());

                }
            } else {
                fw.append("  ").append(e.getMessage());
            }
        } catch (IOException ex) {
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private static String getFileName(Calendar c) {

        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        return filepath + dateFormat.format(c.getTime())+".txt";

    }

    private static String messageTime(Calendar c) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("********************************************\n");
              
        sb.append("Message Time ::: ").append(dateFormat.format(c.getTime())).append(" \n");
        sb.append("********************************************\n");
        return sb.toString();
    }
}
