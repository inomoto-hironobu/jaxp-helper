package saishin.lsimpl;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSParserFilter;

public class Parser implements LSParser {

	@Override
	public DOMConfiguration getDomConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSParserFilter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilter(LSParserFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getBusy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Document parse(LSInput input) throws DOMException, LSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document parseURI(String uri) throws DOMException, LSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node parseWithContext(LSInput input, Node contextArg, short action) throws DOMException, LSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub

	}

}
