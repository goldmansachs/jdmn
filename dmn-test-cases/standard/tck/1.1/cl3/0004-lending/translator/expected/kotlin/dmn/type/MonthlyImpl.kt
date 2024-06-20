package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "Monthly"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class MonthlyImpl : Monthly {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Income")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Income")
    override var income: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    override var expenses: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    override var repayments: java.lang.Number? = null

    constructor() {
    }

    constructor (expenses: java.lang.Number?, income: java.lang.Number?, repayments: java.lang.Number?) {
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
