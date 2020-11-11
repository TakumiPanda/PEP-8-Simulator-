package model;

import java.util.Objects;

public abstract class Number {

	private String number;

	public Number(String num) {
		this.number = num;
	}

	public abstract String get(String in, int i);

	public int compare(String in, String compare) {
		int compare1 = Integer.parseInt(in);
		int compare2 = Integer.parseInt(compare);
		if (compare1 == compare2)
		{
			return 0;
		}
		else if (compare1 < compare2)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}

	public boolean equality(Object o) {
		if (this == o)
		{
			return true;
		}
		if (o == null)
		{
			return false;
		}
		if (getClass() != o.getClass())
		{
			return false;
		}
		Binary bin = (Binary) o;
		return Objects.equals(number, bin.getNumber());
	}

	public void setNumber(String newNum){
		this.number = newNum;
	}

	public String getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return number;
	}
}
