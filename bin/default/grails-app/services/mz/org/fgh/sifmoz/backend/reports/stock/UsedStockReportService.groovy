package mz.org.fgh.sifmoz.backend.reports.stock

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.stock.StockService

@Transactional
@Service(UsedStockReportTemp)
abstract class UsedStockReportService implements IUsedStockReportService {

    StockService stockService

/**
 * get report data for received stock report
 * @param reportSearchParams
 * @return
 */
    @Override
    List<UsedStockReportTemp> getReportDataByReportId(String reportId) {
        return UsedStockReportTemp.findAllByReportId(reportId)
    }

    void processReportUsedStockRecords(ReportSearchParams searchParams) {
        Clinic clinic = Clinic.findById(searchParams.getClinicId())

        def list = Drug.executeQuery("select    dr.packSize as packSize, " +
                "           dr.fnmCode as fnmCode, " +
                "           dr.name as drugName, " +
                "           dr.id as drugId, " +
                "           (select coalesce(sum(s.unitsReceived),0) as r from Stock s inner join s.entrance se " +
                "           where se.dateReceived >= :startDate and se.dateReceived <= :endDate and s.drug = dr and s.clinic = :clinic) as received," +
                "           (select coalesce(sum(pd.quantitySupplied),0) as s from PackagedDrug pd inner join pd.pack pk " +
                "           where pk.pickupDate >= :startDate and pk.pickupDate <= :endDate and pd.drug = dr and pk.clinic = :clinic) as saidas," +
                "           ((select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_POSETIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.inventory i " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = InventoryStockAdjustment and (i.endDate >= :startDate and i.endDate <= :endDate) and i.clinic = :clinic " +
                "           ) + " +
                "           (select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_POSETIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.reference rf " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = StockReferenceAdjustment and (rf.date >= :startDate and rf.date <= :endDate) and rf.clinic = :clinic" +
                "           ) + " +
                "           (select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_POSETIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.destruction rf " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = StockDestructionAdjustment and (rf.date >= :startDate and rf.date <= :endDate) and rf.clinic = :clinic" +
                "           )) as ajustes," +
                "           ((select coalesce(sum(s.stockMoviment),0) as r from Stock s inner join s.entrance se where s.expireDate < :endDate and se.dateReceived <= :endDate and s.drug = dr and s.clinic = :clinic) -" +
                "           (select coalesce(sum(pd.quantitySupplied),0) as s from PackagedDrug pd inner join pd.pack pk where pk.pickupDate >= :startDate and pk.pickupDate <= :endDate and pd.drug = dr and pk.clinic = :clinic) +" +
                "           ((select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_NEGATIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.inventory i " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = InventoryStockAdjustment and (i.endDate >= :endDate) and i.clinic = :clinic" +
                "           ) + " +
                "           (select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_NEGATIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.reference rf " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = StockReferenceAdjustment and (rf.date >= :endDate) and rf.clinic = :clinic" +
                "           ) + " +
                "           (select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_NEGATIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.destruction rf " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = StockDestructionAdjustment and (rf.date > :endDate) and rf.clinic = :clinic" +
                "           ))) as inventario," +
                "         (select coalesce(SUM(sa.adjustedValue),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr  inner join sa.destruction rf " +
                "           where sa.class = StockDestructionAdjustment and (rf.date > :endDate) and rf.clinic = :clinic" +
                "           ) as stockDestroyed, " +
                "           (select max(s.expireDate) from Stock s where s.drug = dr and s.clinic = :clinic) as validade," +
                "           ((select coalesce(sum(s.stockMoviment),0) as r from Stock s inner join s.entrance se where s.expireDate < :endDate and se.dateReceived <= :endDate and s.drug = dr and s.clinic = :clinic) +" +
                "           (select coalesce(sum(pd.quantitySupplied),0) as s from PackagedDrug pd inner join pd.pack pk where pk.pickupDate >= :startDate and pk.pickupDate <= :endDate and pd.drug = dr and pk.clinic = :clinic) +" +
                "           ((select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_NEGATIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.inventory i " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = InventoryStockAdjustment and (i.endDate >= :startDate and i.endDate <= :endDate) and i.clinic = :clinic" +
                "           ) + " +
                "           (select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_NEGATIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.reference rf " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = StockReferenceAdjustment and (rf.date >= :startDate and rf.date <= :endDate) and rf.clinic = :clinic" +
                "           ) + " +
                "           (select coalesce(SUM(CASE sot.code WHEN 'AJUSTE_NEGATIVO' THEN sa.adjustedValue ELSE (-1*sa.adjustedValue) END),0) as adjustedValue" +
                "           from StockAdjustment sa inner join sa.adjustedStock st with st.drug = dr" +
                "                                   inner join sa.destruction rf " +
                "                                   inner join sa.operation sot  " +
                "           where sa.class = StockDestructionAdjustment and (rf.date >= :startDate and rf.date <= :endDate) and rf.clinic = :clinic" +
                "           ))) as saldo" +
                " from Drug dr " +
                " where dr.active = true And" +
                //"       dr.clinicalService.id=: clinicalService and" +
                "        exists (select s " +
                "                   from Stock s inner join s.entrance se " +
                "                   where s.drug = dr and s.clinic = :clinic)",
                [startDate: searchParams.getStartDate(), endDate: searchParams.getEndDate(), clinic: clinic])
      //  clinicalService: searchParams.getClinicalService()

        for (int i = 0; i < list.size() - 1; i++) {
            generateAndSaveUsedStockSubReport(list[i], searchParams)
        }

    }

    void generateAndSaveUsedStockSubReport(Object item, ReportSearchParams searchParams) {
        UsedStockReportTemp reportTemp = new UsedStockReportTemp()
        reportTemp.setReportId(searchParams.getId())
        reportTemp.setYear(searchParams.getYear())
        reportTemp.setPharmacyId(searchParams.getClinicId())
        reportTemp.setProvinceId(searchParams.getProvinceId())
        reportTemp.setDistrictId(searchParams.getDistrictId())
        reportTemp.setStartDate(searchParams.getStartDate())
        reportTemp.setEndDate(searchParams.getEndDate())
        reportTemp.setPeriodType(searchParams.getPeriodType())
        reportTemp.setPeriod(searchParams.getPeriod())
        reportTemp.setReportId(searchParams.getId())

        reportTemp.setFnName(String.valueOf(item[1]))
        reportTemp.setDrugName(String.valueOf(item[2]))
        reportTemp.setDrugId(item[3])
        reportTemp.setReceivedStock(Integer.valueOf(String.valueOf(item[4]))) //initial entrance
        reportTemp.setStockIssued(Integer.valueOf(String.valueOf(item[5]))) //outcomes
        reportTemp.setAdjustment(Integer.valueOf(String.valueOf(item[6])))
        reportTemp.setActualStock(Integer.valueOf(String.valueOf(item[7]))) //inventario
        reportTemp.setDestroyedStock(Long.valueOf(item[8])) //ver
        reportTemp.setBalance(Integer.valueOf(String.valueOf(item[10])))
        save(reportTemp)

    }


}
