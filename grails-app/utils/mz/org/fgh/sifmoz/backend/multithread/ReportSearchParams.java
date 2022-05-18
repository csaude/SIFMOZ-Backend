package mz.org.fgh.sifmoz.backend.multithread;

import grails.validation.Validateable;
import groovy.lang.Closure;
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Validated
public class ReportSearchParams implements Validateable {
    public static final String PERIOD_TYPE_SPECIFIC = "SPECIFIC";
    public static final String PERIOD_TYPE_MONTH = "MONTH";
    public static final String PERIOD_TYPE_QUARTER = "QUARTER";
    public static final String PERIOD_TYPE_SEMESTER = "SEMESTER";
    public static final String PERIOD_TYPE_ANNUAL = "ANNUAL";

    private String id;
    private String clinicId;
    private String provinceId;
    private String districtId;
    private String periodType;
    private String period;
    private String clinicalService;
    private int year;
    private Date startDate;
    private Date endDate;
    private String startDateParam;
    private String endDateParam;
    private String reportType;

    public void determineStartEndDate() {
//        switch (getPeriodType()) {
        switch ("MONTH") {
            case PERIOD_TYPE_SPECIFIC:
               setStartDate(ConvertDateUtils.createDate(getStartDateParam(),"dd-MM-yyyy"));
                setEndDate(ConvertDateUtils.createDate(getEndDateParam(),"dd-MM-yyyy"));
                break;
            case PERIOD_TYPE_MONTH:
//                int month = Integer.parseInt(getPeriod());
                int month = Integer.parseInt("1");
                Date startDateTemp = DateUtils.addMonths(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, month, getYear()), -1);
                int yearStartDate = DateUtils.toCalendar(startDateTemp).get(Calendar.YEAR);
                int monthStartDate = DateUtils.toCalendar(startDateTemp).get(Calendar.MONTH);
                setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, monthStartDate, yearStartDate));
                setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, month, getYear()));
                break;
            case PERIOD_TYPE_QUARTER:
                switch (getPeriod()) {
                    case "1":
                        setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 12, getYear() - 1));
                        setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 4, getYear()));
                        break;
                    case "2":
                        setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 4, getYear() - 1));
                        setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 8, getYear()));
                        break;
                    case "3":
                        setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 8, getYear() - 1));
                        setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 12, getYear()));
                        break;
                }
                break;
            case PERIOD_TYPE_SEMESTER:
                switch (getPeriod()) {
                    case "1":
                        setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 12, getYear() - 1));
                        setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 6, getYear()));
                        break;
                    case "2":
                        setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 6, getYear() - 1));
                        setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 12, getYear()));
                        break;
                }
                break;
            case PERIOD_TYPE_ANNUAL:
                setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 12, getYear() - 1));
                setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 12, getYear()));
                break;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClinicalService() {
        return clinicalService;
    }

    public void setClinicalService(String clinicalService) {
        this.clinicalService = clinicalService;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getClinicId() {
        return clinicId;
    }

    public String getStartDateParam() {
        return startDateParam;
    }

    public void setStartDateParam(String startDateParam) {
        this.startDateParam = startDateParam;
    }

    public String getEndDateParam() {
        return endDateParam;
    }

    public void setEndDateParam(String endDateParam) {
        this.endDateParam = endDateParam;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @Override
    public Errors getErrors() {
        return null;
    }

    @Override
    public void setErrors(Errors errors) {

    }

    @Override
    public Boolean hasErrors() {
        return null;
    }

    @Override
    public void clearErrors() {

    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public boolean validate(Closure<?>... adHocConstraintsClosures) {
        return false;
    }

    @Override
    public boolean validate(Map<String, Object> params) {
        return false;
    }

    @Override
    public boolean validate(Map<String, Object> params, Closure<?>... adHocConstraintsClosures) {
        return false;
    }

    @Override
    public boolean validate(List fieldsToValidate) {
        return false;
    }

    @Override
    public boolean validate(List fieldsToValidate, Closure<?>... adHocConstraintsClosures) {
        return false;
    }

    @Override
    public boolean validate(List fieldsToValidate, Map<String, Object> params) {
        return false;
    }

    @Override
    public boolean validate(List fieldsToValidate, Map<String, Object> params, Closure<?>... adHocConstraintsClosures) {
        return false;
    }
}