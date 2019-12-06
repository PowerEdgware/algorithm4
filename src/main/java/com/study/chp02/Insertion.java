package com.study.chp02;

import edu.princeton.cs.algs4.StdIn;

public class Insertion extends BaseExample {

	public static void sort(Comparable[] a) {
		int l = a.length;
		for (int i = 1; i < l; i++) {
			int j = i;
			// a[i]分别比较[0,i) 如果小于a[i-1] 则交换位置直到j==0停止该流程.即把较小的元素排在a索引低位置
			while (j > 0 && less(a[j], a[j - 1])) {
				exche(a, j, --j);
			}
		}
	}

	public static void main(String[] args) {
		// String[] a = StdIn.readAllStrings();
		String a[] =args;
		show(a);

		sort(a);

		show(a);
	}

}
