package saishin.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

public final class HelperResolver implements EntityResolver2, LSResourceResolver {

	private final Path dest;
	DOMImplementationLS dls;

	public HelperResolver(Path dest) {
		try {
			dls = (DOMImplementationLS) DOMImplementationRegistry.newInstance().getDOMImplementation("LS");
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			
		}
		this.dest = dest;
	}

	@Override
	public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
		System.out.println("es");
		return resolveEntity(name, null, baseURI, null);
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		return resolveEntity(null, publicId, null, systemId);
	}

	@Override
	public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId)
			throws SAXException, IOException {
		InputSource is = new InputSource();
		LSInput input = resolveResource(null, null ,publicId, systemId, baseURI);
		is.setByteStream(input.getByteStream());
		is.setCharacterStream(input.getCharacterStream());
		is.setEncoding(input.getEncoding());
		is.setSystemId(systemId);
		is.setPublicId(publicId);
		return is;
	}

	@Override
	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI)  {
		try {
			LSInput lsInput = dls.createLSInput();
			lsInput.setEncoding("UTF-8");
			URI sys;
			if (baseURI == null) {
				sys = URI.create(systemId);
			} else {
				sys = URI.create(baseURI).resolve(systemId);
			}
			if (!Files.exists(dest)) {
				Files.createDirectory(dest);
			}
			Path file = null;
			if (sys.isAbsolute() && !sys.isOpaque()) {
				if(sys.getScheme().equals("file")) {
					file = Paths.get(sys);
				} else if(sys.getHost() != null) {
					Path host = dest.resolve(sys.getHost());
					if (!Files.exists(host)) {
						Files.createDirectory(host);
					}
					file = host.resolve(sys.getPath().substring(1));
					if (!Files.exists(file)) {
						URLConnection con = sys.toURL().openConnection();
						System.out.println(System.currentTimeMillis());
						Files.createDirectories(file.getParent());
						byte[] b = new byte[100000];
						BufferedInputStream in = new BufferedInputStream(con.getInputStream());
						BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(file));
						int i = 0;
						while ((i = in.read(b)) != -1) {
							out.write(b, 0, i);
						}
						System.out.println(System.currentTimeMillis());
						out.flush();
						out.close();
						in.close();
					}
				}
			} else {
				file = Paths.get(systemId);
				if(!Files.exists(file)) {
					URL url =  HelperResolver.class.getResource("/" + systemId);
					if(url != null) {
						sys = url.toURI();
						file = Paths.get(sys);
					}
				}
			}
			lsInput.setByteStream(Files.newInputStream(file));
			lsInput.setCharacterStream(Files.newBufferedReader(file, Charset.forName("UTF-8")));
			lsInput.setSystemId(sys.toString());
			lsInput.setBaseURI(sys.toString());
			lsInput.setPublicId(publicId);
			lsInput.setEncoding("UTF-8");
			lsInput.setCertifiedText(true);
			return lsInput;
		} catch (IOException | URISyntaxException | UnsupportedCharsetException e) {
			e.printStackTrace();
		}
		return null;
	}
}