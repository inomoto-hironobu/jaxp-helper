package saishin.lsimpl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import org.w3c.dom.ls.LSInput;

class Input implements LSInput {

	private Reader reader;
	private InputStream input;
	private String encoding = "UTF-8";
	private String systemId;
	private boolean certifiedText;
	
	@Override
	public Reader getCharacterStream() {
		return reader;
	}

	@Override
	public void setCharacterStream(Reader characterStream) {
		reader = characterStream;
	}

	@Override
	public InputStream getByteStream() {
		return input;
	}

	@Override
	public void setByteStream(InputStream byteStream) {
		input = byteStream;
		try {
			reader = new InputStreamReader(input, encoding);
		} catch (UnsupportedEncodingException e) {
			reader = new InputStreamReader(input);
			e.printStackTrace();
		}
	}

	@Override
	public String getStringData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStringData(String stringData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSystemId() {
		return systemId;
	}

	@Override
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	public String getPublicId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPublicId(String publicId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBaseURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBaseURI(String baseURI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEncoding() {
		return encoding;
	}

	@Override
	public void setEncoding(String encoding) {
		this.encoding = Objects.requireNonNull(encoding);
	}

	@Override
	public boolean getCertifiedText() {
		return certifiedText;
	}

	@Override
	public void setCertifiedText(boolean certifiedText) {
		this.certifiedText = certifiedText;
	}
}