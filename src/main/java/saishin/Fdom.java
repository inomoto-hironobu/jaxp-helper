package saishin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Fdom {
    private Fdom() {
    }

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

    public static Optional<Element> pullElem(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return Optional.of((Element) node);
        }
        return Optional.empty();
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

    public static void consumeElem(Node node, Consumer<Element> consumer) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            consumer.accept((Element) node);
        }
    }

    public static <R> Optional<R> supplyElem(Node node, Supplier<R> supplier) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return Optional.of(supplier.get());
        }
        return Optional.empty();
    }

    public static <R> Optional<R> functionElem(Node node, Function<Element, R> function) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return Optional.of(function.apply((Element) node));
        }
        return Optional.empty();
    }

    public static void execElemChildren(Node node, Consumer<Element> consumer) {
        if (node.hasChildNodes()) {
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                if (node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                    consumer.accept((Element) node.getChildNodes().item(i));
                }
            }
        }
    }

    public static List<Element> querySelecter(Node node, String query) {
        return Collections.EMPTY_LIST;
    }

    public static List<Element> getElementsByTagName(final Node target, final String name) {
        ArrayList<Element> list = new ArrayList<>();
        acceptDescendants(target, node1 -> node1.getNodeName().equals(name),
                node2 -> {
                    list.add((Element) node2);
                }
                );
        return Collections.unmodifiableList(list);
    }
    public static void acceptDescendants(Node target, Predicate<Node> predicate,
                                         Consumer<Node> consumer) {
        if (predicate.test(target)) {
            consumer.accept(target);
        }
        if (target.hasChildNodes()) {
            for (int i = 0; i < target.getChildNodes().getLength(); i++) {
                acceptDescendants(target.getChildNodes().item(i), predicate, consumer);
            }
        }
    }

	/**
     * @param target
     * @param t 受け渡すオブジェクト
     * @param function 実行関数
     * @param <T>
     */
    public static <T> void applyDescendants(Node target, T t, BiFunction<Node, T, T> function) {
        T n = function.apply(target, t);
        if (target.hasChildNodes()) {
            for (int i = 0; i < target.getChildNodes().getLength(); i++) {
                applyDescendants(target.getChildNodes().item(i), n, function);
            }
        }
    }

    /**
     * @param target 処理したいノード
     * @param t 任意のオブジェクト
     * @param predicate 実行したい条件
     */
    public static <T> void applyDescendants(Node target, T t, Predicate<Node> predicate, BiFunction<Node, T, T> function) {
        T n = null;
        if (predicate.test(target)) {
            n = function.apply(target, t);
        }
        if (target.hasChildNodes()) {
            for (int i = 0; i < target.getChildNodes().getLength(); i++) {
                applyDescendants(target.getChildNodes().item(i), n, predicate, function);
            }
        }
    }
    /**
     * @param target
     * @param predicate
     * @param <T>
     * @return 特定の型のノード集合を返す
     */
    public static <T extends Node> Stream<T> stream(Node target, Predicate<Node> predicate) {
        Stream.Builder<T> builder = Stream.builder();
        acceptDescendants(target
                , node -> predicate.test(node)
                , node -> {
                    builder.add((T) node);
                });
        return builder.build();
    }

    public static Stream<Node> nodeStream(Node target, Predicate<Node> predicate) {
        Stream.Builder<Node> builder = Stream.builder();
        acceptDescendants(target
                , node -> predicate.test(node)
                , node -> {
                    builder.add(node);
                }
        );
        return builder.build();
    }
    public static List<Node> asList(final NodeList target) {
        Objects.nonNull(target);
        ArrayList<Node> temp = new ArrayList<>();
        for (int i = 0; i < target.getLength(); i++) {
            temp.add(target.item(i));
        }
        return Collections.unmodifiableList(temp);
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
}
