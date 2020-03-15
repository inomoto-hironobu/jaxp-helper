package saishin;

public class Format {
	private StringBuilder buffer = new StringBuilder();
	public Format apd(String name, boolean val) {
		buffer.append(name).append(":\t[").append(val).append("]\n");
		return this;
	}
	public Format apd(String name, String val) {
		buffer.append(name).append(":\t[").append(val).append("]\n");
		return this;
	}
	public Format apd(String name, Object val) {
		buffer.append(name).append(":\t[").append(val).append("]\n");
		return this;
	}
	public StringBuilder ret() {
		return buffer;
	}
}
