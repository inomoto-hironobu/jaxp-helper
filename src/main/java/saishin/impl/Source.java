package saishin.impl;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementationSource;

import saishin.lsimpl.LoadAndSave;

public final class Source implements DOMImplementationSource {

	private static final Source inst = new Source();
	public static Source instance() {
		return inst;
	} 
	@Override
	public DOMImplementation getDOMImplementation(String features) {
		if (features.equals("LS")) {
			return new LoadAndSave();
		}
		return null;
	}

	@Override
	public DOMImplementationList getDOMImplementationList(String features) {
		if (features.equals("LS")) {
			return List.inst;
		}
		return null;
	}

	public static class List implements DOMImplementationList {
		private static final List inst = new List();
		@Override
		public DOMImplementation item(int index) {
			if (index == 1) {
				return new LoadAndSave();
			}
			return null;
		}

		@Override
		public int getLength() {
			return 1;
		}
	}
}
