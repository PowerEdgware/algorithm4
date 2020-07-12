package com.study.datastru;

/**
 * 给定一个数组，选定不相邻的数据相加，使得和最大
 * @author shangcj
 *
 */
public class MaxSum {

	/**
	 * 
	 * @param arr
	 * @param i -i处和最大值的最优解
	 * @return
	 */
	public static int rec_opt(int [] arr,int i) {
		if(i==0) return arr[0];
		if(i==1) return Math.max(arr[1], arr[0]);
		
		int choose=rec_opt(arr, i-2)+arr[i];//选择i
		int nochoose=rec_opt(arr, i-1);//不选i
		
		return Math.max(choose, nochoose);
	}
	
	static int dp_optimizer(int arr[]) {
		int[] opt=new int[arr.length];
		opt[0]=arr[0];
		opt[1]=Math.max(arr[0], arr[1]);
		
		//直到i处和的最优解
		//1.选i,则i处的解为 前i-2处最优+i处的值;case1
		//2.不选i，则i处的解为前i-1处的最优解;case2
		//3.到i处和的的最优解=max(case1,case2)
		for(int i=2;i<arr.length;i++) {
			int a=opt[i-2]+arr[i];
			int b=opt[i-1];
			opt[i]=Math.max(a, b);
		}
		return opt[opt.length-1];
	}
	
	public static void main(String[] args) {
		int[] arr= {1,2,4,1,7,8,3};
		
		int max_sum=rec_opt(arr, arr.length-1);
		System.out.println("rec_opt="+max_sum);
		max_sum=dp_optimizer(arr);
		System.out.println("dp_optimizer="+max_sum);
	}
}
