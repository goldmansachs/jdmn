package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "Monthly"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class MonthlyImpl implements Monthly {
        private java.math.BigDecimal income;
        private java.math.BigDecimal expenses;
        private java.math.BigDecimal repayments;

    public MonthlyImpl() {
    }

    public MonthlyImpl(java.math.BigDecimal expenses, java.math.BigDecimal income, java.math.BigDecimal repayments) {
        this.setExpenses(expenses);
        this.setIncome(income);
        this.setRepayments(repayments);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    public java.math.BigDecimal getExpenses() {
        return this.expenses;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Expenses")
    public void setExpenses(java.math.BigDecimal expenses) {
        this.expenses = expenses;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Income")
    public java.math.BigDecimal getIncome() {
        return this.income;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Income")
    public void setIncome(java.math.BigDecimal income) {
        this.income = income;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    public java.math.BigDecimal getRepayments() {
        return this.repayments;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Repayments")
    public void setRepayments(java.math.BigDecimal repayments) {
        this.repayments = repayments;
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
