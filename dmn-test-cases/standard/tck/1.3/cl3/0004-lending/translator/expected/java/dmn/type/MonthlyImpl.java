package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "Monthly"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class MonthlyImpl implements Monthly {
        private java.lang.Number income;
        private java.lang.Number expenses;
        private java.lang.Number repayments;

    public MonthlyImpl() {
    }

    public MonthlyImpl(java.lang.Number expenses, java.lang.Number income, java.lang.Number repayments) {
        this.setExpenses(expenses);
        this.setIncome(income);
        this.setRepayments(repayments);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    public java.lang.Number getExpenses() {
        return this.expenses;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Expenses")
    public void setExpenses(java.lang.Number expenses) {
        this.expenses = expenses;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Income")
    public java.lang.Number getIncome() {
        return this.income;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Income")
    public void setIncome(java.lang.Number income) {
        this.income = income;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    public java.lang.Number getRepayments() {
        return this.repayments;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Repayments")
    public void setRepayments(java.lang.Number repayments) {
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
