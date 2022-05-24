package mz.org.fgh.sifmoz.backend.reports.stock

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stock.StockService

@Transactional
@Service(StockReportTemp)
abstract class StockReportService implements IStockReportService{


    StockService stockService

/**
 * get report data for received stock report
 * @param reportSearchParams
 * @return
 */


    @Override
    void processReportRecords(ReportSearchParams  searchParams) {
        List<Stock>  stocks= stockService.getReceivedStock(searchParams.getClinicId(),
                searchParams.getStartDate(),
                searchParams.getEndDate(), searchParams.getClinicalService())
            for (Stock stock :stocks) {
                StockReportTemp reportTemp = new StockReportTemp()
                reportTemp.setYear(searchParams.getYear())
                reportTemp.setPharmacyId(searchParams.getClinicId())
                reportTemp.setProvinceId(searchParams.getProvinceId())
                reportTemp.setDistrictId(searchParams.getDistrictId())
                reportTemp.setStartDate(searchParams.getStartDate())
                reportTemp.setEndDate(searchParams.getEndDate())
                reportTemp.setPeriodType(searchParams.getPeriodType())
                reportTemp.setPeriod(searchParams.getPeriod())
                reportTemp.setReportId(searchParams.getId())
                reportTemp.setDrugName(stock.getDrug().getName())
                reportTemp.setDateReceived(stock.getEntrance().getDateReceived())
                reportTemp.setExpiryDate(stock.getExpireDate())
                reportTemp.setUnitsReceived(stock.getUnitsReceived())
                reportTemp.setOrderNumber(stock.getEntrance().getOrderNumber())
                reportTemp.setManufacture(stock.getManufacture())
                reportTemp.setBatchNumber(stock.getBatchNumber())
                save(reportTemp)
            }

    }

    @Override
    List<StockReportTemp> getReportDataByReportId(String reportId) {
        return StockReportTemp.findAllByReportId(reportId)
    }



}
