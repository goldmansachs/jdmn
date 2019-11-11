package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "transactionTaxMetaData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TransactionTaxMetaDataImpl.class)
public interface TransactionTaxMetaData extends com.gs.dmn.runtime.DMNType {
    static TransactionTaxMetaData toTransactionTaxMetaData(Object other) {
        if (other == null) {
            return null;
        } else if (TransactionTaxMetaData.class.isAssignableFrom(other.getClass())) {
            return (TransactionTaxMetaData)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TransactionTaxMetaDataImpl result_ = new TransactionTaxMetaDataImpl();
            result_.setTaxType((String)((com.gs.dmn.runtime.Context)other).get("taxType", "taxType"));
            result_.setJurisdiction((String)((com.gs.dmn.runtime.Context)other).get("jurisdiction", "jurisdiction"));
            result_.setAssetClass((String)((com.gs.dmn.runtime.Context)other).get("assetClass", "assetClass"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTransactionTaxMetaData(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TransactionTaxMetaData.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("taxType")
    String getTaxType();

    @com.fasterxml.jackson.annotation.JsonGetter("jurisdiction")
    String getJurisdiction();

    @com.fasterxml.jackson.annotation.JsonGetter("assetClass")
    String getAssetClass();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("taxType", getTaxType());
        context.put("jurisdiction", getJurisdiction());
        context.put("assetClass", getAssetClass());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionTaxMetaData other = (TransactionTaxMetaData) o;
        if (this.getAssetClass() != null ? !this.getAssetClass().equals(other.getAssetClass()) : other.getAssetClass() != null) return false;
        if (this.getJurisdiction() != null ? !this.getJurisdiction().equals(other.getJurisdiction()) : other.getJurisdiction() != null) return false;
        if (this.getTaxType() != null ? !this.getTaxType().equals(other.getTaxType()) : other.getTaxType() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAssetClass() != null ? this.getAssetClass().hashCode() : 0);
        result = 31 * result + (this.getJurisdiction() != null ? this.getJurisdiction().hashCode() : 0);
        result = 31 * result + (this.getTaxType() != null ? this.getTaxType().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("assetClass=" + getAssetClass());
        result_.append(", jurisdiction=" + getJurisdiction());
        result_.append(", taxType=" + getTaxType());
        result_.append("}");
        return result_.toString();
    }
}
