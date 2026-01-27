
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "fetchForexRate"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FetchForexRateInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String derivativeType;
    private String taxChargeType;
    private type.Transaction transaction;
    private type.TransactionTaxMetaData transactionTaxMetaData;

    public String getDerivativeType() {
        return this.derivativeType;
    }

    public void setDerivativeType(String derivativeType) {
        this.derivativeType = derivativeType;
    }

    public String getTaxChargeType() {
        return this.taxChargeType;
    }

    public void setTaxChargeType(String taxChargeType) {
        this.taxChargeType = taxChargeType;
    }

    public type.Transaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(type.Transaction transaction) {
        this.transaction = transaction;
    }

    public type.TransactionTaxMetaData getTransactionTaxMetaData() {
        return this.transactionTaxMetaData;
    }

    public void setTransactionTaxMetaData(type.TransactionTaxMetaData transactionTaxMetaData) {
        this.transactionTaxMetaData = transactionTaxMetaData;
    }

}
