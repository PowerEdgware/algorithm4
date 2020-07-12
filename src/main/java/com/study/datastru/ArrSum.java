package com.study.datastru;

/**
 * 1.减治法
 * 2.分治法
 * @author all
 *
 */
public class ArrSum {

	
	static int decrease_sum(int arr[],int n) {
		if(n==0) return arr[0];
		
		return arr[n]+decrease_sum(arr, n-1);
	}
	
	static int divide_sum(int arr[],int lo,int hi) {
		if(lo==hi)return arr[lo];
		int mid=(lo+hi)>>1;
		return divide_sum(arr, lo, mid)+divide_sum(arr, mid+1, hi);
	}
	
	public static void main(String[] args) {
		int [] arr= {1,2,3,4,5};
		int sum=decrease_sum(arr, arr.length-1);
		System.out.println(sum);
		
		sum=divide_sum(arr, 0, arr.length-1);
		System.out.println(sum);
	}
}
