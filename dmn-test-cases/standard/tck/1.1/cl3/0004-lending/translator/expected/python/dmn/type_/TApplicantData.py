import typing
import decimal
import datetime
import time
import isodate

import jdmn.runtime.DMNType
import jdmn.runtime.Context
import jdmn.runtime.DMNRuntimeException


# Generated(value = {"itemDefinitionInterface.ftl", "tApplicantData"})
class TApplicantData(jdmn.runtime.DMNType.DMNType):
    def __init__(self):
        jdmn.runtime.DMNType.DMNType.__init__(self)

        self.monthly = None
        self.age = None
        self.existingCustomer = None
        self.maritalStatus = None
        self.employmentStatus = None

    def toTApplicantData(self, other: typing.Any) -> typing.Optional['TApplicantData']:
        if other is None:
            return None
        elif issubclass(type(other), TApplicantData):
            return other
        elif isinstance(other, jdmn.runtime.Context.Context):
            result_ = TApplicantData()
            result_.monthly = other.get("Monthly")
            result_.age = other.get("Age")
            result_.existingCustomer = other.get("ExistingCustomer")
            result_.maritalStatus = other.get("MaritalStatus")
            result_.employmentStatus = other.get("EmploymentStatus")
            return result_
        elif isinstance(other, jdmn.runtime.DMNType.DMNType):
            return self.toTApplicantData(other.toContext())
        else:
            raise jdmn.runtime.DMNRuntimeException.DMNRuntimeException("Cannot convert '{0}' to '{1}'".format(type(other), type(TApplicantData)))

    def toContext(self) -> jdmn.runtime.Context.Context:
        context = jdmn.runtime.Context.Context()
        context.put("monthly", self.monthly)
        context.put("age", self.age)
        context.put("existingCustomer", self.existingCustomer)
        context.put("maritalStatus", self.maritalStatus)
        context.put("employmentStatus", self.employmentStatus)
        return context

    def equalTo(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.age != other.age:
            return False
        if self.employmentStatus != other.employmentStatus:
            return False
        if self.existingCustomer != other.existingCustomer:
            return False
        if self.maritalStatus != other.maritalStatus:
            return False
        if self.monthly != other.monthly:
            return False

        return True

    def hashCode(self):
        result = 0
        result = 31 * result + (0 if self.age is None else hash(self.age))
        result = 31 * result + (0 if self.employmentStatus is None else hash(self.employmentStatus))
        result = 31 * result + (0 if self.existingCustomer is None else hash(self.existingCustomer))
        result = 31 * result + (0 if self.maritalStatus is None else hash(self.maritalStatus))
        result = 31 * result + (0 if self.monthly is None else hash(self.monthly))
        return result

    def asString(self) -> str:
        result_ = "{"
        result_ += ("Age=" + str(self.age))
        result_ += (", EmploymentStatus=" + str(self.employmentStatus))
        result_ += (", ExistingCustomer=" + str(self.existingCustomer))
        result_ += (", MaritalStatus=" + str(self.maritalStatus))
        result_ += (", Monthly=" + str(self.monthly))
        result_ += "}"
        return result_
