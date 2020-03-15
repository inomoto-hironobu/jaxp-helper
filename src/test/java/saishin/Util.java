package saishin;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Util {
    public static Document doc(String target) throws RuntimeException {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src(target));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static InputStream src(String path) {
        return Util.class.getResourceAsStream(path);
    }
    public static InputStream xhtml() {
    	try {
			return new BufferedInputStream(new URL("https://www.w3.org/TR/xhtml1/").openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}