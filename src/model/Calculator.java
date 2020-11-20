package model;

public interface Calculator<T> {
	T add(T num1, T num2);
	T subtract(T num1, T num2);
	T multiply(T a, T b);
	T divide(T a, T b);
	T mod(T a, T b);
}
