
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "isForexRateRequired"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class IsForexRateRequiredInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String derivativeType;
    private String taxChargeType;
    private type.TransactionTaxMetaData transactionTaxMetaData;

    public IsForexRateRequiredInput_() {
    }

    public IsForexRateRequiredInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object derivativeType = input_.get("DerivativeType");
            setDerivativeType((String)derivativeType);
            Object taxChargeType = input_.get("TaxChargeType");
            setTaxChargeType((String)taxChargeType);
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

    public type.TransactionTaxMetaData getTransactionTaxMetaData() {
        return this.transactionTaxMetaData;
    }

    public void setTransactionTaxMetaData(type.TransactionTaxMetaData transactionTaxMetaData) {
        this.transactionTaxMetaData = transactionTaxMetaData;
    }

}
