package saishin;

import static org.junit.jupiter.api.Assertions.*;
import static saishin.Util.doc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
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

import saishin.MainHelper;
import saishin.PrintHandler;
import saishin.impl.HelperResolver;

public class JaxpHelperTest {

	HelperResolver resolver;
	PrintHandler handler = new PrintHandler(System.out, System.err);
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Logger.getGlobal().addHandler(new ConsoleHandler());
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
		MainHelper.dump(System.out, f);
		try {
			DocumentBuilder b = f.newDocumentBuilder();
			MainHelper.dump(System.out, b);
			MainHelper.dump(System.out, b.getDOMImplementation());
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


}
