package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "generateOutputData"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.GenerateOutputDataImpl::class)
interface GenerateOutputData : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Decision")
    val decision: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Assessment")
    val assessment: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Issue")
    val issue: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("decision", this.decision)
        context.put("assessment", this.assessment)
        context.put("issue", this.issue)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as GenerateOutputData
        if (if (this.assessment != null) this.assessment != other.assessment else other.assessment != null) return false
        if (if (this.decision != null) this.decision != other.decision else other.decision != null) return false
        if (if (this.issue != null) this.issue != other.issue else other.issue != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.assessment != null) this.assessment.hashCode() else 0)
        result = 31 * result + (if (this.decision != null) this.decision.hashCode() else 0)
        result = 31 * result + (if (this.issue != null) this.issue.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Assessment=" + assessment)
        result_.append(", Decision=" + decision)
        result_.append(", Issue=" + issue)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toGenerateOutputData(other: Any?): GenerateOutputData? {
            if (other == null) {
                return null
            } else if (other is GenerateOutputData?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = GenerateOutputDataImpl()
                result_.decision = other.get("decision", "Decision") as String?
                result_.assessment = other.get("assessment", "Assessment") as java.math.BigDecimal?
                result_.issue = other.get("issue", "Issue") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toGenerateOutputData(other.toContext())
            } else if (other is proto.GenerateOutputData) {
                var result_: GenerateOutputDataImpl = GenerateOutputDataImpl()
                result_.decision = (other as proto.GenerateOutputData).getDecision()
                result_.assessment = java.math.BigDecimal.valueOf((other as proto.GenerateOutputData).getAssessment())
                result_.issue = java.math.BigDecimal.valueOf((other as proto.GenerateOutputData).getIssue())
                return result_
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), GenerateOutputData::class.java.getSimpleName()))
            }
        }

        @JvmStatic
        fun toProto(other: GenerateOutputData?): proto.GenerateOutputData {
            var result_: proto.GenerateOutputData.Builder = proto.GenerateOutputData.newBuilder()
            if (other != null) {
                var decisionProto_: String = (if ((other as GenerateOutputData).decision == null) "" else (other as GenerateOutputData).decision!!)
                result_.setDecision(decisionProto_)
                var assessmentProto_: Double = (if ((other as GenerateOutputData).assessment == null) 0.0 else (other as GenerateOutputData).assessment!!.toDouble())
                result_.setAssessment(assessmentProto_)
                var issueProto_: Double = (if ((other as GenerateOutputData).issue == null) 0.0 else (other as GenerateOutputData).issue!!.toDouble())
                result_.setIssue(issueProto_)
            }
            return result_.build()
        }

        @JvmStatic
        fun toProto(other: List<GenerateOutputData?>?): List<proto.GenerateOutputData>? {
            if (other == null) {
                return null
            } else {
                return other.stream().map({o -> toProto(o)}).collect(java.util.stream.Collectors.toList())
            }
        }
    }
}
