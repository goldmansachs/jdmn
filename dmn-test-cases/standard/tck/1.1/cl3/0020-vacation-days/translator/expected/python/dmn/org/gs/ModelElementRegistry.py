import jdmn.runtime.discovery.ModelElementRegistry


class ModelElementRegistry(jdmn.runtime.discovery.ModelElementRegistry.ModelElementRegistry):
    def __init__(self):
        # Register elements from model '0020-vacation-days'
        self.register("Age", "org.gs.Age.Age")
        self.register("Base Vacation Days", "org.gs.BaseVacationDays.BaseVacationDays")
        self.register("Extra days case 1", "org.gs.ExtraDaysCase1.ExtraDaysCase1")
        self.register("Extra days case 2", "org.gs.ExtraDaysCase2.ExtraDaysCase2")
        self.register("Extra days case 3", "org.gs.ExtraDaysCase3.ExtraDaysCase3")
        self.register("Total Vacation Days", "org.gs.TotalVacationDays.TotalVacationDays")
        self.register("Years of Service", "org.gs.YearsOfService.YearsOfService")
