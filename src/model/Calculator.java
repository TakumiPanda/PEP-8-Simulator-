package model; public interface Calculator<T> {
	
	public T add(T num1, T num2);
	public T subtract(T num1, T num2);
	public T multiply(T a, T b);
	public T divide(T a, T b);
}
