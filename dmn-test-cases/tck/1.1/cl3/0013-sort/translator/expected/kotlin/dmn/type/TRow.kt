package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tRow"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TRowImpl::class)
interface TRow : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("col1")
    val col1: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("col2")
    val col2: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("col3")
    val col3: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("col4")
    val col4: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("col1", this.col1)
        context.put("col2", this.col2)
        context.put("col3", this.col3)
        context.put("col4", this.col4)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TRow
        if (if (this.col1 != null) this.col1 != other.col1 else other.col1 != null) return false
        if (if (this.col2 != null) this.col2 != other.col2 else other.col2 != null) return false
        if (if (this.col3 != null) this.col3 != other.col3 else other.col3 != null) return false
        if (if (this.col4 != null) this.col4 != other.col4 else other.col4 != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.col1 != null) this.col1.hashCode() else 0)
        result = 31 * result + (if (this.col2 != null) this.col2.hashCode() else 0)
        result = 31 * result + (if (this.col3 != null) this.col3.hashCode() else 0)
        result = 31 * result + (if (this.col4 != null) this.col4.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("col1=" + col1)
        result_.append(", col2=" + col2)
        result_.append(", col3=" + col3)
        result_.append(", col4=" + col4)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTRow(other: Any?): TRow? {
            if (other == null) {
                return null
            } else if (other is TRow?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TRowImpl()
                result_.col1 = other.get("col1") as java.math.BigDecimal?
                result_.col2 = other.get("col2") as java.math.BigDecimal?
                result_.col3 = other.get("col3") as java.math.BigDecimal?
                result_.col4 = other.get("col4") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTRow(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TRow::class.java.getSimpleName()))
            }
        }
    }
}
