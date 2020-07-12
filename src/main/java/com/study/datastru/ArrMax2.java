package com.study.datastru;

/**
 * 数组中最大和次大元素
 * @author all
 *
 */
public class ArrMax2 {

	static void max2(int[] arr) {
		int x1=0,x2=1;
		if(arr[x1]<arr[x2]) {
			x1=x1^x2;
			x2=x1^x2;
			x1=x2^x1;
		}
		for(int i=2;i<arr.length;i++) {
			if(arr[x2]<arr[i])
				if(arr[x1]<arr[x2=i]) {
					x1=x1^x2;
					x2=x1^x2;
					x1=x2^x1;
				}
		}
		System.out.println(arr[x1]+"\t"+arr[x2]);
	}
	
	static void swap(int x1,int x2) {
		System.out.println(x1+"\t"+x2);
		x1=x1^x2;
		x2=x1^x2;
		x1=x2^x1;
		System.out.println(x1+"\t"+x2);
	}
	public static void main(String[] args) {
		swap(1, 3);
		
		int [] arr= {1,9,5,4,5};
		
		max2(arr);
	}
}
