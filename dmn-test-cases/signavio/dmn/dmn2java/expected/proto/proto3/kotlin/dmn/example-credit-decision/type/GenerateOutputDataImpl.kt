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
    override var assessment: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Issue")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Issue")
    override var issue: kotlin.Number? = null

    constructor() {
    }

    constructor (assessment: kotlin.Number?, decision: String?, issue: kotlin.Number?) {
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
