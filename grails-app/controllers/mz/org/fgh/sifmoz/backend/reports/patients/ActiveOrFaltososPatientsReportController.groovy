package mz.org.fgh.sifmoz.backend.reports.patients

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams

import static org.springframework.http.HttpStatus.*

class ActiveOrFaltososPatientsReportController extends MultiThreadRestReportController {

    IActiveOrFaltososPatientsReportService activeOrFaltososPatientsReportService
    ReportSearchParams reportSearchParams

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ActiveOrFaltososPatientsReportController() {
        super(ActiveOrFaltosoPatientReport)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond activeOrFaltososPatientsReportService.list(params), model:[activePatientsReportCount: activeOrFaltososPatientsReportService.count()]
    }

    def show(Long id) {
        respond activeOrFaltososPatientsReportService.get(id)
    }

    @Transactional
    def save(ActiveOrFaltosoPatientReport activePatientsReport) {
        if (activePatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (activePatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond activePatientsReport.errors
            return
        }

        try {
            //activeOrFaltososPatientsReportService.doSave(activePatientsReport)
        } catch (ValidationException e) {
            respond activePatientsReport.errors
            return
        }

        respond activePatientsReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ActiveOrFaltosoPatientReport activePatientsReport) {
        if (activePatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (activePatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond activePatientsReport.errors
            return
        }

        try {
            // activeOrFaltososPatientsReportService.doSave(activePatientsReport)
        } catch (ValidationException e) {
            respond activePatientsReport.errors
            return
        }

        respond activePatientsReport, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || activeOrFaltososPatientsReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        render qtyToProcess: qtyRecordsToProcess
        doProcessReport()
    }

    /**
     * Implementa toda a logica de processamento da informação do relatório
     */
    @Override
    void run() {
        activeOrFaltososPatientsReportService.doSave(activeOrFaltososPatientsReportService.processamentoDados(getSearchParams()))
//        for (patient in activePatientsReportService.processamentoDados()) {
//            save(patient)
//        ReportSearchParams
//        }

    }

    /*
    String calcularIdade (Date dateOfBirth, Date endDate) {
        Calendar calendar = Calendar.getInstance()
        Calendar dateOfBirth1 = new GregorianCalendar()
        dateOfBirth.setTime(dateOfBirth1)
        calendar.setTime(endDate)
        def endDateYear = calendar.get(Calendar.YEAR)
        calendar.setTime(dateOfBirth)
        def dateOfBirthYear = calendar.get(Calendar.YEAR)
        def age = endDateYear - dateOfBirthYear

        dateOfBirth1.add(Calendar.YEAR, age)

        if (endDate.before(dateOfBirth1)) {

            age--

        }
        return  age.toString()
    }
    */

    @Override
    long getRecordsQtyToProcess() {
        return 0
    }

    @Override
    void getProcessedRecordsQty(String reportId) {

    }

    @Override
    void printReport(String reportId, String fileType) {

    }
}
