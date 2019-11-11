
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "compile"})
public class CompileRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String nextTrafficLight;
    private java.math.BigDecimal avgOfNumbers;
    private String name;

    public CompileRuleOutput(boolean matched) {
        super(matched);
    }

    public String getNextTrafficLight() {
        return this.nextTrafficLight;
    }
    public void setNextTrafficLight(String nextTrafficLight) {
        this.nextTrafficLight = nextTrafficLight;
    }
    public java.math.BigDecimal getAvgOfNumbers() {
        return this.avgOfNumbers;
    }
    public void setAvgOfNumbers(java.math.BigDecimal avgOfNumbers) {
        this.avgOfNumbers = avgOfNumbers;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompileRuleOutput other = (CompileRuleOutput) o;
        if (this.getNextTrafficLight() != null ? !this.getNextTrafficLight().equals(other.getNextTrafficLight()) : other.getNextTrafficLight() != null) return false;
        if (this.getAvgOfNumbers() != null ? !this.getAvgOfNumbers().equals(other.getAvgOfNumbers()) : other.getAvgOfNumbers() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getNextTrafficLight() != null ? this.getNextTrafficLight().hashCode() : 0);
        result = 31 * result + (this.getAvgOfNumbers() != null ? this.getAvgOfNumbers().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", nextTrafficLight='%s'", nextTrafficLight));
        result_.append(String.format(", avgOfNumbers='%s'", avgOfNumbers));
        result_.append(String.format(", name='%s'", name));
        result_.append(")");
        return result_.toString();
    }
}
