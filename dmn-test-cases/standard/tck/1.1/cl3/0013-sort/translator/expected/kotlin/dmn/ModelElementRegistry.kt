
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0013-sort'
        register("listA", "ListA")
        register("sort1", "Sort1")
        register("sort2", "Sort2")
        register("sort3", "Sort3")
        register("stringList", "StringList")
        register("tableB", "TableB")
    }
}
