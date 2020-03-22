package saishin;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Fdom {
	public static final class Bridge<T> {
		public Bridge(Node item, T t) {
			// TODO Auto-generated constructor stub
		}
		Node node;
		T value;
	}
	public static final Predicate<Node> ELEMENT_FILTER = (node) -> node.getNodeType() == Node.ELEMENT_NODE;
    public static final Predicate<Node> TEXT_FILTER = (node) -> node.getNodeType() == Node.TEXT_NODE;
	private Fdom() {
    }

    public static void test(Node node, Predicate<Node> filtrate, Consumer<?> consumer) {
    	if(filtrate.test(node)) {
    		
    	}
    }
    public static <T> void ifAttr(Node node, String name, Consumer<T> consumer, String value) {
    	if(node.getNodeType() == Node.ELEMENT_NODE) {
    		if(node.getAttributes().getNamedItem(name) != null) {
    			
    		}
    	}
    }
    public static <T> void ifAttr(Node node, String name, Consumer<T> consumer) {
    	ifAttr(node, name, consumer, null);
    }
    /**
     * もしnodeがElementであればpredicateを実行しその値を返す
     */
    public static boolean testElem(Node node, Predicate<Element> predicate) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return predicate.test((Element) node);
        }
        return false;
    }

    /**
     * @param <T>
     * @param target
     * @param finder
     * @param function
     * @param value
     */
    public static <T> void applyAncestor(Node target, Predicate<Bridge<T>> finder, Function<Bridge<T>, Optional<T>> function, Bridge<T> value) {
    	
    }

    /**
     * @param <T>
     * @param target
     * @param finder
     * @param function
     * @param value
     */
    public static <T> void applyAncestors(Node target, Predicate<Bridge<T>> finder, Function<Bridge<T>, Optional<T>> function, Bridge<T> value) {
    	
    }
	/**
	 * 
     * @param target
     * @param finder
     * @param function 実行関数
     * @param value 受け渡すオブジェクト
     */
    public static <T> Optional<T> applyDescendant(Node target, BiPredicate<Node, T> finder, BiFunction<Node, T, Optional<T>> function, T value) {
    	T result = null;
    	for (int i = 0; i < target.getChildNodes().getLength(); i++) {
        	Node node = target.getChildNodes().item(i);
        	if(finder.test(node, value)) {
        		result = function.apply(node, value).get();
        		break;
        	} else {
        		return applyDescendant(target.getChildNodes().item(i), finder, function, value);
        	}
        }
        return Optional.ofNullable(result);
    }
    
    /**
     * @param target 処理したいノード
     * @param filtrate 実行したい条件
     * @param function 実行関数
     * @param value 任意のオブジェクト
     */
    public static <T> Optional<T> applyDescendants(Node target, BiPredicate<Node, T> filtrate, BiFunction<Node, T, T> function, T value) {
        T result = null;
    	if (target.hasChildNodes()) {
            for (int i = 0; i < target.getChildNodes().getLength(); i++) {
                result = value;
                if (filtrate.test(target, value)) {
                	result = applyDescendants(target.getChildNodes().item(i), filtrate, function, result).get();
                }
            }
        }
		return Optional.ofNullable(result);
    }
}
