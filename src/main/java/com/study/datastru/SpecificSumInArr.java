package com.study.datastru;

/*
 * 判断数组中是否存在某些数字和等于给定的数字
 */
public class SpecificSumInArr {

	public static boolean rec_subset(int[]arr ,int i,int s) {
		//s==0说明已经找到和为s的数据
		if(s==0) return true;
		//i==0已经到达数组首元素
		if(i==0) return s==arr[i];
		
		//当前元素比s大，则只能继续下一次
		if(arr[i]>s) return rec_subset(arr, i-1, s);
		
		//针对i处元素，选择i，不选择i
		boolean c=rec_subset(arr, i-1, s-arr[i]);
		return c?c:rec_subset(arr, i-1, s);
	}
	
	public static void main(String[] args) {
		int[] arr= {1,2,4,1,7,8,3};
		
		boolean exist=rec_subset(arr, arr.length-1, 200);
		System.out.println(exist);
	}
}
