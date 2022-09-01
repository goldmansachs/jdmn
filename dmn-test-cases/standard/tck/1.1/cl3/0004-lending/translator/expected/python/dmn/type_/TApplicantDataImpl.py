import typing
import decimal
import datetime
import time
import isodate

import type_.TApplicantData
import type_.Monthly


# Generated(value = {"itemDefinition.ftl", "tApplicantData"})
class TApplicantDataImpl(type_.TApplicantData.TApplicantData):
    def __init__(self, age: typing.Optional[decimal.Decimal] = None, employmentStatus: typing.Optional[str] = None, existingCustomer: typing.Optional[bool] = None, maritalStatus: typing.Optional[str] = None, monthly: typing.Optional[type_.Monthly.Monthly] = None):
        type_.TApplicantData.TApplicantData.__init__(self)

        self.age = age
        self.employmentStatus = employmentStatus
        self.existingCustomer = existingCustomer
        self.maritalStatus = maritalStatus
        self.monthly = monthly

    def __eq__(self, other: typing.Any) -> bool:
        return self.equalTo(other)

    def __hash__(self):
        return self.hashCode()

    def __str__(self) -> str:
        return self.asString()
