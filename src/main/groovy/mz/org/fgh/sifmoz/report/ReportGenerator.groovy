package mz.org.fgh.sifmoz.report

import mz.org.fgh.sifmoz.backend.reports.patients.ActiveOrFaltosoPatientReport
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource

class ReportGenerator {
/*
    static byte[] generateReport(Map<String, Object> parameters, List<ActiveOrFaltosoPatientReport> reportObjects, String reportPath ) {
        try {

            JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(reportObjects as Collection<Map<String, ?>>)
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
           // render(file: byteArrayOutputStream.toByteArray(), contentType: 'application/pdf')
            //     response.setContentType("application/pdf")  //<-- you'll have to handle this dynamically at some point
            //    response.setHeader("Content-disposition", "attachment;filename=${11}")
            //   response.outputStream <<  byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }*/

    static byte[] generateReport(Map<String, Object> parameters, Collection reportObjects, String reportPath) {
        try {
            JRBeanCollectionDataSource mapCollectionDataSource = new JRBeanCollectionDataSource(reportObjects)
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
            // render(file: byteArrayOutputStream.toByteArray(), contentType: 'application/pdf')
            //     response.setContentType("application/pdf")  //<-- you'll have to handle this dynamically at some point
            //    response.setHeader("Content-disposition", "attachment;filename=${11}")
            //   response.outputStream <<  byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }
}
