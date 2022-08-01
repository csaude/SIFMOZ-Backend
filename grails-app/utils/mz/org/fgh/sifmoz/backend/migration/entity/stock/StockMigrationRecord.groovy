package mz.org.fgh.sifmoz.backend.migration.entity.stock

import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance

import java.text.DateFormat
import java.text.SimpleDateFormat

class StockMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private String batchnumber;

    private Date datereceived;

    private Date expirydate;

    private char modified;

    private String shelfnumber;

    private String numeroguia;

    private int unitsreceived;

    private String manufacturer;

    private char hasunitsremaining;

    private BigDecimal unitprice;

    private String stockcentername

    String name

    private StockTakeMigrationRecord stockTakeMigrationRecord;

    private StockAdjustmentMigrationRecord stockAdjustmentMigrationRecord;

    //----------------------------------------------------

    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        Stock.withTransaction {
            def clinic = Clinic.findWhere(main_clinic: true)
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy")
            String dateFormated = dateFormat.format(this.datereceived)

            // Setting StockEntrance
            getMigratedRecordEntrance().setClinic(clinic)
            getMigratedRecordEntrance().setDateReceived(this.datereceived)
            getMigratedRecordEntrance().setOrderNumber(dateFormated + "_NA")

            // Setting Stock
            getMigratedRecordStock().setClinic(clinic)
            getMigratedRecordStock().setBatchNumber(this.batchnumber)
            getMigratedRecordStock().setModified(this.modified == (char) 'T')
            getMigratedRecordStock().setUnitsReceived(this.unitsreceived)
            getMigratedRecordStock().setExpireDate(this.expirydate)
            getMigratedRecordStock().setShelfNumber(this.shelfnumber)
            getMigratedRecordStock().setManufacture(this.manufacturer)

            // Auxiliar
            def stockEntranceAux = StockEntrance.findWhere(this.datereceived)
            def stockCenterAux = StockCenter.findWhere(name: this.stockcentername)
            def drugAux = Drug.findWhere(name: this.name)


            if (stockEntranceAux != null) {// Ja existe o stockEntrance // migrar apenas o Stock

                getMigratedRecordStock().setEntrance(stockEntranceAux) // Setting numero de guia => (id do stockEntrance)
                getMigratedRecordStock().setCenter(stockCenterAux)
                getMigratedRecordStock().setHasUnitsRemaining(this.hasunitsremaining == (char) 'T')
                getMigratedRecordStock().setDrug(drugAux)

                //Saving Stock
                if (getMigratedRecordStock().hasErrors()) {
                    System.println("ERROR: "+getMigratedRecordStock().errors)
                    MigrationLog migrationLog = new MigrationLog()
                    migrationLog.errors = getMigratedRecordStock().errors
                    logs.add(migrationLog)
                } else {
                    System.println("NO ERROR...")
                    getMigratedRecordStock().save(flush: true)
                }
            } else {// O stockEntrance ainda nao existe
                // migrar entrance e o Stock

                def stockEntranceAux1 = null
                //Saving StockEntrance
                if (getMigratedRecordEntrance().hasErrors()) {
                    System.println("ERROR: "+getMigratedRecordEntrance().errors)
                    MigrationLog migrationLog = new MigrationLog()
                    migrationLog.errors = getMigratedRecordEntrance().errors
                    logs.add(migrationLog)
                } else {
                    System.println("NO ERROR...")
                    stockEntranceAux1 = getMigratedRecordEntrance().save(flush: true)
                }

                if (stockEntranceAux1 != null) {
                    getMigratedRecordStock().setEntrance(stockEntranceAux1)
                    getMigratedRecordStock().setCenter(stockCenterAux)
                    getMigratedRecordStock().setHasUnitsRemaining(this.hasunitsremaining == (char) 'T')
                    getMigratedRecordStock().setDrug(drugAux)

                    //Saving Stock
                    if (getMigratedRecordStock().hasErrors()) {
                        System.println("ERROR: " + getMigratedRecordStock().errors)
                        MigrationLog migrationLog = new MigrationLog()
                        migrationLog.errors = getMigratedRecordStock().errors
                        logs.add(migrationLog)
                    } else {
                        System.println("NO ERROR...")
                        getMigratedRecordStock().save(flush: true)
                    }
                }
            }
        }

        return null
    }

    @Override
    void updateIDMEDInfo() {

    }

    @Override
    int getId() {
        return this.id
    }

    @Override
    String getEntityName() {
        return "stock"
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return new Stock()
    }

    Stock getMigratedRecordStock() {
        return (Stock) super.getMigratedRecord()
    }

    StockEntrance getMigratedRecordEntrance() {
        return (StockEntrance) super.getMigratedRecord()
    }
}
