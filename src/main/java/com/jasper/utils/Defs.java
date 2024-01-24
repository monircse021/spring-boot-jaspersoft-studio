package com.jasper.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Defs {
//
    //Messages
    public static String NO_REPORT_DATA_FOUND = "No report data found for the given criteria";
    public static final String REPORT_TYPE_PDF = "PDF";
    public static final String REPORT_TYPE_XLS = "XLS";

    public static String REPORT_PATH;
    public static final String OFFENDER_REPORT_FILE = "/offenderReportFiles";



    public static String reportPathWindows = "D:/reports/";


    public static String getReportTemplatePath()
    {
//        if(SystemUtils.IS_OS_UNIX)
//            return reportPathLinux;
//        else
            return reportPathWindows;

    }


    public static BufferedImage getBufferedImage(String filePath) {
        BufferedImage bImageFromConvert = null;
        try {
            FileInputStream fins = new FileInputStream(filePath);
            BufferedImage in = ImageIO.read(fins);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(in, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            ByteArrayInputStream ins = new ByteArrayInputStream(imageInByte);
            bImageFromConvert = ImageIO.read(ins);
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bImageFromConvert;
    }

}