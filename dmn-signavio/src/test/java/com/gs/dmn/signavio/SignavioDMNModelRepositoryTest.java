package com.gs.dmn.signavio;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SignavioDMNModelRepositoryTest extends AbstractTest {
    private SignavioDMNModelRepository dmnModelRepository;
    private final DMNSerializer dmnSerializer = new XMLDMNSerializer(LOGGER, this.inputParameters);

    @BeforeEach
    public void setUp() {
        String pathName = "dmn/complex/Example credit decision.dmn";
        this.dmnModelRepository = readModels(pathName);
    }

    @Test
    void testGetDiagramId() {
        assertNull(this.dmnModelRepository.getDiagramId(null));
        TDRGElement decision = this.dmnModelRepository.findDRGElementByName("processPriorIssues");
        assertNotNull(decision);
        assertEquals("9acf44f2b05343d79fc35140c493c1e0", this.dmnModelRepository.getDiagramId(decision));
    }

    @Test
    void testGetShapeId() {
        assertNull(this.dmnModelRepository.getShapeId(null));
        TDRGElement decision = this.dmnModelRepository.findDRGElementByName("processPriorIssues");
        assertNotNull(decision);
        assertEquals("sid-F7FAA264-FA92-4952-A302-2BEADD9DCC59", this.dmnModelRepository.getShapeId(decision));
    }

    @Test
    void testGetGlossaryId() {
        assertNull(this.dmnModelRepository.getGlossaryId(null));
        TDefinitions model = getDefinitions(this.dmnModelRepository);
        assertNotNull(model);
        TItemDefinition itemDefinition = this.dmnModelRepository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, "creditIssueType"));
        assertNotNull(itemDefinition);
        assertEquals("3aa8101e7bad40dd87d293f33b5ae045", this.dmnModelRepository.getGlossaryId(itemDefinition));
    }

    @Override
    protected URI resource(String path) {
        return signavioResource(path);
    }

    private SignavioDMNModelRepository readModels(String pathName) {
        File input = new File(resource(pathName));
        List<TDefinitions> definitionsList = this.dmnSerializer.readModels(input);
        return new SignavioDMNModelRepository(definitionsList, SignavioTestConstants.SIG_EXT_NAMESPACE);
    }
}