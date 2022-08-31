import typing
import decimal
import datetime
import time
import isodate

import type_.TRequestedProduct


# Generated(value = {"itemDefinition.ftl", "tRequestedProduct"})
class TRequestedProductImpl(type_.TRequestedProduct.TRequestedProduct):
    def __init__(self, amount: typing.Optional[decimal.Decimal] = None, productType: typing.Optional[str] = None, rate: typing.Optional[decimal.Decimal] = None, term: typing.Optional[decimal.Decimal] = None):
        type_.TRequestedProduct.TRequestedProduct.__init__(self)

        self.amount = amount
        self.productType = productType
        self.rate = rate
        self.term = term

    def __eq__(self, other: typing.Any) -> bool:
        return self.equalTo(other)

    def __hash__(self):
        return self.hashCode()

    def __str__(self) -> str:
        return self.asString()
