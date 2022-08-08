import jdmn.runtime.discovery.ModelElementRegistry


class ModelElementRegistry(jdmn.runtime.discovery.ModelElementRegistry.ModelElementRegistry):
    def __init__(self):
        # Register elements from model '0020-vacation-days'
        self.register("Age", "Age.Age")
        self.register("'Base Vacation Days'", "BaseVacationDays.BaseVacationDays")
        self.register("'Extra days case 1'", "ExtraDaysCase1.ExtraDaysCase1")
        self.register("'Extra days case 2'", "ExtraDaysCase2.ExtraDaysCase2")
        self.register("'Extra days case 3'", "ExtraDaysCase3.ExtraDaysCase3")
        self.register("'Total Vacation Days'", "TotalVacationDays.TotalVacationDays")
        self.register("'Years of Service'", "YearsOfService.YearsOfService")
