package com.jasper.controller;

import com.jasper.dto.ReportData;
import com.jasper.service.OfferReportService;
import com.jasper.utils.Defs;
import com.jasper.utils.ReportUtil;
import com.jasper.utils.UrlDefs;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class OffenderReportController {
    @Autowired
    OfferReportService offenderReportService;
    @GetMapping("/offender-details")
    public void departmentWiseEmployeeReport(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException, JRException {
        String fileName = "offenderDetails.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=" + fileName);
        ReportData reportData= offenderReportService.getOffenderDetails();
        generateOffenderDetailsReport(request,response,redirectAttributes,reportData);

    }

    public ModelAndView generateOffenderDetailsReport(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,ReportData reportData){
        ModelAndView mv = new ModelAndView(new RedirectView(request.getContextPath() + UrlDefs.OFFENDER_DETAILS));
        byte[] bArr = null;

        try {
            List<ReportData> reportDataList=new ArrayList<>();
            reportDataList.add(reportData);

            if(reportDataList !=null){
                String reportType = Defs.REPORT_TYPE_PDF;
                String jasperResourcePath = request.getServletContext().getRealPath("/WEB-INF/");
                InputStream reportInputStream = getClass().getResourceAsStream("/reports/rptPublicInformationV2.jrxml");
                bArr = ReportUtil.getByteArrayForOffender(reportDataList, reportType,reportInputStream,jasperResourcePath);
                if(bArr !=null)
                    ReportUtil.generateReportFromByteArray(bArr, reportType,response);

                reportInputStream.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("eMsg", Defs.NO_REPORT_DATA_FOUND);
            return mv;
        }
        return null;
    }

}
