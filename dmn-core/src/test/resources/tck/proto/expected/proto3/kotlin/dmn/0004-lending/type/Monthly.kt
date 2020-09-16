package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "Monthly"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.MonthlyImpl::class)
interface Monthly : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Income")
    val income: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    val expenses: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    val repayments: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("income", this.income)
        context.put("expenses", this.expenses)
        context.put("repayments", this.repayments)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as Monthly
        if (if (this.expenses != null) this.expenses != other.expenses else other.expenses != null) return false
        if (if (this.income != null) this.income != other.income else other.income != null) return false
        if (if (this.repayments != null) this.repayments != other.repayments else other.repayments != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.expenses != null) this.expenses.hashCode() else 0)
        result = 31 * result + (if (this.income != null) this.income.hashCode() else 0)
        result = 31 * result + (if (this.repayments != null) this.repayments.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Expenses=" + expenses)
        result_.append(", Income=" + income)
        result_.append(", Repayments=" + repayments)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toMonthly(other: Any?): Monthly? {
            if (other == null) {
                return null
            } else if (other is Monthly?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = MonthlyImpl()
                result_.income = other.get("Income") as java.math.BigDecimal?
                result_.expenses = other.get("Expenses") as java.math.BigDecimal?
                result_.repayments = other.get("Repayments") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toMonthly(other.toContext())
            } else if (other is proto.Monthly) {
                var result_: MonthlyImpl = MonthlyImpl()
                result_.income = java.math.BigDecimal.valueOf((other as proto.Monthly).getIncome())
                result_.expenses = java.math.BigDecimal.valueOf((other as proto.Monthly).getExpenses())
                result_.repayments = java.math.BigDecimal.valueOf((other as proto.Monthly).getRepayments())
                return result_
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), Monthly::class.java.getSimpleName()))
            }
        }

        @JvmStatic
        fun toProto(other: Monthly?): proto.Monthly {
            var result_: proto.Monthly.Builder = proto.Monthly.newBuilder()
            if (other != null) {
                var incomeProto_: Double = (if ((other as Monthly).income == null) 0.0 else (other as Monthly).income!!.toDouble())
                result_.setIncome(incomeProto_)
                var expensesProto_: Double = (if ((other as Monthly).expenses == null) 0.0 else (other as Monthly).expenses!!.toDouble())
                result_.setExpenses(expensesProto_)
                var repaymentsProto_: Double = (if ((other as Monthly).repayments == null) 0.0 else (other as Monthly).repayments!!.toDouble())
                result_.setRepayments(repaymentsProto_)
            }
            return result_.build()
        }

        @JvmStatic
        fun toProto(other: List<Monthly?>?): List<proto.Monthly>? {
            if (other == null) {
                return null
            } else {
                return other.stream().map({o -> toProto(o)}).collect(java.util.stream.Collectors.toList())
            }
        }
    }
}
