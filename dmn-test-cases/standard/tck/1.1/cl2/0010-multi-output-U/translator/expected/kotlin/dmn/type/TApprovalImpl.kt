package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tApproval"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TApprovalImpl : TApproval {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Status")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Status")
    override var status: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    override var rate: String? = null

    constructor() {
    }

    constructor (rate: String?, status: String?) {
        this.rate = rate
        this.status = status
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
