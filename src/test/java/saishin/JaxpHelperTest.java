package saishin;

import static org.junit.jupiter.api.Assertions.*;
import static saishin.Util.doc;

import java.io.*;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import saishin.HelperResolver;
import saishin.JAXPHelper;
import saishin.PrintHandler;

public class JaxpHelperTest {

	HelperResolver resolver = new HelperResolver(Paths.get(System.getProperty("user.home"), ".entity"));
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

	private Document doc(InputStream in) {
		try {
			
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			f.setNamespaceAware(true);
			f.setValidating(true);
			DocumentBuilder b = f.newDocumentBuilder();
			b.setEntityResolver(resolver);
			return b.parse(in);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void testSax() {
		try {
			System.out.println(System.currentTimeMillis());
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			System.out.println(System.currentTimeMillis());
			parser.parse(get("/dir.xml"), new DefaultHandler());
			System.out.println(System.currentTimeMillis());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDump() {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		JAXPHelper.dump(System.out, f);
		try {
			DocumentBuilder b = f.newDocumentBuilder();
			JAXPHelper.dump(System.out, b);
			JAXPHelper.dump(System.out, b.getDOMImplementation());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e);
		}
	}
	@Test
	public void testHandler() {
		SAXParser parser;
		try {
			SAXParserFactory f = SAXParserFactory.newInstance();
			parser = SAXParserFactory.newInstance().newSAXParser();
			PrintHandler handler = new PrintHandler(System.out, System.err);
			
			parser.parse(get("/test2.xml"), handler);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("");
		}
		System.out.println(System.currentTimeMillis());
		
	}
	private BufferedInputStream get(String name) {
		return new BufferedInputStream(JaxpHelperTest.class.getResourceAsStream(name));
	}


	@Test
	public void testResolver() {
		try {
			HelperResolver resolver = new HelperResolver(Paths.get(System.getProperty("user.home"), ".entity"));
			resolver.resolveEntity("-//W3C//DTD XHTML 1.0 Strict//EN",
					"https://www.w3.org/Graphics/SVG/1.2/rng/Tiny-1.2/Tiny-1.2.rng");
			resolver.resolveEntity(""
					, "-//W3C//DTD XHTML 1.0 Strict//EN"
					, "https://www.w3.org/Graphics/SVG/1.2/rng/Tiny-1.2/Tiny-1.2.rng"
					, "https://www.w3.org/Graphics/SVG/1.2/rng/Tiny-1.2/Tiny-1.2.rng");

		} catch (SAXException | IOException e) {
			fail(e);
		}

	}
	@Test
	public void testResolverSchema() {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		PrintHandler handler = new PrintHandler(System.out, System.err);
		
		sf.setResourceResolver(resolver);
		sf.setErrorHandler(handler);
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setFeature("http://xml.org/sax/features/validation", false);
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setEntityResolver(resolver);
			reader.setErrorHandler(handler);
			reader.setContentHandler(handler);
			reader.setDTDHandler(handler);
			SAXSource so = new SAXSource(
					reader,
					new InputSource(
							new InputStreamReader(get("/dir.xsd"), "UTF-8")));
			sf.newSchema(so);
		} catch (SAXException | UnsupportedEncodingException e) {
			e.printStackTrace();
			fail();
		}
		
	}
}
