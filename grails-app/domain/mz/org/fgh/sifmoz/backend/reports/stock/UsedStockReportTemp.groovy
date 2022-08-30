package mz.org.fgh.sifmoz.backend.reports.stock

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.protection.Menu

class UsedStockReportTemp extends BaseEntity{
    String id //ReportId
    String reportId
    String pharmacyId
    String provinceId
    String districtId
    String period
    String periodType
    String month
    String quarter
    String semester
    int year
    Date startDate
    Date endDate

    //Used Stock Report
    String fnName
    String drugId
    String drugName
    Long balance
    Long receivedStock
    Long stockIssued
    Long destroyedStock
    Long adjustment
    Long actualStock

    static constraints = {
        id generator: "uuid"
        pharmacyId nullable: true
        provinceId nullable: true
        districtId nullable: true
        period nullable: true
        month nullable: true
        quarter nullable: true
        semester nullable: true
        startDate nullable: true
        endDate nullable: true
        year nullable: true
        periodType nullable: true
        drugId nullable: true
        fnName nullable: true
    }

    @Override
    public String toString() {
        return "UsedStockReportTemp{" +
                "id='" + id + '\'' +
                "drugName='" + drugName + '\'' +
                ", reportId='" + reportId + '\'' +
                ", pharmacyId='" + pharmacyId + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", period='" + period + '\'' +
                ", periodType='" + periodType + '\'' +
                ", month='" + month + '\'' +
                ", quarter='" + quarter + '\'' +
                ", semester='" + semester + '\'' +
                ", year=" + year +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", balance=" + balance +
                ", receivedStock=" + receivedStock +
                ", stockIssued=" + stockIssued +
                ", destroyedStock=" + destroyedStock +
                ", adjustment=" + adjustment +
                ", actualStock=" + actualStock +
                '}';
    }
    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(reportsMenuCode))
        }
        return menus
    }
}
