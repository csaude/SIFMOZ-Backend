package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import org.springframework.beans.factory.annotation.Autowired

@Transactional
@Service(MmiaStockSubReportItem)
abstract class MmiaStockSubReportService implements IMmiaStockSubReportService {

    @Autowired
    IReportProcessMonitorService reportProcessMonitorService

    @Override
    List<MmiaStockSubReportItem> generateMmiaStockSubReport(ReportSearchParams searchParams, ReportProcessMonitor processMonitor) {
        ClinicalService service = ClinicalService.findById(searchParams.getClinicalService())
        Clinic clinic = Clinic.findById(searchParams.getClinicId())

        List<MmiaStockSubReportItem> mmiaStockSubReportItems = new ArrayList<>()

        def list = Drug.executeQuery("select    " +
                "           dr.packSize as packSize, " +
                "           dr.fnmCode as fnmCode, " +
                "           dr.name as drugName, " +
                "           dr.id as drugId, " +
                "           (select coalesce(sum(s.unitsReceived),0) as r from Stock s inner join s.entrance se where se.dateReceived >= :startDate and se.dateReceived <= :endDate and s.drug = dr and s.clinic = :clinic) as received," +
                "           (select coalesce(sum(pd.quantitySupplied),0) as s from PackagedDrug pd inner join pd.pack pk where pk.pickupDate >= :startDate and pk.pickupDate <= :endDate and pd.drug = dr and pk.clinic = :clinic) as saidas," +
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
                " where  dr.active = true " +
                "       and exists (select s " +
                "                   from Stock s inner join s.entrance se " +
                "                   where s.drug = dr and s.clinic = :clinic)",
                [startDate: searchParams.getStartDate(), endDate: searchParams.getEndDate(), clinic: clinic])

        double percUnit = 35/list.size()
        for (int i = 0; i < list.size() - 1; i ++) {

            generateAndSaveMmiaStockSubReport(list[i], mmiaStockSubReportItems, searchParams.getId())
            processMonitor.setProgress(processMonitor.getProgress() + percUnit)
            reportProcessMonitorService.save(processMonitor)
        }


        processMonitor.setProgress(100);
        processMonitor.setMsg("Processamento terminado")
        reportProcessMonitorService.save(processMonitor);

        return mmiaStockSubReportItems
    }

    void generateAndSaveMmiaStockSubReport(Object item, List<MmiaStockSubReportItem> mmiaStockSubReportItems, String reportId) {
        MmiaStockSubReportItem stockSubReportItem = new MmiaStockSubReportItem()
        stockSubReportItem.setReportId(reportId)
        stockSubReportItem.setUnit(String.valueOf(item[0]))
        stockSubReportItem.setFnmCode(String.valueOf(item[1]))
        stockSubReportItem.setDrugName(String.valueOf(item[2]))
        stockSubReportItem.setInitialEntrance(Integer.valueOf(String.valueOf(item[4])))
        stockSubReportItem.setOutcomes(Integer.valueOf(String.valueOf(item[5])))
        stockSubReportItem.setLossesAdjustments(Integer.valueOf(String.valueOf(item[6])))
        stockSubReportItem.setInventory(Integer.valueOf(String.valueOf(item[7])))
        stockSubReportItem.setExpireDate(item[8] as Date)
        stockSubReportItem.setBalance(Integer.valueOf(String.valueOf(item[9])))
        save(stockSubReportItem)
        mmiaStockSubReportItems.add(stockSubReportItem)
    }
}
