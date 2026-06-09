
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "removeall"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class RemoveallInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> blacklist;
    private List<String> names;

    public RemoveallInput_() {
    }

    public RemoveallInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object blacklist = input_.get("http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml#blacklist");
            setBlacklist((List<String>)blacklist);
            Object names = input_.get("http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml#names");
            setNames((List<String>)names);
        }
    }

    public List<String> getBlacklist() {
        return this.blacklist;
    }

    public void setBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

}
