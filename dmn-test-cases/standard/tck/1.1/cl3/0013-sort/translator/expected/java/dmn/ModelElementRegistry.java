
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'sort'
        register("listA", "ListA");
        register("sort1", "Sort1");
        register("sort2", "Sort2");
        register("sort3", "Sort3");
        register("stringList", "StringList");
        register("tableB", "TableB");
    }
}
