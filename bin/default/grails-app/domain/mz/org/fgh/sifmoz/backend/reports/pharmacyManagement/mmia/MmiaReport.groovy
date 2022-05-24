package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

class MmiaReport {

    String id
    String reportId
    String clinicId
    String periodType
    int period
    int year
    Date startDate
    Date endDate

    /**
     * Tipo de doentes em TARV
     */
    int totalPacientesInicio
    int totalPacientesManter
    int totalPacientesAlterar
    int totalPacientesTransito
    int totalPacientesTransferidoDe

    /**
     * Faixa etaria dos pacientes em TARV
     */
    int totalPacientesAdulto
    int totalPacientes04
    int totalPacientes59
    int totalPacientes1014

    /**
     * Profilaxia
     */
    int totalPacientesPPE
    int totalPacientesPREP
    int totalpacientesCE

    int dsM0
    int dsM1
    int dsM2
    int dsM3
    int dsM4
    int dsM5

    int dtM0
    int dtM1
    int dtM2

    int dM


    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        periodType nullable: false , inList: ['MONTH']
    }

    void addTotalPacientesInicio() {
        this.totalPacientesInicio ++
    }

    void addTotalPacientesManter() {
        this.totalPacientesManter ++
    }

    void addTotalPacientesAlterar() {
        this.totalPacientesAlterar ++
    }

    void addTotalPacientesTransito() {
        this.totalPacientesTransito ++
    }

    void addTotalPacientesTransferido() {
        this.totalPacientesTransferidoDe ++
    }

    void addTotalPacientesAdulto() {
        this.totalPacientesAdulto ++
    }

    void addTotalPacientes04() {
        this.totalPacientes04 ++
    }

    void addTotalPacientes59() {
        this.totalPacientes59 ++
    }

    void addTotalPacientes1014() {
        this.totalPacientes1014 ++
    }

    void addTotalPacientesPPE() {
        this.totalPacientesPPE ++
    }

    void addTotalPacientesPREP() {
        this.totalPacientesPREP ++
    }

    void addTotalPacientesCE() {
        this.totalpacientesCE ++
    }

    void addTotalDsM0() {
        this.dsM0 ++
    }

    void addTotalDtM0() {
        this.dtM0 ++
    }

    void addTotalDM() {
        this.dM ++
    }

    void addTotalPrep() {
        this.prep ++
    }
}
