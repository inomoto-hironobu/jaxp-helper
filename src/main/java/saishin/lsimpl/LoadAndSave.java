package saishin.lsimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

import saishin.MainHelper;

public class LoadAndSave implements DOMImplementation, DOMImplementationLS {


	@Override
	public LSParser createLSParser(short mode, String schemaType) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSSerializer createLSSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSInput createLSInput() {
		return new Input();
	}

	@Override
	public LSOutput createLSOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasFeature(String feature, String version) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getFeature(String feature, String version) {
		// TODO Auto-generated method stub
		return null;
	}
	
}