package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "applicant"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.ApplicantImpl::class)
interface Applicant : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Name")
    val name: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Age")
    val age: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Credit score")
    val creditScore: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Prior issues")
    val priorIssues: List<String?>?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("name", this.name)
        context.put("age", this.age)
        context.put("creditScore", this.creditScore)
        context.put("priorIssues", this.priorIssues)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as Applicant
        if (if (this.age != null) this.age != other.age else other.age != null) return false
        if (if (this.creditScore != null) this.creditScore != other.creditScore else other.creditScore != null) return false
        if (if (this.name != null) this.name != other.name else other.name != null) return false
        if (if (this.priorIssues != null) this.priorIssues != other.priorIssues else other.priorIssues != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.age != null) this.age.hashCode() else 0)
        result = 31 * result + (if (this.creditScore != null) this.creditScore.hashCode() else 0)
        result = 31 * result + (if (this.name != null) this.name.hashCode() else 0)
        result = 31 * result + (if (this.priorIssues != null) this.priorIssues.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Age=" + age)
        result_.append(", Credit score=" + creditScore)
        result_.append(", Name=" + name)
        result_.append(", Prior issues=" + priorIssues)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toApplicant(other: Any?): Applicant? {
            if (other == null) {
                return null
            } else if (other is Applicant?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = ApplicantImpl()
                result_.name = other.get("name", "Name") as String?
                result_.age = other.get("age", "Age") as java.math.BigDecimal?
                result_.creditScore = other.get("creditScore", "Credit score") as java.math.BigDecimal?
                result_.priorIssues = other.get("priorIssues", "Prior issues") as List<String?>?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toApplicant(other.toContext())
            } else if (other is proto.Applicant) {
                var result_: ApplicantImpl = ApplicantImpl()
                result_.name = (other as proto.Applicant).getName()
                result_.age = java.math.BigDecimal.valueOf((other as proto.Applicant).getAge())
                result_.creditScore = java.math.BigDecimal.valueOf((other as proto.Applicant).getCreditScore())
                result_.priorIssues = ((other as proto.Applicant).getPriorIssuesList()?.stream()?.map({e -> e})?.collect(java.util.stream.Collectors.toList()) as List<String?>?)
                return result_
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), Applicant::class.java.getSimpleName()))
            }
        }

        @JvmStatic
        fun toProto(other: Applicant?): proto.Applicant {
            var result_: proto.Applicant.Builder = proto.Applicant.newBuilder()
            if (other != null) {
                var nameProto_: String = (if ((other as Applicant).name == null) "" else (other as Applicant).name!!)
                result_.setName(nameProto_)
                var ageProto_: Double = (if ((other as Applicant).age == null) 0.0 else (other as Applicant).age!!.toDouble())
                result_.setAge(ageProto_)
                var creditScoreProto_: Double = (if ((other as Applicant).creditScore == null) 0.0 else (other as Applicant).creditScore!!.toDouble())
                result_.setCreditScore(creditScoreProto_)
                var priorIssuesProto_: List<String?>? = (other as Applicant).priorIssues?.stream()?.map({e -> (if (e == null) "" else e!!)})?.collect(java.util.stream.Collectors.toList())
                if (priorIssuesProto_ != null) {
                    result_.addAllPriorIssues(priorIssuesProto_)
                }
            }
            return result_.build()
        }

        @JvmStatic
        fun toProto(other: List<Applicant?>?): List<proto.Applicant>? {
            if (other == null) {
                return null
            } else {
                return other.stream().map({o -> toProto(o)}).collect(java.util.stream.Collectors.toList())
            }
        }
    }
}
