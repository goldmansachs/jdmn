package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "Monthly"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class MonthlyImpl : Monthly {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Income")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Income")
    override var income: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Expenses")
    override var expenses: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Repayments")
    override var repayments: kotlin.Number? = null

    constructor() {
    }

    constructor (expenses: kotlin.Number?, income: kotlin.Number?, repayments: kotlin.Number?) {
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
