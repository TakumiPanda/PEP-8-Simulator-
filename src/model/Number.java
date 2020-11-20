package model;

/**
 * 
 * 
 * @author
 * @version 2.4
 *
 */
public class Number {

	/**
	 * number is the String variable of the Number class.
	 */
	private String number;
	
	/**
	 * Parameterized constructor. Constructs a Number object.
	 *
	 * @param num String that'll be the variable number's value.
	 */
	public Number(String num) {
		this.number = num;
	}

	/**
	 * Compare function. Compares two strings and returns:
	 * 0 if first String is equal to the second String.
	 * -1 if first String is less than the second String.
	 * 1 if first String is greater than the second String.
	 * 
	 * @param in First String to be compared.
	 * @param compare Second String to be compared.
	 * @return int.
	 */
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

	/**
	 * Equality function. Checks the object o to be:
	 * the same value as o.
	 * the value isn't null.
	 * the same class as o.
	 *
	 * @param o Object to be compared.
	 * @return boolean false if equal to null, different class,
	 * or not equals to number. Else, true.
	 */
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
		Number bin = (Number) o;
		return bin.number.equals(this.number);
	}

	/**
	 * Sets variable to be the new value that is 
	 * set in the parameter.
	 * 
	 * @param newNum New value for variable.
	 */
	public void setNumber(String newNum){
		this.number = newNum;
	}

	/**
	 * Returns the variable.
	 * 
	 * @return The variable number as a String.
	 */
	public String getNumber() {
		return number;
	}
}
