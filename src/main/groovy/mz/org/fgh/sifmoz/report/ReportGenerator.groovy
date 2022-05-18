package mz.org.fgh.sifmoz.report

import mz.org.fgh.sifmoz.backend.utilities.Utilities
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
import net.sf.jasperreports.export.Exporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration

import java.sql.Connection

class ReportGenerator {

    static byte[] generateReport(Map<String, Object> parameters, String fileType, List<Map<String, Object>> reportObjects, String reportPath ) {
        return generateReport(parameters, reportPath, fileType, null, reportObjects)
    }

    static byte[] generateReport(String reportPath, Map<String, Object> parameters, String fileType, Connection connection) {
        return generateReport(parameters, reportPath, fileType, connection, null)
    }

    static byte[] generateReport(Map<String, Object> parameters, String reportPath, String fileType, Connection connection, List<Map<String, Object>> reportObjects) {
        try {
            def jasperPrint
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath)
<<<<<<< HEAD
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
=======
            if (Utilities.listHasElements(reportObjects as ArrayList<?>)) {
                JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(reportObjects)
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            } else {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection)
            }
            if (fileType.equals("PDF")) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
                JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
                return byteArrayOutputStream.toByteArray()
            } else if (fileType.equals("XLS")) {
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                configuration.setOnePagePerSheet(true);
                configuration.setIgnoreGraphics(false);

                File outputFile = new File("output.xlsx");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
                OutputStream fileOutputStream = new FileOutputStream(outputFile)
                Exporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
                exporter.setConfiguration(configuration);
                exporter.exportReport();
                byteArrayOutputStream.writeTo(fileOutputStream);

                return byteArrayOutputStream.toByteArray()
            } else throw new IllegalArgumentException("Report type not supported ["+ fileType+"]")
>>>>>>> 6f35413ae40509586a1b46e4ddb62e9509f37b77
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }
}