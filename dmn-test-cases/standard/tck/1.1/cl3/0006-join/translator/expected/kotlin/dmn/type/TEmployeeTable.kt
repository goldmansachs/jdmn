package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tEmployeeTable"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TEmployeeTableImpl::class)
interface TEmployeeTable : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("id")
    val id: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("name")
    val name: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("deptNum")
    val deptNum: kotlin.Number?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.add("id", this.id)
        context.add("name", this.name)
        context.add("deptNum", this.deptNum)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TEmployeeTable
        if (if (this.deptNum != null) this.deptNum != other.deptNum else other.deptNum != null) return false
        if (if (this.id != null) this.id != other.id else other.id != null) return false
        if (if (this.name != null) this.name != other.name else other.name != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.deptNum != null) this.deptNum.hashCode() else 0)
        result = 31 * result + (if (this.id != null) this.id.hashCode() else 0)
        result = 31 * result + (if (this.name != null) this.name.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("deptNum=" + deptNum)
        result_.append(", id=" + id)
        result_.append(", name=" + name)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTEmployeeTable(other: Any?): TEmployeeTable? {
            if (other == null) {
                return null
            } else if (other is TEmployeeTable?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TEmployeeTableImpl()
                var id = other.get("id")
                result_.id = id as String?
                var name = other.get("name")
                result_.name = name as String?
                var deptNum = other.get("deptNum")
                result_.deptNum = deptNum as kotlin.Number?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTEmployeeTable(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TEmployeeTable::class.java.getSimpleName()))
            }
        }
    }
}
