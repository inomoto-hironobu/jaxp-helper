package saishin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

class DomHelperTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testQuerySelecter() {
		Document test2;
		try {
			test2 = JAXPHelper.doc("/test2.xml");
			DomHelper.querySelecter(test2, "#test");
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			fail(e);
		}
	}

	@Test
	public void test() {
		Document test;
		try {
			test = JAXPHelper.doc("/test.xml");
			System.out.println(System.currentTimeMillis());

			System.out.println(System.currentTimeMillis());
			DomHelper.pullElem(test.getDocumentElement()).ifPresent(e -> {
				System.out.println(e.getNodeName());
			});

			System.out.println(System.currentTimeMillis());
			DomHelper.getElements(test, "");
			System.out.println(System.currentTimeMillis());

			Stream<Element> stream = DomHelper
					.stream(test, node -> node.getNodeType() == Node.ELEMENT_NODE);
			boolean actual = stream
					.anyMatch(element -> element.getNodeName().equals("p"));
			assertTrue(actual);
			assertEquals(10, stream.count());
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e);
		}
		
	}
}
