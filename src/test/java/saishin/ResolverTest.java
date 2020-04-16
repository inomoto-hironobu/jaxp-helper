package saishin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import saishin.impl.HelperResolver;

class ResolverTest {
	HelperResolver resolver;
	PrintHandler handler = new PrintHandler(System.out, System.err);
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		Path dirfortest = Paths.get(System.getProperty("user.home"), "test/.entity");
		if(Files.exists(dirfortest)) {
			Files.walk(dirfortest).forEach(f -> {
				try {
					if(!Files.isDirectory(f)) Files.deleteIfExists(f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		Files.createDirectories(dirfortest);
		resolver = new HelperResolver(dirfortest);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		HelperResolver resolver = new HelperResolver(Paths.get(System.getProperty("java.tmpdir"), "test"));
		try {
			resolver.fromLocal(new URI("http://www.w3.org/TR/html4/frameset.dtd"));
		} catch (URISyntaxException e) {
			fail();
		}
	}

	@Test
	public void testResolver() {
		try {
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
	public void testDirxsd() {
		sf.setResourceResolver(resolver);
		sf.setErrorHandler(handler);
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setFeature("http://xml.org/sax/features/validation", false);
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setEntityResolver(resolver);
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
	@Test
	public void testDir2xml() {
		sf.setResourceResolver(resolver);
		sf.setErrorHandler(handler);
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setFeature("http://xml.org/sax/features/validation", false);
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setEntityResolver(resolver);
			reader.parse(new InputSource(
					new InputStreamReader(get("/dir2.xml"), "UTF-8")));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test2() {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		sf.setResourceResolver(resolver);
		sf.setErrorHandler(handler);
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setFeature("http://xml.org/sax/features/validation", false);
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setEntityResolver(resolver);
			SAXSource so = new SAXSource(
					reader,
					new InputSource(
							new InputStreamReader(get("/test2.xml"), "UTF-8")));
			sf.newSchema(so);
		} catch (SAXException | UnsupportedEncodingException e) {
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test3() {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		sf.setResourceResolver(resolver);
		sf.setErrorHandler(handler);
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setFeature("http://xml.org/sax/features/validation", false);
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setEntityResolver(resolver);
			SAXSource so = new SAXSource(
					reader,
					new InputSource(
							new InputStreamReader(get("/test3.xml"), "UTF-8")));
			sf.newSchema(so);
		} catch (SAXException | UnsupportedEncodingException e) {
			e.printStackTrace();
			fail();
		}
	}
	private BufferedInputStream get(String name) {
		return new BufferedInputStream(JaxpHelperTest.class.getResourceAsStream(name));
	}
}
