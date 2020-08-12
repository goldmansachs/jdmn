package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tApplicantData"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TApplicantDataImpl : TApplicantData {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Monthly")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Monthly")
    override var monthly: type.Monthly? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Age")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Age")
    override var age: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("ExistingCustomer")
    @set:com.fasterxml.jackson.annotation.JsonGetter("ExistingCustomer")
    override var existingCustomer: Boolean? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("MaritalStatus")
    @set:com.fasterxml.jackson.annotation.JsonGetter("MaritalStatus")
    override var maritalStatus: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("EmploymentStatus")
    @set:com.fasterxml.jackson.annotation.JsonGetter("EmploymentStatus")
    override var employmentStatus: String? = null

    constructor() {
    }

    constructor (age: java.math.BigDecimal?, employmentStatus: String?, existingCustomer: Boolean?, maritalStatus: String?, monthly: type.Monthly?) {
        this.age = age
        this.employmentStatus = employmentStatus
        this.existingCustomer = existingCustomer
        this.maritalStatus = maritalStatus
        this.monthly = monthly
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
