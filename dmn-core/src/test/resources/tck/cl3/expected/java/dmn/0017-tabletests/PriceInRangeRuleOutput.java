
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "priceInRange"})
public class PriceInRangeRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String priceInRange;
    private Integer priceInRangePriority;

    public PriceInRangeRuleOutput(boolean matched) {
        super(matched);
    }

    public String getPriceInRange() {
        return this.priceInRange;
    }
    public void setPriceInRange(String priceInRange) {
        this.priceInRange = priceInRange;
    }

    public Integer getPriceInRangePriority() {
        return this.priceInRangePriority;
    }
    public void setPriceInRangePriority(Integer priceInRangePriority) {
        this.priceInRangePriority = priceInRangePriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceInRangeRuleOutput other = (PriceInRangeRuleOutput) o;
        if (this.getPriceInRange() != null ? !this.getPriceInRange().equals(other.getPriceInRange()) : other.getPriceInRange() != null) return false;
        if (this.getPriceInRangePriority() != null ? !this.getPriceInRangePriority().equals(other.getPriceInRangePriority()) : other.getPriceInRangePriority() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPriceInRange() != null ? this.getPriceInRange().hashCode() : 0);
        result = 31 * result + (this.getPriceInRangePriority() != null ? this.getPriceInRangePriority().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", priceInRange='%s'", priceInRange));
        result_.append(")");
        return result_.toString();
    }

    @Override
    public List<com.gs.dmn.runtime.RuleOutput> sort(List<com.gs.dmn.runtime.RuleOutput> matchedResults_) {
        List<com.gs.dmn.runtime.Pair<String, Integer>> priceInRangePairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            priceInRangePairs.add(new com.gs.dmn.runtime.Pair(((PriceInRangeRuleOutput)matchedResult_).getPriceInRange(), ((PriceInRangeRuleOutput)matchedResult_).getPriceInRangePriority()));
        });
        priceInRangePairs.sort(new com.gs.dmn.runtime.PairComparator());

        List<com.gs.dmn.runtime.RuleOutput> result_ = new ArrayList<com.gs.dmn.runtime.RuleOutput>();
        for(int i=0; i<matchedResults_.size(); i++) {
            PriceInRangeRuleOutput output_ = new PriceInRangeRuleOutput(true);
            output_.setPriceInRange(priceInRangePairs.get(i).getLeft());
            output_.setPriceInRangePriority(priceInRangePairs.get(i).getRight());
            result_.add(output_);
        }
        return result_;
    }
}
