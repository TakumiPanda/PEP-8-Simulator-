package model;

public abstract class Calculator<T> {
	
	public abstract T add(T a, T b);
	public abstract T subtract(T a, T b);
	public abstract T multiply(T a, T b);
	public abstract T divide(T a, T b);
	public abstract T mod(T a, T b);
	
}
