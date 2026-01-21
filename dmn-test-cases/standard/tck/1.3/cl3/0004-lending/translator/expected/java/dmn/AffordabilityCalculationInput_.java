
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "AffordabilityCalculation"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AffordabilityCalculationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private java.lang.Number monthlyIncome;
        private java.lang.Number monthlyRepayments;
        private java.lang.Number monthlyExpenses;
        private String riskCategory;
        private java.lang.Number requiredMonthlyInstallment;

    public java.lang.Number getMonthlyIncome() {
        return this.monthlyIncome;
    }

    public void setMonthlyIncome(java.lang.Number monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public java.lang.Number getMonthlyRepayments() {
        return this.monthlyRepayments;
    }

    public void setMonthlyRepayments(java.lang.Number monthlyRepayments) {
        this.monthlyRepayments = monthlyRepayments;
    }

    public java.lang.Number getMonthlyExpenses() {
        return this.monthlyExpenses;
    }

    public void setMonthlyExpenses(java.lang.Number monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public String getRiskCategory() {
        return this.riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public java.lang.Number getRequiredMonthlyInstallment() {
        return this.requiredMonthlyInstallment;
    }

    public void setRequiredMonthlyInstallment(java.lang.Number requiredMonthlyInstallment) {
        this.requiredMonthlyInstallment = requiredMonthlyInstallment;
    }

}
