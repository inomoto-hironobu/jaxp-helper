package saishin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;

public class PrintHandler extends DefaultHandler2 {

	private PrintStream out;
	private PrintStream err;

	public PrintHandler(OutputStream out, OutputStream err) {
		this.out = new PrintStream(Objects.requireNonNull(out));
		this.err = new PrintStream(Objects.requireNonNull(err));
	}

	private void print(String f, Object... args) {
		out.printf(f, args);
	}

	@Override
	public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException {
		print("attributeDecl:%s%s%s%s", eName, aName, type, mode, value);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		print("charachters:%s", String.valueOf(ch, start, length));
	}

	@Override
	public void comment(char[] ch, int start, int length) throws SAXException {
		print("comment:%s\n", String.valueOf(ch, start, length));
	}

	@Override
	public void elementDecl(String name, String model) throws SAXException {
		print("", name, model);
	}

	@Override
	public void endCDATA() throws SAXException {
		print("endCDATA");
	}

	@Override
	public void endDocument() throws SAXException {
		out.println("endDocument");
	}

	@Override
	public void endDTD() throws SAXException {
		out.print("endDTD");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		print("endElement:%s;%s;%s\n", uri, localName, qName);
	}

	@Override
	public void endEntity(String name) throws SAXException {
		print("endEntity:%s", name);
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		print("", prefix);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		err.println(e);
	}

	@Override
	public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {
		print("", name, publicId, systemId);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		err.println(e);
	}

	@Override
	public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
		print("", name, baseURI);
		return super.getExternalSubset(name, baseURI);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		print("", start, length);
	}

	@Override
	public void internalEntityDecl(String name, String value) throws SAXException {
		print("", name, value);
	}

	@Override
	public void notationDecl(String name, String publicId, String systemId) throws SAXException {
		print("", name, publicId, systemId);
		super.notationDecl(name, publicId, systemId);
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		print("", target, data);
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		print("", publicId, systemId);
		return super.resolveEntity(publicId, systemId);
	}

	@Override
	public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId)
			throws SAXException, IOException {
		print("resolveEntity:%s %s %s %s\n", name, publicId, baseURI, systemId);
		return new InputSource(systemId);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		print("", locator);
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		print("", name);
	}

	@Override
	public void startCDATA() throws SAXException {
		print("startCDATA");
	}

	@Override
	public void startDocument() throws SAXException {
		out.println("startDocument");
	}

	@Override
	public void startDTD(String name, String publicId, String systemId) throws SAXException {
		print("startDTD:%s;%s;%s\n", name, publicId, systemId);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		print("startElement:%s;%s;%s;%s\n", uri, localName, qName, attributes.toString());
	}

	@Override
	public void startEntity(String name) throws SAXException {
		print("startEntity:%s", name);
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		print("startPrefixMapping:%s;%s\n", prefix, uri);
	}

	@Override
	public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
			throws SAXException {
		print("", name, publicId, systemId, notationName);
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		err.println(e);
	}
}
