
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'child'
        register("https://kie.org/dmn/_E41422F9-9D1F-4BAD-B81A-0A28390DF4F7#ListChild", "child.ListChild");
        register("https://kie.org/dmn/_E41422F9-9D1F-4BAD-B81A-0A28390DF4F7#SimpleChild", "child.SimpleChild");
        // Register elements from model 'parent'
        register("https://kie.org/dmn/_41DA1B21-4EF2-42AC-8B8C-0EB93859BC91#ListParent", "parent.ListParent");
        register("https://kie.org/dmn/_41DA1B21-4EF2-42AC-8B8C-0EB93859BC91#SimpleParent", "parent.SimpleParent");
    }
}
