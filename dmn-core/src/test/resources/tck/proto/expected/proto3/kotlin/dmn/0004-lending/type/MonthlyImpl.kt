package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "Monthly"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class MonthlyImpl : Monthly {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Income")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Income")
    override var income: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    override var expenses: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    override var repayments: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (expenses: java.math.BigDecimal?, income: java.math.BigDecimal?, repayments: java.math.BigDecimal?) {
        this.expenses = expenses
        this.income = income
        this.repayments = repayments
    }

    override fun equals(other: Any?): Boolean {
        return equalTo(other)
    }

    override fun hashCode(): Int {
        return hash()
    }

    @Override
    override fun toString(): String {
        return asString()
    }
}
