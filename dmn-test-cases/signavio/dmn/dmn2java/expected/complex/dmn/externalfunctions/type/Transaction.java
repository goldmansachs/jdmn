package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "transaction"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TransactionImpl.class)
public interface Transaction extends com.gs.dmn.runtime.DMNType {
    static Transaction toTransaction(Object other) {
        if (other == null) {
            return null;
        } else if (Transaction.class.isAssignableFrom(other.getClass())) {
            return (Transaction)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TransactionImpl result_ = new TransactionImpl();
            Object tradeDate = ((com.gs.dmn.runtime.Context)other).get("tradeDate", "tradeDate");
            result_.setTradeDate((java.time.LocalDate)tradeDate);
            Object buySellIndicator = ((com.gs.dmn.runtime.Context)other).get("buySellIndicator", "buySellIndicator");
            result_.setBuySellIndicator((String)buySellIndicator);
            Object offeringType = ((com.gs.dmn.runtime.Context)other).get("offeringType", "offeringType");
            result_.setOfferingType((String)offeringType);
            Object exerciseAssign = ((com.gs.dmn.runtime.Context)other).get("exerciseAssign", "exerciseAssign");
            result_.setExerciseAssign((Boolean)exerciseAssign);
            Object settlementDate = ((com.gs.dmn.runtime.Context)other).get("settlementDate", "settlementDate");
            result_.setSettlementDate((java.time.LocalDate)settlementDate);
            Object clearingAgent = ((com.gs.dmn.runtime.Context)other).get("clearingAgent", "clearingAgent");
            result_.setClearingAgent((String)clearingAgent);
            Object deliveryInstruction = ((com.gs.dmn.runtime.Context)other).get("deliveryInstruction", "deliveryInstruction");
            result_.setDeliveryInstruction((Boolean)deliveryInstruction);
            Object executionCapacity = ((com.gs.dmn.runtime.Context)other).get("executionCapacity", "executionCapacity");
            result_.setExecutionCapacity((String)executionCapacity);
            Object quantity = ((com.gs.dmn.runtime.Context)other).get("quantity", "quantity");
            result_.setQuantity((java.lang.Number)quantity);
            Object price = ((com.gs.dmn.runtime.Context)other).get("price", "price");
            result_.setPrice((java.lang.Number)price);
            Object commision = ((com.gs.dmn.runtime.Context)other).get("commision", "commision");
            result_.setCommision((java.lang.Number)commision);
            Object executionMarket = ((com.gs.dmn.runtime.Context)other).get("executionMarket", "executionMarket");
            result_.setExecutionMarket((String)executionMarket);
            Object currencyCode = ((com.gs.dmn.runtime.Context)other).get("currencyCode", "currencyCode");
            result_.setCurrencyCode((String)currencyCode);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTransaction(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Transaction.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("tradeDate")
    java.time.LocalDate getTradeDate();

    @com.fasterxml.jackson.annotation.JsonGetter("buySellIndicator")
    String getBuySellIndicator();

    @com.fasterxml.jackson.annotation.JsonGetter("offeringType")
    String getOfferingType();

    @com.fasterxml.jackson.annotation.JsonGetter("exerciseAssign")
    Boolean getExerciseAssign();

    @com.fasterxml.jackson.annotation.JsonGetter("settlementDate")
    java.time.LocalDate getSettlementDate();

    @com.fasterxml.jackson.annotation.JsonGetter("clearingAgent")
    String getClearingAgent();

    @com.fasterxml.jackson.annotation.JsonGetter("deliveryInstruction")
    Boolean getDeliveryInstruction();

    @com.fasterxml.jackson.annotation.JsonGetter("executionCapacity")
    String getExecutionCapacity();

    @com.fasterxml.jackson.annotation.JsonGetter("quantity")
    java.lang.Number getQuantity();

    @com.fasterxml.jackson.annotation.JsonGetter("price")
    java.lang.Number getPrice();

    @com.fasterxml.jackson.annotation.JsonGetter("commision")
    java.lang.Number getCommision();

    @com.fasterxml.jackson.annotation.JsonGetter("executionMarket")
    String getExecutionMarket();

    @com.fasterxml.jackson.annotation.JsonGetter("currencyCode")
    String getCurrencyCode();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.add("tradeDate", getTradeDate());
        context.add("buySellIndicator", getBuySellIndicator());
        context.add("offeringType", getOfferingType());
        context.add("exerciseAssign", getExerciseAssign());
        context.add("settlementDate", getSettlementDate());
        context.add("clearingAgent", getClearingAgent());
        context.add("deliveryInstruction", getDeliveryInstruction());
        context.add("executionCapacity", getExecutionCapacity());
        context.add("quantity", getQuantity());
        context.add("price", getPrice());
        context.add("commision", getCommision());
        context.add("executionMarket", getExecutionMarket());
        context.add("currencyCode", getCurrencyCode());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction other = (Transaction) o;
        if (this.getBuySellIndicator() != null ? !this.getBuySellIndicator().equals(other.getBuySellIndicator()) : other.getBuySellIndicator() != null) return false;
        if (this.getClearingAgent() != null ? !this.getClearingAgent().equals(other.getClearingAgent()) : other.getClearingAgent() != null) return false;
        if (this.getCommision() != null ? !this.getCommision().equals(other.getCommision()) : other.getCommision() != null) return false;
        if (this.getCurrencyCode() != null ? !this.getCurrencyCode().equals(other.getCurrencyCode()) : other.getCurrencyCode() != null) return false;
        if (this.getDeliveryInstruction() != null ? !this.getDeliveryInstruction().equals(other.getDeliveryInstruction()) : other.getDeliveryInstruction() != null) return false;
        if (this.getExecutionCapacity() != null ? !this.getExecutionCapacity().equals(other.getExecutionCapacity()) : other.getExecutionCapacity() != null) return false;
        if (this.getExecutionMarket() != null ? !this.getExecutionMarket().equals(other.getExecutionMarket()) : other.getExecutionMarket() != null) return false;
        if (this.getExerciseAssign() != null ? !this.getExerciseAssign().equals(other.getExerciseAssign()) : other.getExerciseAssign() != null) return false;
        if (this.getOfferingType() != null ? !this.getOfferingType().equals(other.getOfferingType()) : other.getOfferingType() != null) return false;
        if (this.getPrice() != null ? !this.getPrice().equals(other.getPrice()) : other.getPrice() != null) return false;
        if (this.getQuantity() != null ? !this.getQuantity().equals(other.getQuantity()) : other.getQuantity() != null) return false;
        if (this.getSettlementDate() != null ? !this.getSettlementDate().equals(other.getSettlementDate()) : other.getSettlementDate() != null) return false;
        if (this.getTradeDate() != null ? !this.getTradeDate().equals(other.getTradeDate()) : other.getTradeDate() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getBuySellIndicator() != null ? this.getBuySellIndicator().hashCode() : 0);
        result = 31 * result + (this.getClearingAgent() != null ? this.getClearingAgent().hashCode() : 0);
        result = 31 * result + (this.getCommision() != null ? this.getCommision().hashCode() : 0);
        result = 31 * result + (this.getCurrencyCode() != null ? this.getCurrencyCode().hashCode() : 0);
        result = 31 * result + (this.getDeliveryInstruction() != null ? this.getDeliveryInstruction().hashCode() : 0);
        result = 31 * result + (this.getExecutionCapacity() != null ? this.getExecutionCapacity().hashCode() : 0);
        result = 31 * result + (this.getExecutionMarket() != null ? this.getExecutionMarket().hashCode() : 0);
        result = 31 * result + (this.getExerciseAssign() != null ? this.getExerciseAssign().hashCode() : 0);
        result = 31 * result + (this.getOfferingType() != null ? this.getOfferingType().hashCode() : 0);
        result = 31 * result + (this.getPrice() != null ? this.getPrice().hashCode() : 0);
        result = 31 * result + (this.getQuantity() != null ? this.getQuantity().hashCode() : 0);
        result = 31 * result + (this.getSettlementDate() != null ? this.getSettlementDate().hashCode() : 0);
        result = 31 * result + (this.getTradeDate() != null ? this.getTradeDate().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("buySellIndicator=" + getBuySellIndicator());
        result_.append(", clearingAgent=" + getClearingAgent());
        result_.append(", commision=" + getCommision());
        result_.append(", currencyCode=" + getCurrencyCode());
        result_.append(", deliveryInstruction=" + getDeliveryInstruction());
        result_.append(", executionCapacity=" + getExecutionCapacity());
        result_.append(", executionMarket=" + getExecutionMarket());
        result_.append(", exerciseAssign=" + getExerciseAssign());
        result_.append(", offeringType=" + getOfferingType());
        result_.append(", price=" + getPrice());
        result_.append(", quantity=" + getQuantity());
        result_.append(", settlementDate=" + getSettlementDate());
        result_.append(", tradeDate=" + getTradeDate());
        result_.append("}");
        return result_.toString();
    }
}
