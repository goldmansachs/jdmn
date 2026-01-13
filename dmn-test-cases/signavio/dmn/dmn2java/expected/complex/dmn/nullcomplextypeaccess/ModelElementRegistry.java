
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'TestNullComplexTypeAccess'
        register("http://www.provider.com/dmn/1.1/diagram/7f0dd69d49504172be8e6e3c23d8ed63.xml#incompleteDecisionTable", "IncompleteDecisionTable");
        register("http://www.provider.com/dmn/1.1/diagram/7f0dd69d49504172be8e6e3c23d8ed63.xml#testNullComplexTypeAccess", "TestNullComplexTypeAccess");
    }
}
