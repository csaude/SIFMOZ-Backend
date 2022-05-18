package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento


import grails.converters.*
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.patients.ActiveOrFaltosoPatientReport
import mz.org.fgh.sifmoz.report.ReportGenerator

import java.text.SimpleDateFormat

class HistoricoLevantamentoReportController extends MultiThreadRestReportController {
    IHistoricoLevantamentoReportService historicoLevantamentoReportService
    HistoricoLevantamentoReport historicoLevantamentoReport

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    HistoricoLevantamentoReportController() {
        super(HistoricoLevantamentoReport)
    }

    @Override
    protected String getProcessingStatusMsg() {
        return null
    }

    def index() { }

    /**
     * Implementa toda a logica de processamento da informação do relatório
     */
    @Override
    void run() {
        initHistryReport()
        processHistryReport()
    }

    private void initHistryReport() {
        this.historicoLevantamentoReport = new HistoricoLevantamentoReport()
        this.historicoLevantamentoReport.setReportId(getSearchParams().getId())
        this.historicoLevantamentoReport.setClinicId(getSearchParams().getClinicId())
        this.historicoLevantamentoReport.setPeriodType(getSearchParams().getPeriodType())
        this.historicoLevantamentoReport.setPeriod(Integer.valueOf(getSearchParams().getPeriod()))
        this.historicoLevantamentoReport.setYear(getSearchParams().getYear())
        this.historicoLevantamentoReport.setStartDate(getSearchParams().getStartDate())
        this.historicoLevantamentoReport.setEndDate(getSearchParams().getEndDate())
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        render getProcessStatus() as JSON
        doProcessReport()
    }

    void processHistryReport() {
        historicoLevantamentoReportService.processamentoDados(getSearchParams())
    }

    def printReport(ReportSearchParams searchParams) {
        Date stDate = new SimpleDateFormat("dd/MM/yyyy").parse("21/05/2019")
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("21/03/2022")
        String reportId = searchParams.getId()
        Map<String, Object> map = new HashMap<>()
        map.put("path", System.getProperty("user.home"))
        map.put("reportId", reportId)
        map.put("facilityName", Clinic.findById(searchParams.getClinicId()).getClinicName())
        map.put("startDate", stDate)
        map.put("endDate", endDate)
        map.put("province", Province.findById(searchParams.getProvinceId()).getDescription())
        map.put("district", District.findById(searchParams.getDistrictId()).getDescription())
        map.put("year", searchParams.getYear().toString())

        List<HistoricoLevantamentoReport> reportObjects = historicoLevantamentoReportService.getReportDataByReportId(reportId)
        String jrxmlFilePath = System.getProperty("user.home")+ File.separator + "HistoricoDeLevantamento.jrxml"

        byte [] report = ReportGenerator.generateReport(map,reportObjects,jrxmlFilePath)
        render(file: report, contentType: 'application/pdf')
    }
}
