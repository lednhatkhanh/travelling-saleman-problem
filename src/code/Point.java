package code;

public class Point {
	private int value, penalty;
	private String name;

	public Point() {
		value = penalty = 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(int name) {
		this.name = Character.toString((char) (name + 64));
	}
}
