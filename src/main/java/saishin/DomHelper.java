package saishin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomHelper {

	/**
	 * NamedNodeMapを持つnodeで指定されたnameの属性を持っているならその値を返す
	 */
	public static Optional<String> pullAttrValue(Node node, String name) {
	    if (node.hasAttributes()) {
	        if (node.getAttributes().getNamedItem(name) != null) {
	            return Optional.of(node.getAttributes().getNamedItem(name).getNodeValue());
	        }
	    }
	    return Optional.empty();
	}

	/**
	 * NamedNodeMapを持つnodeで指定されたnameの属性を持っているならその値を返す
	 */
	public static Optional<String> pullAttrValue(Node node, String name, String namespace) {
	    if (node.hasAttributes()) {
	        if (node.getAttributes().getNamedItem(name) != null) {
	            return Optional.of(node.getAttributes().getNamedItemNS(namespace, name).getNodeValue());
	        }
	    }
	    return Optional.empty();
	}

	public static <R> Optional<R> ifPresentAttr(Node node, String name, Supplier<R> function) {
		if (node.hasAttributes()) {
	        if (node.getAttributes().getNamedItem(name) != null) {
	            return Optional.ofNullable(function.get());
	        }
	    }
		return Optional.empty();
	}
	public static Optional<Element> pullElem(Node node) {
	    if (node.getNodeType() == Node.ELEMENT_NODE) {
	        return Optional.of((Element) node);
	    }
	    return Optional.empty();
	}

	public static List<Element> getElementsAsList(final Node target, final String name) {
	    ArrayList<Element> list = new ArrayList<>();
	    Fdom.applyDescendants(target
	    		, (node, v1) -> node.getNodeName().equals(name)
	    		, (node, v2) -> {
	                list.add((Element) node);
	                return list;
	            }
	    		,(List<Element>) list);
	    return Collections.unmodifiableList(list);
	}

	public static Stream<Node> nodeStream(final NodeList target) {
	    Objects.nonNull(target);
	    Stream.Builder<Node> builder = Stream.builder();
	    target.getLength();
	    for (int i = 0; i < target.getLength(); i++) {
	        builder.add(target.item(i));
	    }
	    return builder.build();
	}

	public static List<Element> querySelecter(Node node, String query) {
		// TODO Auto-generated method stub
	    return Collections.EMPTY_LIST;
	}
	public static Stream<Element> stream(Node node, Predicate<Node> filtrate) {
		// TODO Auto-generated method stub
	    return null;
	}
	public static Stream<Element> getElements(Node test, String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
