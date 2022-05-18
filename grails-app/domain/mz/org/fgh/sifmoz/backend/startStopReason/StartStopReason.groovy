package mz.org.fgh.sifmoz.backend.startStopReason

class StartStopReason {
    public static final String TERMINO_DO_TRATAMENTO = "TERMINO_DO_TRATAMENTO"
    public static final String TRANSFERIDO_DE = "TRANSFERIDO_DE"
    public static final String REINICIO_TRATAMETO = "REINICIO_TRATAMETO"
    public static final String ABANDONO = "ABANDONO"
    public static final String TRANSFERIDO_PARA = "TRANSFERIDO_PARA"
    public static final String INICIO_MATERNIDADE = "INICIO_MATERNIDADE"
    public static final String VOLTOU_REFERENCIA = "VOLTOU_REFERENCIA"
    public static final String REFERIDO_PARA = "REFERIDO_PARA"
    public static final String TRANSITO = "TRANSITO"
    public static final String OBITO = "OBITO"
    public static final String ALTERACAO = "ALTERACAO"
    public static final String MANUNTENCAO = "MANUNTENCAO"
    public static final String INICIO_AO_TRATAMENTO = "INICIO_AO_TRATAMENTO"

    String id
    boolean isStartReason
    String code
    String reason

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        reason unique: true
    }

    boolean isNew() {
        return this.code.equals(INICIO_AO_TRATAMENTO)
    }

    boolean isManutencao() {
        return this.code.equals(MANUNTENCAO)
    }
    boolean isAlteracao() {
        return this.code.equals(ALTERACAO)
    }
    boolean isTransferido() {
        return this.code.equals(TRANSFERIDO_DE)
    }
    boolean isTransito() {
        return this.code.equals(TRANSITO)
    }
    boolean isReferido() {
        return this.code.equals(REFERIDO_PARA)
    }
}
