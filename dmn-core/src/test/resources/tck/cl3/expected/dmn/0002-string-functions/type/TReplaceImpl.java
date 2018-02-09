package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tReplace"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TReplaceImpl implements TReplace {
        private String aao;
        private String aanplusStarstar;
        private String encloseVowels;

    public TReplaceImpl() {
    }

    public TReplaceImpl(String aanplusStarstar, String aao, String encloseVowels) {
        this.setAanplusStarstar(aanplusStarstar);
        this.setAao(aao);
        this.setEncloseVowels(encloseVowels);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("AanplusStarstar")
    public String getAanplusStarstar() {
        return this.aanplusStarstar;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("AanplusStarstar")
    public void setAanplusStarstar(String aanplusStarstar) {
        this.aanplusStarstar = aanplusStarstar;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Aao")
    public String getAao() {
        return this.aao;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Aao")
    public void setAao(String aao) {
        this.aao = aao;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("encloseVowels")
    public String getEncloseVowels() {
        return this.encloseVowels;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("encloseVowels")
    public void setEncloseVowels(String encloseVowels) {
        this.encloseVowels = encloseVowels;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
