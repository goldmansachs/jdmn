package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "applicant"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class ApplicantImpl : Applicant {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Name")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Name")
    override var name: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Age")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Age")
    override var age: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Credit score")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Credit score")
    override var creditScore: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Prior issues")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Prior issues")
    override var priorIssues: List<String?>? = null

    constructor() {
    }

    constructor (age: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, name: String?, priorIssues: List<String?>?) {
        this.age = age
        this.creditScore = creditScore
        this.name = name
        this.priorIssues = priorIssues
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
