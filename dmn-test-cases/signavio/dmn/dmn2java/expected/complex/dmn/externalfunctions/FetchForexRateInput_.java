
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "fetchForexRate"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FetchForexRateInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String derivativeType;
    private String taxChargeType;
    private type.Transaction transaction;
    private type.TransactionTaxMetaData transactionTaxMetaData;

    public FetchForexRateInput_() {
    }

    public FetchForexRateInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object derivativeType = input_.get("DerivativeType");
            setDerivativeType((String)derivativeType);
            Object taxChargeType = input_.get("TaxChargeType");
            setTaxChargeType((String)taxChargeType);
            Object transaction = input_.get("Transaction");
            setTransaction(type.Transaction.toTransaction(null));
            Object transactionTaxMetaData = input_.get("TransactionTaxMetaData");
            setTransactionTaxMetaData(type.TransactionTaxMetaData.toTransactionTaxMetaData(null));
        }
    }

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
