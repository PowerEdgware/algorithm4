package com.study.datastru;

import java.util.Arrays;

/**
 * 数组倒置，减治法
 * @author all
 *
 */
public class ArrReverse {

	public static void main(String[] args) {
		int [] arr= {1,2,3,4,5};
		
		reverse(arr, 0, arr.length-1);
		System.out.println(Arrays.toString(arr));
		
	}
	
	static void reverse(int[] arr,int lo,int hi) {
		if(lo<hi) {
			swap(arr, lo, hi);
			reverse(arr, lo+1, hi-1);//减治法
		}
	}
	static void swap(int[] arr,int i,int j) {
		int tmp=arr[i];
		arr[i]=arr[j];
		arr[j]=tmp;
	}
}
