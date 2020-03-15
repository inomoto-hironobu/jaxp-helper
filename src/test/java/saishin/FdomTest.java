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
        Fdom.applyDescendants(d.getDocumentElement()
        	, (n, r) -> false
        	, (n, r) -> {
	            System.out.printf("%s %s\n", n.getNodeName(), r);
	            if (n.getNodeType() == Node.ELEMENT_NODE) {
	                return ++r;
	            }
	            return r;
        	}
        	,i);
    }
    @Test
    public void testStream() {
        Document d = Util.doc("/dir.xml");
        MutableInt actual = new MutableInt(0);
        DomHelper
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
		Fdom.applyDescendants(test
				, null
				, (n, r) -> {
					
					if (DomHelper.pullAttrValue(n, "href").isPresent()) {
						count.addAndGet(1);
					}
					return null;
				}, null);
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);

		//
		count.set(0);
		begin = System.nanoTime();
		Fdom.applyDescendants(test
				, null
				, (n, r) -> {
					Fdom.ifAttr(n, "test", (node) -> {});
					return null;
				}, null);
		System.out.println(count);
		System.out.println(System.nanoTime() - begin);

		//
		count.set(0);
		begin = System.nanoTime();
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
	}
}