package com.cooksys.ftd.assignments.day.two.objects;

public class Test {

	public static void main(String[] args) {
		
		IRational a = new Rational(-2,6);
		IRational b = new Rational(2,5);
		IRational c = new SimplifiedRational(-2, 6);
		IRational d = new SimplifiedRational(2, 5);
		IRational e = new Rational(2,-6);
		
		System.out.println(a);
		System.out.println(b);
		
		System.out.println(c);
		System.out.println(d);
		System.out.println("=========ADD========");
		System.out.println(a.add(b));
		System.out.println(c.add(d));
		System.out.println("=========SUB========");
		System.out.println(a.sub(b));
		System.out.println(c.sub(d));
		System.out.println("=========MUL========");
		System.out.println(a.mul(b));
		System.out.println(c.mul(d));
		System.out.println("=========DIV========");
		System.out.println(a.div(b));
		System.out.println(c.div(d));
		System.out.println("=========EQU========");
		System.out.println(a.equals(a));
		System.out.println(a.equals(c));
		System.out.println(a.equals(e));
	}
	
}
