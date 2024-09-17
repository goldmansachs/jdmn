package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "transaction"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TransactionImpl implements Transaction {
        private java.time.LocalDate tradeDate;
        private String buySellIndicator;
        private String offeringType;
        private Boolean exerciseAssign;
        private java.time.LocalDate settlementDate;
        private String clearingAgent;
        private Boolean deliveryInstruction;
        private String executionCapacity;
        private java.lang.Number quantity;
        private java.lang.Number price;
        private java.lang.Number commision;
        private String executionMarket;
        private String currencyCode;

    public TransactionImpl() {
    }

    public TransactionImpl(String buySellIndicator, String clearingAgent, java.lang.Number commision, String currencyCode, Boolean deliveryInstruction, String executionCapacity, String executionMarket, Boolean exerciseAssign, String offeringType, java.lang.Number price, java.lang.Number quantity, java.time.LocalDate settlementDate, java.time.LocalDate tradeDate) {
        this.setBuySellIndicator(buySellIndicator);
        this.setClearingAgent(clearingAgent);
        this.setCommision(commision);
        this.setCurrencyCode(currencyCode);
        this.setDeliveryInstruction(deliveryInstruction);
        this.setExecutionCapacity(executionCapacity);
        this.setExecutionMarket(executionMarket);
        this.setExerciseAssign(exerciseAssign);
        this.setOfferingType(offeringType);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setSettlementDate(settlementDate);
        this.setTradeDate(tradeDate);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("buySellIndicator")
    public String getBuySellIndicator() {
        return this.buySellIndicator;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("buySellIndicator")
    public void setBuySellIndicator(String buySellIndicator) {
        this.buySellIndicator = buySellIndicator;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("clearingAgent")
    public String getClearingAgent() {
        return this.clearingAgent;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("clearingAgent")
    public void setClearingAgent(String clearingAgent) {
        this.clearingAgent = clearingAgent;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("commision")
    public java.lang.Number getCommision() {
        return this.commision;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("commision")
    public void setCommision(java.lang.Number commision) {
        this.commision = commision;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("currencyCode")
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("currencyCode")
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("deliveryInstruction")
    public Boolean getDeliveryInstruction() {
        return this.deliveryInstruction;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("deliveryInstruction")
    public void setDeliveryInstruction(Boolean deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("executionCapacity")
    public String getExecutionCapacity() {
        return this.executionCapacity;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("executionCapacity")
    public void setExecutionCapacity(String executionCapacity) {
        this.executionCapacity = executionCapacity;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("executionMarket")
    public String getExecutionMarket() {
        return this.executionMarket;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("executionMarket")
    public void setExecutionMarket(String executionMarket) {
        this.executionMarket = executionMarket;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("exerciseAssign")
    public Boolean getExerciseAssign() {
        return this.exerciseAssign;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("exerciseAssign")
    public void setExerciseAssign(Boolean exerciseAssign) {
        this.exerciseAssign = exerciseAssign;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("offeringType")
    public String getOfferingType() {
        return this.offeringType;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("offeringType")
    public void setOfferingType(String offeringType) {
        this.offeringType = offeringType;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("price")
    public java.lang.Number getPrice() {
        return this.price;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("price")
    public void setPrice(java.lang.Number price) {
        this.price = price;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("quantity")
    public java.lang.Number getQuantity() {
        return this.quantity;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("quantity")
    public void setQuantity(java.lang.Number quantity) {
        this.quantity = quantity;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("settlementDate")
    public java.time.LocalDate getSettlementDate() {
        return this.settlementDate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("settlementDate")
    public void setSettlementDate(java.time.LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("tradeDate")
    public java.time.LocalDate getTradeDate() {
        return this.tradeDate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("tradeDate")
    public void setTradeDate(java.time.LocalDate tradeDate) {
        this.tradeDate = tradeDate;
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
