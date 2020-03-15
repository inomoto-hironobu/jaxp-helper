package saishin;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.xml.sax.SAXException;

/**
 * JAXPを助けるユーティリティクラス
 */
public final class JAXPHelper {

	public static final DocumentBuilderFactory dbf = xercesDocumentBuilderFactory();
	public static final DocumentBuilder db = xercesDocumentBuilder();
	
	private JAXPHelper() {
	}

	public static DOMImplementation t() {

		DOMImplementationList ls;
		try {
			ls = DOMImplementationRegistry.newInstance().getDOMImplementationList("Traversal 2.0");
			System.out.println(ls.getLength());
			for (int i = 0; i < ls.getLength(); i++) {
				System.out.println(ls.item(i));
			}
			return null;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static SchemaFactory schemaFactroy(Path dest) {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setResourceResolver(new HelperResolver(dest));
		return factory; 
	}
	public static Optional<DOMImplementationRegistry> reg() {
		try {
			return Optional.of(DOMImplementationRegistry.newInstance());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	public static DOMImplementationLS ls() {
		DOMImplementationLS  tmp = null;
		tmp = (DOMImplementationLS) reg().orElse(null).getDOMImplementation("");
		try {
			tmp = (DOMImplementationLS) DOMImplementationRegistry.newInstance().getDOMImplementation("LS");
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			
		}
		return tmp;
	}
	public static DocumentBuilderFactory xercesDocumentBuilderFactory() {
		return new org.apache.xerces.jaxp.DocumentBuilderFactoryImpl();
	}
	public static DocumentBuilder xercesDocumentBuilder() {
		try {
			return JAXPHelper.dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static Document doc(String string) throws FileNotFoundException, SAXException, IOException {
		return JAXPHelper.db.parse(new FileInputStream(string));
	}

	/**
	 * closeしない
	 */
	public static void dump(OutputStream out, DocumentBuilderFactory factory) {
		String display = new Format()
				.apd("isCoalescing", factory.isCoalescing())
				.apd("isExpandEntityReferences", factory.isExpandEntityReferences())
				.apd("isIgnoringComments", factory.isIgnoringComments())
				.apd("isIgnoringElementContentWhitespace", factory.isIgnoringElementContentWhitespace())
				.apd("isNamespaceAware", factory.isNamespaceAware())
				.apd("isValidating", factory.isValidating())
				.apd("isXIncludeAware", factory.isXIncludeAware())
				.apd("getSchema", factory.getSchema())
				.apd("class", factory.getClass().getName()).ret().toString();
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.append(display);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void dump(OutputStream out, DOMImplementation impl) {
		String display = new Format()
		.apd("Core 1.0", impl.hasFeature("Core", "1.0"))
		.apd("Core 2.0", impl.hasFeature("Core", "2.0"))
		.apd("Core 3.0", impl.hasFeature("Core", "3.0"))
		.apd("XML 1.0", impl.hasFeature("XML", "1.0"))
		.apd("XML 2.0", impl.hasFeature("XML", "2.0"))
		.apd("XML 3.0", impl.hasFeature("XML", "3.0"))
		.apd("LS 1.0",impl.hasFeature("LS", "1.0"))
		.apd("LS 2.0",impl.hasFeature("LS", "2.0"))
		.apd("LS 3.0",impl.hasFeature("LS", "3.0"))
		.apd("Events 1.0", impl.hasFeature("Events", "1.0"))
		.apd("Events 2.0", impl.hasFeature("Events", "2.0"))
		.apd("Events 3.0", impl.hasFeature("Events", "3.0"))
		.apd("XPath", impl.hasFeature("XPath", "1.0"))
		.apd("XPath", impl.hasFeature("XPath", "2.0"))
		.apd("XPath", impl.hasFeature("XPath", "3.0"))
		.apd("Traversal 1.0", impl.hasFeature("Traversal", "1.0"))
		.apd("Traversal 2.0", impl.hasFeature("Traversal", "2.0"))
		.apd("Traversal 3.0", impl.hasFeature("Traversal", "3.0"))
		.apd("XMLVersion 1.0", impl.hasFeature("XMLVersion", "1.0"))
		.apd("XMLVersion 1.0", impl.hasFeature("XMLVersion", "1.1"))
		.ret()
		.toString();
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.append(display);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * closeしない
	 */
	public static void dump(OutputStream out, DocumentBuilder builder) {
		String display = new Format()
				.apd("isNamespaceAware", builder.isNamespaceAware())
				.apd("isValidating", builder.isValidating())
				.apd("isXIncludeAware", builder.isXIncludeAware())
				.apd("class", builder.getClass().getName())
				.ret().toString();
		DOMImplementation impl = builder.getDOMImplementation();
		dump(out, impl);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.append(display);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
