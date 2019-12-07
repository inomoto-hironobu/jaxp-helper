package saishin;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.nio.file.Path;

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
}
