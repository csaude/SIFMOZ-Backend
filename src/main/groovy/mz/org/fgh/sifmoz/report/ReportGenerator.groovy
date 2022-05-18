package mz.org.fgh.sifmoz.report

<<<<<<< HEAD
import mz.org.fgh.sifmoz.backend.reports.patients.ActiveOrFaltosoPatientReport
=======
import net.sf.jasperreports.engine.JRExporterParameter
>>>>>>> 92963a1a071960f72c8701a71908032755213ac4
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import net.sf.jasperreports.engine.export.JRXlsExporter
import net.sf.jasperreports.export.ExporterInput
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimpleXlsReportConfiguration

import java.sql.Connection

class ReportGenerator {
/*
    static byte[] generateReport(Map<String, Object> parameters, List<ActiveOrFaltosoPatientReport> reportObjects, String reportPath ) {
        try {
<<<<<<< HEAD

            JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(reportObjects as Collection<Map<String, ?>>)
=======
            JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(reportObjects)
>>>>>>> 92963a1a071960f72c8701a71908032755213ac4
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

    static byte[] generateReport(Map<String, Object> parameters, List objects, String reportPath, String report ) {
        try {
            JRBeanCollectionDataSource mapCollectionDataSource = new JRBeanCollectionDataSource(objects)
           // String reportUri = reportPath+"/"+report
            JasperReport jasperReport = JasperCompileManager.compileReport(report)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }

    static byte[] generateReport(Map<String, Object> parameters, String reportPath, String report, Connection connection) {
        try {
            String reportUri = reportPath+"/"+report
            JasperReport jasperReport = JasperCompileManager.compileReport(reportUri)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)

           /* JRXlsExporter xlsExporter = new JRXlsExporter();
            xlsExporter.setExporterInput(new SimpleExporterInput(xlsPrint));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outXlsName));
            SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
            xlsReportConfiguration.setOnePagePerSheet(false);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setDetectCellType(false);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsExporter.setConfiguration(xlsReportConfiguration);

            xlsExporter.exportReport();*/

            return byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }
}
