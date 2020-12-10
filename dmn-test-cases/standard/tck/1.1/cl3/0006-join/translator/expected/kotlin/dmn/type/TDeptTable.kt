package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tDeptTable"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TDeptTableImpl::class)
interface TDeptTable : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("number")
    val number: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("name")
    val name: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("manager")
    val manager: String?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("number", this.number)
        context.put("name", this.name)
        context.put("manager", this.manager)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TDeptTable
        if (if (this.manager != null) this.manager != other.manager else other.manager != null) return false
        if (if (this.name != null) this.name != other.name else other.name != null) return false
        if (if (this.number != null) this.number != other.number else other.number != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.manager != null) this.manager.hashCode() else 0)
        result = 31 * result + (if (this.name != null) this.name.hashCode() else 0)
        result = 31 * result + (if (this.number != null) this.number.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("manager=" + manager)
        result_.append(", name=" + name)
        result_.append(", number=" + number)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTDeptTable(other: Any?): TDeptTable? {
            if (other == null) {
                return null
            } else if (other is TDeptTable?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TDeptTableImpl()
                result_.number = other.get("number") as java.math.BigDecimal?
                result_.name = other.get("name") as String?
                result_.manager = other.get("manager") as String?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTDeptTable(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TDeptTable::class.java.getSimpleName()))
            }
        }
    }
}
