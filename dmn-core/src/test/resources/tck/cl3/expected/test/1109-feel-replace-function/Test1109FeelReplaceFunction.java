
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1109-feel-replace-function.dmn"})
public class Test1109FeelReplaceFunction extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001_7637e5a8ed() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_001_7637e5a8ed
        String feelReplaceFunction_001_7637e5a8edOutput = new FeelReplaceFunction_001_7637e5a8ed().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("[1=ab][2=]cd", feelReplaceFunction_001_7637e5a8edOutput);
    }

    @org.junit.Test
    public void testCase002_b5c242ccd4() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_002_b5c242ccd4
        String feelReplaceFunction_002_b5c242ccd4Output = new FeelReplaceFunction_002_b5c242ccd4().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("a", feelReplaceFunction_002_b5c242ccd4Output);
    }

    @org.junit.Test
    public void testCase003_bf7aa95050() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_003_bf7aa95050
        String feelReplaceFunction_003_bf7aa95050Output = new FeelReplaceFunction_003_bf7aa95050().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("#", feelReplaceFunction_003_bf7aa95050Output);
    }

    @org.junit.Test
    public void testCase004_55a2186006() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_004_55a2186006
        String feelReplaceFunction_004_55a2186006Output = new FeelReplaceFunction_004_55a2186006().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelReplaceFunction_004_55a2186006Output);
    }

    @org.junit.Test
    public void testCase005_271d93aa68() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_005_271d93aa68
        String feelReplaceFunction_005_271d93aa68Output = new FeelReplaceFunction_005_271d93aa68().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelReplaceFunction_005_271d93aa68Output);
    }

    @org.junit.Test
    public void testCase006_9cd005d2e2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_006_9cd005d2e2
        String feelReplaceFunction_006_9cd005d2e2Output = new FeelReplaceFunction_006_9cd005d2e2().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("#ar", feelReplaceFunction_006_9cd005d2e2Output);
    }

    @org.junit.Test
    public void testCase007_91583e38c9() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_007_91583e38c9
        String feelReplaceFunction_007_91583e38c9Output = new FeelReplaceFunction_007_91583e38c9().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelReplaceFunction_007_91583e38c9Output);
    }

    @org.junit.Test
    public void testCase008_8c7c3871f8() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_008_8c7c3871f8
        String feelReplaceFunction_008_8c7c3871f8Output = new FeelReplaceFunction_008_8c7c3871f8().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("a*cada*", feelReplaceFunction_008_8c7c3871f8Output);
    }

    @org.junit.Test
    public void testCase009_b1e4220bc9() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_009_b1e4220bc9
        String feelReplaceFunction_009_b1e4220bc9Output = new FeelReplaceFunction_009_b1e4220bc9().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("*", feelReplaceFunction_009_b1e4220bc9Output);
    }

    @org.junit.Test
    public void testCase010_cd4e7a6d9f() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_010_cd4e7a6d9f
        String feelReplaceFunction_010_cd4e7a6d9fOutput = new FeelReplaceFunction_010_cd4e7a6d9f().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("*c*bra", feelReplaceFunction_010_cd4e7a6d9fOutput);
    }

    @org.junit.Test
    public void testCase011_c310665f57() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_011_c310665f57
        String feelReplaceFunction_011_c310665f57Output = new FeelReplaceFunction_011_c310665f57().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("brcdbr", feelReplaceFunction_011_c310665f57Output);
    }

    @org.junit.Test
    public void testCase012_b0cf9e6723() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_012_b0cf9e6723
        String feelReplaceFunction_012_b0cf9e6723Output = new FeelReplaceFunction_012_b0cf9e6723().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abbraccaddabbra", feelReplaceFunction_012_b0cf9e6723Output);
    }

    @org.junit.Test
    public void testCase013_f669d03fa9() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_013_f669d03fa9
        String feelReplaceFunction_013_f669d03fa9Output = new FeelReplaceFunction_013_f669d03fa9().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("b", feelReplaceFunction_013_f669d03fa9Output);
    }

    @org.junit.Test
    public void testCase014_cea33baeee() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_014_cea33baeee
        String feelReplaceFunction_014_cea33baeeeOutput = new FeelReplaceFunction_014_cea33baeee().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("bbbb", feelReplaceFunction_014_cea33baeeeOutput);
    }

    @org.junit.Test
    public void testCase015_57ce78ec8a() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_015_57ce78ec8a
        String feelReplaceFunction_015_57ce78ec8aOutput = new FeelReplaceFunction_015_57ce78ec8a().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("carted", feelReplaceFunction_015_57ce78ec8aOutput);
    }

    @org.junit.Test
    public void testCase016_1c38095f50() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_016_1c38095f50
        String feelReplaceFunction_016_1c38095f50Output = new FeelReplaceFunction_016_1c38095f50().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("Xant", feelReplaceFunction_016_1c38095f50Output);
    }

    @org.junit.Test
    public void testCase017_b9c3c03b87() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_017_b9c3c03b87
        String feelReplaceFunction_017_b9c3c03b87Output = new FeelReplaceFunction_017_b9c3c03b87().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("(012) 345-6789", feelReplaceFunction_017_b9c3c03b87Output);
    }

    @org.junit.Test
    public void testCase018_aba3349043() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_018_aba3349043
        String feelReplaceFunction_018_aba3349043Output = new FeelReplaceFunction_018_aba3349043().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("facet[i][o][u]sl[y]", feelReplaceFunction_018_aba3349043Output);
    }

    @org.junit.Test
    public void testCase019_6ef91033ad() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_019_6ef91033ad
        String feelReplaceFunction_019_6ef91033adOutput = new FeelReplaceFunction_019_6ef91033ad().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("###", feelReplaceFunction_019_6ef91033adOutput);
    }

    @org.junit.Test
    public void testCase020_52d93a8851() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_020_52d93a8851
        String feelReplaceFunction_020_52d93a8851Output = new FeelReplaceFunction_020_52d93a8851().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("#.#.#.", feelReplaceFunction_020_52d93a8851Output);
    }

    @org.junit.Test
    public void testCase021_e33828e3da() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_021_e33828e3da
        String feelReplaceFunction_021_e33828e3daOutput = new FeelReplaceFunction_021_e33828e3da().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("###", feelReplaceFunction_021_e33828e3daOutput);
    }

    @org.junit.Test
    public void testCase022_bd75fac0bd() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_022_bd75fac0bd
        String feelReplaceFunction_022_bd75fac0bdOutput = new FeelReplaceFunction_022_bd75fac0bd().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("###", feelReplaceFunction_022_bd75fac0bdOutput);
    }

    @org.junit.Test
    public void testCase023_5c337d3725() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_023_5c337d3725
        String feelReplaceFunction_023_5c337d3725Output = new FeelReplaceFunction_023_5c337d3725().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("# # # # ", feelReplaceFunction_023_5c337d3725Output);
    }

    @org.junit.Test
    public void testCase024_4a89220cd6() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_024_4a89220cd6
        String feelReplaceFunction_024_4a89220cd6Output = new FeelReplaceFunction_024_4a89220cd6().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelReplaceFunction_024_4a89220cd6Output);
    }

    @org.junit.Test
    public void testCase025_b7f9525875() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_025_b7f9525875
        String feelReplaceFunction_025_b7f9525875Output = new FeelReplaceFunction_025_b7f9525875().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("###", feelReplaceFunction_025_b7f9525875Output);
    }

    @org.junit.Test
    public void testCase026_acb176590a() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_026_acb176590a
        String feelReplaceFunction_026_acb176590aOutput = new FeelReplaceFunction_026_acb176590a().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelReplaceFunction_026_acb176590aOutput);
    }

    @org.junit.Test
    public void testCase027_d8d25f40e5() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_027_d8d25f40e5
        String feelReplaceFunction_027_d8d25f40e5Output = new FeelReplaceFunction_027_d8d25f40e5().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("###", feelReplaceFunction_027_d8d25f40e5Output);
    }

    @org.junit.Test
    public void testCase028_96e8c698af() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelReplaceFunction_028_96e8c698af
        String feelReplaceFunction_028_96e8c698afOutput = new FeelReplaceFunction_028_96e8c698af().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelReplaceFunction_028_96e8c698afOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
