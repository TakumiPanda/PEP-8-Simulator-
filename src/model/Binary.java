package model;

import java.util.Objects;

/**
 * Binary object class that extends Number.
 * 
 * @author
 * @version 2.4
 *
 */
public class Binary extends Number {

	/**
	 * Parameter constructor that creates a Binary Object.
	 * 
	 * @param num String object that'll set to the Binary object.
	 */
	public Binary(String num) {
		super(num);
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
	@Override
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
		return Objects.equals(this, bin);
	}
}
