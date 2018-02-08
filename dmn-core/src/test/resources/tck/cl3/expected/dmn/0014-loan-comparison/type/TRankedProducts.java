package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tRankedProducts"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TRankedProducts extends com.gs.dmn.runtime.DMNType {
    static TRankedProducts toTRankedProducts(Object other) {
        if (other == null) {
            return null;
        } else if (TRankedProducts.class.isAssignableFrom(other.getClass())) {
            return (TRankedProducts)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TRankedProductsImpl result_ = new TRankedProductsImpl();
            result_.setMetricsTable((List<type.TMetric>)((com.gs.dmn.runtime.Context)other).get("metricsTable"));
            result_.setRankByRate((List<type.TMetric>)((com.gs.dmn.runtime.Context)other).get("rankByRate"));
            result_.setRankByDownPmt((List<type.TMetric>)((com.gs.dmn.runtime.Context)other).get("rankByDownPmt"));
            result_.setRankByMonthlyPmt((List<type.TMetric>)((com.gs.dmn.runtime.Context)other).get("rankByMonthlyPmt"));
            result_.setRankByEquityPct((List<type.TMetric>)((com.gs.dmn.runtime.Context)other).get("rankByEquityPct"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTRankedProducts(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TRankedProducts.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("metricsTable")
    List<type.TMetric> getMetricsTable();

    @com.fasterxml.jackson.annotation.JsonGetter("rankByRate")
    List<type.TMetric> getRankByRate();

    @com.fasterxml.jackson.annotation.JsonGetter("rankByDownPmt")
    List<type.TMetric> getRankByDownPmt();

    @com.fasterxml.jackson.annotation.JsonGetter("rankByMonthlyPmt")
    List<type.TMetric> getRankByMonthlyPmt();

    @com.fasterxml.jackson.annotation.JsonGetter("rankByEquityPct")
    List<type.TMetric> getRankByEquityPct();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("metricsTable", getMetricsTable());
        context.put("rankByRate", getRankByRate());
        context.put("rankByDownPmt", getRankByDownPmt());
        context.put("rankByMonthlyPmt", getRankByMonthlyPmt());
        context.put("rankByEquityPct", getRankByEquityPct());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TRankedProducts other = (TRankedProducts) o;
        if (this.getMetricsTable() != null ? !this.getMetricsTable().equals(other.getMetricsTable()) : other.getMetricsTable() != null) return false;
        if (this.getRankByDownPmt() != null ? !this.getRankByDownPmt().equals(other.getRankByDownPmt()) : other.getRankByDownPmt() != null) return false;
        if (this.getRankByEquityPct() != null ? !this.getRankByEquityPct().equals(other.getRankByEquityPct()) : other.getRankByEquityPct() != null) return false;
        if (this.getRankByMonthlyPmt() != null ? !this.getRankByMonthlyPmt().equals(other.getRankByMonthlyPmt()) : other.getRankByMonthlyPmt() != null) return false;
        if (this.getRankByRate() != null ? !this.getRankByRate().equals(other.getRankByRate()) : other.getRankByRate() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getMetricsTable() != null ? this.getMetricsTable().hashCode() : 0);
        result = 31 * result + (this.getRankByDownPmt() != null ? this.getRankByDownPmt().hashCode() : 0);
        result = 31 * result + (this.getRankByEquityPct() != null ? this.getRankByEquityPct().hashCode() : 0);
        result = 31 * result + (this.getRankByMonthlyPmt() != null ? this.getRankByMonthlyPmt().hashCode() : 0);
        result = 31 * result + (this.getRankByRate() != null ? this.getRankByRate().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("metricsTable=" + getMetricsTable());
        result_.append(", rankByDownPmt=" + getRankByDownPmt());
        result_.append(", rankByEquityPct=" + getRankByEquityPct());
        result_.append(", rankByMonthlyPmt=" + getRankByMonthlyPmt());
        result_.append(", rankByRate=" + getRankByRate());
        result_.append("}");
        return result_.toString();
    }
}
