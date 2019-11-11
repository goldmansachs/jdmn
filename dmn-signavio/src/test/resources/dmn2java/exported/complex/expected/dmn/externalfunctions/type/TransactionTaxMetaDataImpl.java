package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "transactionTaxMetaData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TransactionTaxMetaDataImpl implements TransactionTaxMetaData {
        private String taxType;
        private String jurisdiction;
        private String assetClass;

    public TransactionTaxMetaDataImpl() {
    }

    public TransactionTaxMetaDataImpl(String assetClass, String jurisdiction, String taxType) {
        this.setAssetClass(assetClass);
        this.setJurisdiction(jurisdiction);
        this.setTaxType(taxType);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("assetClass")
    public String getAssetClass() {
        return this.assetClass;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("assetClass")
    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("jurisdiction")
    public String getJurisdiction() {
        return this.jurisdiction;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("jurisdiction")
    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("taxType")
    public String getTaxType() {
        return this.taxType;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("taxType")
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
