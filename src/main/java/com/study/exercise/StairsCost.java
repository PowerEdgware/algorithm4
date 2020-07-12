package com.study.exercise;

import java.util.Arrays;

/**
 * 走楼梯，到达指楼梯处，花费体力最小
 * @author shangcj
 *
 */
public class StairsCost {

	/**
	 * 不能递归实现
	 * @param cost
	 * @param i
	 * @return
	 */
	public static int rec_cost(int[] cost,int i) {
		if(i<0) return 0;
		if(i==0) return cost[0];
		if(i==1) return Math.min(cost[0], cost[1]);
		
		int cost_a=rec_cost(cost,i-1)+cost[i];//从i-1处跨1步上来
		int cost_b=rec_cost(cost, i-2)+cost[i-1];
		System.out.println("a="+cost_a+"\tb="+cost_b+" i="+i);
		return Math.min(cost_a, cost_b);//取最小体力花费
	}
	
	public static int min_cost(int cost[]) {
		
		int arr[]=new int[cost.length];
		arr[0]=0;
		arr[1]=Math.min(cost[0], cost[1]);
		
		for(int i=2;i<cost.length;i++) {
			int a=arr[i-2]+cost[i-1];
			int b=arr[i-1]+cost[i];
			arr[i]=Math.min(a, b);
		}
		System.out.println(Arrays.toString(cost));
		System.out.println(Arrays.toString(arr));
		
		return arr[arr.length-1];
		
	}
	
	public static void main(String[] args) {
		int[]  cost = {10,15,20,5,7};
		//int[]  cost = {1, 100, 1, 1, 1, 100, 1, 1, 100,1};
//		int min_cost=rec_cost(cost, cost.length-1);
		int  min_cost=min_cost(cost);
		System.out.println(min_cost);
	}
}
