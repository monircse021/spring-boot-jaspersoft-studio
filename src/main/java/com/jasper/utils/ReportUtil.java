package com.jasper.utils;

import com.jasper.dto.Offense;
import com.jasper.dto.ReportData;
import com.jasper.dto.TestDemoDto;
import com.jasper.dto.UserDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

public class ReportUtil {
    public static byte[] getByteArrayForOffender(List<ReportData> dataList,
                                                  String reportType, InputStream inputStream,String jasperResourcePath) throws JRException {

        String tempReport = Defs.getReportTemplatePath() + Defs.OFFENDER_REPORT_FILE;
        Defs.REPORT_PATH = tempReport;
        File tempReportDir = new File(tempReport);

        try {
            if (!tempReportDir.exists()) {
                tempReportDir.mkdirs();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JRBeanCollectionDataSource beanColDataSource;

        List<Offense> offenseList = new ArrayList<>();

        for(Offense offense:dataList.get(0).getOffenses()){
            offenseList.add(offense);
        }

        UserDetails userDetails=dataList.get(0).getUserDetails();

        userDetails.setGender(null);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("pOffenderName", userDetails.getOffenderName()==null ? "":userDetails.getOffenderName());
        parameters.put("pAge", userDetails.getAge()==null ? "" :userDetails.getAge());
        parameters.put("pDateOfBirth", userDetails.getDateOfBirth()==null ?"" : userDetails.getDateOfBirth());
        parameters.put("pGender", userDetails.getGender()==null ? "" :userDetails.getGender());
        parameters.put("pRace", userDetails.getRace()==null ? "":userDetails.getRace());
        parameters.put("pHeight", userDetails.getHeight()==null ? "":userDetails.getHeight());
        parameters.put("pHair", userDetails.getHair()==null ? "":userDetails.getHair());
        parameters.put("pSid", userDetails.getSid()==null ? "":userDetails.getSid());
        parameters.put("pWeight", userDetails.getWeight()==null ? "":userDetails.getWeight());
        parameters.put("pEyes", userDetails.getEyes()==null ? "":userDetails.getEyes());
        parameters.put("pCaseload", userDetails.getCaseload()==null ? "":userDetails.getCaseload());
        parameters.put("ps=Status", userDetails.getStatus()==null ? "":userDetails.getStatus());
        parameters.put("pFieldAdmissionDate", userDetails.getFieldAdmissionDate()==null ? "":userDetails.getFieldAdmissionDate());
        parameters.put("pEarliestReleaseDate", userDetails.getEarliestReleaseDate()==null ? "":userDetails.getEarliestReleaseDate());
        parameters.put("pImage", userDetails.getImage()==null ? "":userDetails.getImage());


        beanColDataSource = new JRBeanCollectionDataSource(offenseList, false);

        JasperReport jasperMasterReport = JasperCompileManager.compileReport(inputStream);


        if (dataList != null && dataList.size() > 0) {

            JRAbstractLRUVirtualizer virtualizer = null;
            JRSwapFile swapFile = new JRSwapFile(Defs.REPORT_PATH, 1024, 1024);
            virtualizer = new JRSwapFileVirtualizer(2, swapFile, true);

            parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, new Boolean(false));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);

            JRPdfExporter pdfexporter;
            JRXlsExporter xlsexporter;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                if (reportType.equalsIgnoreCase(Defs.REPORT_TYPE_PDF)) {
                    pdfexporter = new JRPdfExporter();
                    pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    pdfexporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    pdfexporter.exportReport();

                } else {
                    xlsexporter = new JRXlsExporter();
                    xlsexporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 24985);
                    xlsexporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    xlsexporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    xlsexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
                    xlsexporter.exportReport();
                }
            } catch (Exception ex) {
            } finally {
                virtualizer.cleanup();
                xlsexporter = null;
                pdfexporter = null;
            }
            byte[] bArr = null;
            bArr = baos.toByteArray();
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                System.out.println("Flushing Error");
            }
            return bArr;
        } else {
            return null;
        }
    }

    public static void generateReportFromByteArray(byte[] bArr, String reportType, HttpServletResponse response){
        try {
            try {
                String fileName ="offender-detail-report-"+ LocalDate.now().toString();
                if (reportType.equalsIgnoreCase(Defs.REPORT_TYPE_PDF)) {
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
                    response.setContentType("application/pdf");

                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
                    response.setContentType("application/xls");
                }

                response.getOutputStream().write(bArr);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
