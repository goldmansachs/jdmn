
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bigMid"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BigMidInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TestPeopleType testPeopleType;

    public BigMidInput_() {
    }

    public BigMidInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object testPeopleType = input_.get("TestPeopleType");
            setTestPeopleType(type.TestPeopleType.toTestPeopleType(null));
        }
    }

    public type.TestPeopleType getTestPeopleType() {
        return this.testPeopleType;
    }

    public void setTestPeopleType(type.TestPeopleType testPeopleType) {
        this.testPeopleType = testPeopleType;
    }

}
