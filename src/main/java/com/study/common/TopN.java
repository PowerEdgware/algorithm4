package com.study.common;

import com.study.chp02.MinPQ;

public class TopN {

	public static void main(String[] args) {
		Integer arr[]= {0,3,1,3,1,0,6,11,9,5,7};
		
		int topN=5;
		
		MinPQ<Integer> pq=new MinPQ<>(topN);
		
		for (int i = 0; i < arr.length; i++) {
			if(pq.size()>=topN) {
				pq.deleteMin();
			}
			pq.insert(arr[i]);
		}
		
		pq.show();
	}
}
