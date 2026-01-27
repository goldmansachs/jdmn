
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "isForexRateRequired"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class IsForexRateRequiredInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String derivativeType;
    private String taxChargeType;
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

    public type.TransactionTaxMetaData getTransactionTaxMetaData() {
        return this.transactionTaxMetaData;
    }

    public void setTransactionTaxMetaData(type.TransactionTaxMetaData transactionTaxMetaData) {
        this.transactionTaxMetaData = transactionTaxMetaData;
    }

}
