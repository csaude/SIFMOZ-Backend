package mz.org.fgh.sifmoz.backend.stock

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails

@Transactional
@Service(Stock)
abstract class StockService implements IStockService{



    List<Stock> getReceivedStock(String clinicId, Date startDate, Date endDate, String clinicalServiceId){
        List<PatientVisitDetails> list = Stock.executeQuery("select  s from Stock  s " +
                " inner join s.entrance se" +
                " left join s.drug d" +
                " where se.dateReceived BETWEEN  :startDate AND :endDate  AND " +
                " s.clinic.id =:clinicId " +
                " AND d.clinicalService.id =:clinicalServiceId",
                [startDate: startDate, endDate: endDate, clinicId: clinicId, clinicalServiceId:clinicalServiceId]);
        return list

    }


}
