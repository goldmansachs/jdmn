
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "AffordabilityCalculation"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AffordabilityCalculationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number monthlyIncome;
    private java.lang.Number monthlyRepayments;
    private java.lang.Number monthlyExpenses;
    private String riskCategory;
    private java.lang.Number requiredMonthlyInstallment;

    public AffordabilityCalculationInput_() {
    }

    public AffordabilityCalculationInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object monthlyIncome = input_.get("MonthlyIncome");
            setMonthlyIncome((java.lang.Number)monthlyIncome);
            Object monthlyRepayments = input_.get("MonthlyRepayments");
            setMonthlyRepayments((java.lang.Number)monthlyRepayments);
            Object monthlyExpenses = input_.get("MonthlyExpenses");
            setMonthlyExpenses((java.lang.Number)monthlyExpenses);
            Object riskCategory = input_.get("RiskCategory");
            setRiskCategory((String)riskCategory);
            Object requiredMonthlyInstallment = input_.get("RequiredMonthlyInstallment");
            setRequiredMonthlyInstallment((java.lang.Number)requiredMonthlyInstallment);
        }
    }

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
