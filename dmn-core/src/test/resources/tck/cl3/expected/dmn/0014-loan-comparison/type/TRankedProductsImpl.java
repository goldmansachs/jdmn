package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tRankedProducts"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TRankedProductsImpl implements TRankedProducts {
        private List<type.TMetric> metricsTable;
        private List<type.TMetric> rankByRate;
        private List<type.TMetric> rankByDownPmt;
        private List<type.TMetric> rankByMonthlyPmt;
        private List<type.TMetric> rankByEquityPct;

    public TRankedProductsImpl() {
    }

    public TRankedProductsImpl(List<type.TMetric> metricsTable, List<type.TMetric> rankByDownPmt, List<type.TMetric> rankByEquityPct, List<type.TMetric> rankByMonthlyPmt, List<type.TMetric> rankByRate) {
        this.setMetricsTable(metricsTable);
        this.setRankByDownPmt(rankByDownPmt);
        this.setRankByEquityPct(rankByEquityPct);
        this.setRankByMonthlyPmt(rankByMonthlyPmt);
        this.setRankByRate(rankByRate);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("metricsTable")
    public List<type.TMetric> getMetricsTable() {
        return this.metricsTable;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("metricsTable")
    public void setMetricsTable(List<type.TMetric> metricsTable) {
        this.metricsTable = metricsTable;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rankByDownPmt")
    public List<type.TMetric> getRankByDownPmt() {
        return this.rankByDownPmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rankByDownPmt")
    public void setRankByDownPmt(List<type.TMetric> rankByDownPmt) {
        this.rankByDownPmt = rankByDownPmt;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rankByEquityPct")
    public List<type.TMetric> getRankByEquityPct() {
        return this.rankByEquityPct;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rankByEquityPct")
    public void setRankByEquityPct(List<type.TMetric> rankByEquityPct) {
        this.rankByEquityPct = rankByEquityPct;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rankByMonthlyPmt")
    public List<type.TMetric> getRankByMonthlyPmt() {
        return this.rankByMonthlyPmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rankByMonthlyPmt")
    public void setRankByMonthlyPmt(List<type.TMetric> rankByMonthlyPmt) {
        this.rankByMonthlyPmt = rankByMonthlyPmt;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rankByRate")
    public List<type.TMetric> getRankByRate() {
        return this.rankByRate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rankByRate")
    public void setRankByRate(List<type.TMetric> rankByRate) {
        this.rankByRate = rankByRate;
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
