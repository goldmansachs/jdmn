package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tApplicantData"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TApplicantDataImpl::class)
interface TApplicantData : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Monthly")
    val monthly: type.Monthly?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Age")
    val age: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("ExistingCustomer")
    val existingCustomer: Boolean?

    @get:com.fasterxml.jackson.annotation.JsonGetter("MaritalStatus")
    val maritalStatus: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("EmploymentStatus")
    val employmentStatus: String?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("monthly", this.monthly)
        context.put("age", this.age)
        context.put("existingCustomer", this.existingCustomer)
        context.put("maritalStatus", this.maritalStatus)
        context.put("employmentStatus", this.employmentStatus)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TApplicantData
        if (if (this.age != null) this.age != other.age else other.age != null) return false
        if (if (this.employmentStatus != null) this.employmentStatus != other.employmentStatus else other.employmentStatus != null) return false
        if (if (this.existingCustomer != null) this.existingCustomer != other.existingCustomer else other.existingCustomer != null) return false
        if (if (this.maritalStatus != null) this.maritalStatus != other.maritalStatus else other.maritalStatus != null) return false
        if (if (this.monthly != null) this.monthly != other.monthly else other.monthly != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.age != null) this.age.hashCode() else 0)
        result = 31 * result + (if (this.employmentStatus != null) this.employmentStatus.hashCode() else 0)
        result = 31 * result + (if (this.existingCustomer != null) this.existingCustomer.hashCode() else 0)
        result = 31 * result + (if (this.maritalStatus != null) this.maritalStatus.hashCode() else 0)
        result = 31 * result + (if (this.monthly != null) this.monthly.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Age=" + age)
        result_.append(", EmploymentStatus=" + employmentStatus)
        result_.append(", ExistingCustomer=" + existingCustomer)
        result_.append(", MaritalStatus=" + maritalStatus)
        result_.append(", Monthly=" + monthly)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTApplicantData(other: Any?): TApplicantData? {
            if (other == null) {
                return null
            } else if (other is TApplicantData?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TApplicantDataImpl()
                result_.monthly = other.get("Monthly") as type.Monthly?
                result_.age = other.get("Age") as java.math.BigDecimal?
                result_.existingCustomer = other.get("ExistingCustomer") as Boolean?
                result_.maritalStatus = other.get("MaritalStatus") as String?
                result_.employmentStatus = other.get("EmploymentStatus") as String?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTApplicantData(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TApplicantData::class.java.getSimpleName()))
            }
        }
    }
}
