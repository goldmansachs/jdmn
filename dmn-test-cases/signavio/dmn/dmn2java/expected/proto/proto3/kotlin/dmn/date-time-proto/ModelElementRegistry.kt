
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model 'date-time-proto'
        register("CompositeDateTime", "CompositeDateTime")
        register("CompositeInputDateTime", "CompositeInputDateTime")
        register("Date", "Date")
        register("DateTime", "DateTime")
        register("InputDate", "InputDate")
        register("InputDateTime", "InputDateTime")
        register("InputTime", "InputTime")
        register("Time", "Time")
    }
}
