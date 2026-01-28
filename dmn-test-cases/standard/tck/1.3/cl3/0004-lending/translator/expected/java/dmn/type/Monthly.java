package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "Monthly"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.MonthlyImpl.class)
public interface Monthly extends com.gs.dmn.runtime.DMNType {
    static Monthly toMonthly(Object other) {
        if (other == null) {
            return null;
        } else if (Monthly.class.isAssignableFrom(other.getClass())) {
            return (Monthly)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            MonthlyImpl result_ = new MonthlyImpl();
            Object income = ((com.gs.dmn.runtime.Context)other).get("Income");
            result_.setIncome((java.lang.Number)income);
            Object expenses = ((com.gs.dmn.runtime.Context)other).get("Expenses");
            result_.setExpenses((java.lang.Number)expenses);
            Object repayments = ((com.gs.dmn.runtime.Context)other).get("Repayments");
            result_.setRepayments((java.lang.Number)repayments);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toMonthly(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Monthly.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Income")
    java.lang.Number getIncome();

    @com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    java.lang.Number getExpenses();

    @com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    java.lang.Number getRepayments();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.add("income", getIncome());
        context.add("expenses", getExpenses());
        context.add("repayments", getRepayments());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Monthly other = (Monthly) o;
        if (this.getExpenses() != null ? !this.getExpenses().equals(other.getExpenses()) : other.getExpenses() != null) return false;
        if (this.getIncome() != null ? !this.getIncome().equals(other.getIncome()) : other.getIncome() != null) return false;
        if (this.getRepayments() != null ? !this.getRepayments().equals(other.getRepayments()) : other.getRepayments() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getExpenses() != null ? this.getExpenses().hashCode() : 0);
        result = 31 * result + (this.getIncome() != null ? this.getIncome().hashCode() : 0);
        result = 31 * result + (this.getRepayments() != null ? this.getRepayments().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Expenses=" + getExpenses());
        result_.append(", Income=" + getIncome());
        result_.append(", Repayments=" + getRepayments());
        result_.append("}");
        return result_.toString();
    }
}
