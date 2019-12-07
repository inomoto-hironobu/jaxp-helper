package saishin;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static saishin.Util.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import saishin.Fdom;

public class FdomTest {

    @Test
    public void testApplyDescendants() {
        Document d = Util.doc("/dir.xml");
        Integer i = 1;
        Fdom.applyDescendants(d.getDocumentElement(), i, (n, r) -> {
            System.out.printf("%s %s\n", n.getNodeName(), r);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                return ++r;
            }
            return r;
        });
    }
    @Test
    public void testStream() {
        Document d = Util.doc("/dir.xml");
        MutableInt actual = new MutableInt(0);
        Fdom
                .nodeStream(d.getElementsByTagName("dir"))
                .forEach((e) -> {
                    System.out.println(e.getNodeName());
                    actual.add(1);
                });
        assertEquals(2, actual.intValue());
    }

	@Test
	public void testBase() {
		Object actual;
		Object expetcetd;
		
		AtomicInteger count = new AtomicInteger(0);
		Document test = doc("/test.xml");

		//
		long begin = System.nanoTime();
		Fdom.applyDescendants(test, null, (n, r) -> {
			if (Fdom.pullAttrValue(n, "href").isPresent()) {
				count.addAndGet(1);
			}
			return null;
		});
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);

		//
		count.set(0);
		begin = System.nanoTime();
		Fdom.applyDescendants(test, null, (n, r) -> {
			if (Fdom.pullAttrValue(n, "href").isPresent()) {
				count.addAndGet(1);
			}
			return null;
		});
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);

		//
		count.set(0);
		begin = System.nanoTime();
		Fdom.acceptDescendants(test, e -> {
			return Fdom.pullAttrValue(e, "href").isPresent();
		}, e -> {
			count.addAndGet(1);
		});
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);

		//
		count.set(0);
		begin = System.nanoTime();
		NodeList nl = test.getElementsByTagName("a");
		for (int i = 0; i < nl.getLength(); i++) {
			count.addAndGet(1);
		}
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);

		//
		count.set(0);
		begin = System.nanoTime();
		ArrayList<Element> es = new ArrayList<>(100);
		Fdom.acceptDescendants(test , n -> {
			return n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("a");
		}, n -> {
			es.add((Element) n);
		});
		es.forEach(e -> count.addAndGet(1));
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);

		//
		count.set(0);
		begin = System.nanoTime();
		Fdom.acceptDescendants(test, n -> {
			return n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("a");
		}, n -> {
			count.addAndGet(1);
		});
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);
	}

	@Test
	public void test() {
		Document test = doc("/test.xml");
		System.out.println(System.currentTimeMillis());

		System.out.println(System.currentTimeMillis());
		Fdom.pullElem(test.getDocumentElement()).ifPresent(e -> {
			System.out.println(e.getNodeName());
		});

		Fdom.consumeElem(test.getDocumentElement(), m -> {

		});
		System.out.println(System.currentTimeMillis());
		Fdom.stream(test, e -> e.hasAttributes()).forEach((e) -> {
			System.out.println("href:" + e.getNodeName());
		});
		System.out.println(System.currentTimeMillis());

		Stream<Element> stream = Fdom
				.stream(test, node -> node.getNodeType() == Node.ELEMENT_NODE);
		boolean actual = stream
				.anyMatch(element -> element.getNodeName().equals("p"));
		assertTrue(actual);
		assertEquals(10, stream.count());
	}

	@Test
	public void testQuerySelecter() {
		Document test2 = doc("/test2.xml");
		Fdom.querySelecter(test2, "#test");
	}
}