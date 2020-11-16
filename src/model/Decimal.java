package model;

import java.util.Objects;

/**
 * Decimal object class that extends Number.
 * 
 * @author 
 * @version 2.4 
 *
 */
public class Decimal extends Number {

	/**
	 * Parameter constructor that creates a Decimal Object.
	 * 
	 * @param num String object that'll set to the Decimal object.
	 */
	public Decimal(String num)
	{
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
		Decimal dec = (Decimal) o;
		return Objects.equals(this, dec);
	}
}
