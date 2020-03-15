package saishin.lsimpl;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;

public class Serializer implements LSSerializer {

	@Override
	public DOMConfiguration getDomConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNewLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNewLine(String newLine) {
		// TODO Auto-generated method stub

	}

	@Override
	public LSSerializerFilter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilter(LSSerializerFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean write(Node nodeArg, LSOutput destination) throws LSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean writeToURI(Node nodeArg, String uri) throws LSException {
		URI target = URI.create(uri);
		try {
			Files.newBufferedWriter(Paths.get(target));
		} catch (IOException e) {
			throw new LSException((short) 0L, e.getMessage());
		}
		return false;
	}

	@Override
	public String writeToString(Node nodeArg) throws DOMException, LSException {
		// TODO Auto-generated method stub
		return null;
	}

}
