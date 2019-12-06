package com.study.chp02;

import edu.princeton.cs.algs4.StdOut;

public class BaseExample {

	static boolean less(Comparable l, Comparable r) {
		return l.compareTo(r) < 0;
	}

	static void exche(Comparable[] a, int i, int j) {
		Comparable tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	static void show(Comparable a[]) {
		for (Comparable c : a) {
			StdOut.print(c+" ");
		}
		StdOut.println();
	}
}
