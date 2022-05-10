package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.converters.JSON
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.drug.IDrugService
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.IPackService
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.stock.IStockService
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class MmiaReportController extends MultiThreadRestReportController{

    IMmiaReportService mmiaReportService
    List<Pack> packList
    MmiaReport curMmiaReport
    IPackService packService
    IDrugService drugService
    IStockService stockService
    IMmiaStockSubReportService mmiaStockSubReportService
    private int qtyProcessed
    List<MmiaRegimenSubReport> regimenSubReportList
    public static final String PROCESS_STATUS_PROCESSING_PACKS = "A processar dispensas"
    public static final String PROCESS_STATUS_PROCESSING_STOCK = "A processar stock"

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MmiaReportController() {
        super(MmiaReport)
    }

    @Override
    protected int countProcessedRecs() {
        return qtyProcessed
    }

    @Override
    int countRecordsToProcess() {
        if (this.processStage == PROCESS_STATUS_PROCESSING_STOCK) {
            return 0
        } else if (this.processStage == PROCESS_STATUS_PROCESSING_PACKS) {
            return packService.countPacksByServiceOnPeriod(ClinicalService.findByCode(getSearchParams().getServiceCode()), Clinic.findById(getSearchParams().getClinicId()), getSearchParams().getStartDate(), getSearchParams().getEndDate())
        }
        return 0
    }

    @Override
    protected String getProcessingStatusMsg() {
        if (!Utilities.stringHasValue(processStage)) processStage = PROCESS_STATUS_INITIATING
        return processStage
    }

    @Override
    void printReport(String reportId, String fileType) {

    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond mmiaReportService.list(params), model:[mmiaReportCount: mmiaReportService.count()]
    }

    def show(String id) {
        respond mmiaReportService.get(id)
    }

    @Transactional
    def save(MmiaReport mmiaReport) {
        if (mmiaReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaReport.errors
            return
        }

        try {
            mmiaReportService.save(mmiaReport)
        } catch (ValidationException e) {
            respond mmiaReport.errors
            return
        }

        respond mmiaReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MmiaReport mmiaReport) {
        if (mmiaReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaReport.errors
            return
        }

        try {
            mmiaReportService.save(mmiaReport)
        } catch (ValidationException e) {
            respond mmiaReport.errors
            return
        }

        respond mmiaReport, [status: OK, view:"show"]
    }

    def getProcessingStatus() {
        render getProcessStatus()
    }

    @Transactional
    def delete(String id) {
        if (id == null || mmiaReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        render getProcessStatus() as JSON
        doProcessReport()
    }

    @Override
    void run() {
        initMmiaReportRecord()
        processStage = PROCESS_STATUS_PROCESSING_PACKS
        updateProcessingStatus()
        searchAndProcessPacks()
        processStage = PROCESS_STATUS_PROCESSING_STOCK
        updateProcessingStatus()
        processMmiaStock()
    }

    void processMmiaStock() {
        mmiaStockSubReportService.generateMmiaStockSubReport(getSearchParams())
    }

    void searchAndProcessPacks() {
        mmiaReportService.processReport(getSearchParams(), this.curMmiaReport)
    }

    private void initMmiaReportRecord() {
        this.curMmiaReport = new MmiaReport()
        this.curMmiaReport.setReportId(getSearchParams().getReportId())
        this.curMmiaReport.setClinicId(getSearchParams().getClinicId())
        this.curMmiaReport.setPeriodType(getSearchParams().getPeriodType())
        this.curMmiaReport.setPeriod(Integer.valueOf(getSearchParams().getPeriod()))
        this.curMmiaReport.setYear(getSearchParams().getYear())
    }

    void updateProcessedRecs(int counter) {
        qtyProcessed = counter + 1
    }
}
