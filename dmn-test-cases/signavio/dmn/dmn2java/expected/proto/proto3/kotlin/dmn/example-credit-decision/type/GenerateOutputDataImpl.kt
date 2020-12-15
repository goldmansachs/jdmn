package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "generateOutputData"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class GenerateOutputDataImpl : GenerateOutputData {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Decision")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Decision")
    override var decision: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Assessment")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Assessment")
    override var assessment: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Issue")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Issue")
    override var issue: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (assessment: java.math.BigDecimal?, decision: String?, issue: java.math.BigDecimal?) {
        this.assessment = assessment
        this.decision = decision
        this.issue = issue
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
