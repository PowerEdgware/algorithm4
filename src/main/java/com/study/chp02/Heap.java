package com.study.chp02;

import java.util.Arrays;

public class Heap<Key extends Comparable<Key>> extends BaseExample {

	private Key arr[];// 二叉堆
	private int size;

	public Heap(Key[] arr) {
		Comparable[] a = Arrays.copyOf(arr, arr.length, Comparable[].class);
		this.arr = (Key[]) a;
		this.size = this.arr.length;
		heapify();
	}

	// 使堆有序
	void heapify() {
		int half = size / 2;
		for (int i = half - 1; i >= 0; i--) {// 一层一层的下沉
			sinkDown(i, size);
		}
	}

	public void sort() {
		// 对换0和size-1，使size-1下沉 同时减少数组大小
		int N = size;
		while (N > 0) {
			// 交换元素
			exche(arr, 0, N-1);

			// 下沉首元素
			sinkDown(0, --N);
		}
	}

	public void show() {
		show(arr);
	}

	// 上浮元素
	private void shiftUp(int k) {
		while (k > 0) {
			int p = (k - 1) / 2;
			if (arr[p].compareTo(arr[k]) >= 0)
				break;
			exche(arr, p, k);
			k = p;
		}
	}

	private void sinkDown(int k, int N) {
		int half = N >>> 1;

		while (k < half) {// 元素从0开始
			int next = 2 * k + 1;
			int right = next + 1;
			Key o = arr[next];
			if (right < N && o.compareTo(arr[right]) < 0) {
				next = right;
				o = arr[next];
			}
			// 比较父元素和较大子元素
			if (arr[k].compareTo(o) >= 0) {
				break;
			}
			exche(arr, k, next);

			k = next;
		}
	}
	
	
	public static void main(String[] args) {
		Integer arr[]= {0,3,1,3,1,0,6,11,9,5,7};
		Heap<Integer> heap=new Heap<>(arr);
		
		heap.show();
		
		heap.sort();
		
		System.out.println("After heap sort");
		heap.show();
	}
}
