package com.study.chp02;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 大顶堆 PriorityQueue Timer
 * 
 * @param <Key>
 */
public class MinPQ<Key extends Comparable<Key>> extends BaseExample {

	private Key[] pq;
	private int N;

	public MinPQ(int maxN) {
		pq = (Key[]) new Comparable[maxN];
	}

	// 上浮位置k的元素
	private void shiftUp(int k) {
		while (k > 0) {
			int p = (k-1) / 2;
			if (less(pq[p], pq[k])) {
				break;
			}
			// 父元素小于当前元素，则交换
			exche(pq, p, k);
			// 继续向上维持堆的有序化
			k = p;
		}
	}

	// 下沉>索引可以从0开始
	private void sinkDown(int k) {
		// 结束于堆的一半元素处，也即是h-1层
		int half = N >>> 1;
		while (k < half) {
			int left = 2 * k + 1;
			// 比较左孩子和当前元素大小
			// 左右元素比较一下，找出大的在和父元素相比
			int right = left + 1;
			Comparable<Key> c = pq[left];
			int next = left;
			if (right < N && !less(c, pq[right])) {
				c = pq[right];
				next = right;
			}
			// 父元素已经大于等于左右孩子最大者，则结束。堆有序
			if (!less(c, pq[k])) {
				break;
			}
			// 继续维持堆特性
			exche(pq, k, next);
			k = next;
		}
	}
	
	//先不自动扩容
	public void insert(Key key) {
		//放到最后位置并上浮
		pq[N]=key;
		shiftUp(N);
		
		N++;
	}
	
	public Key deleteMin() {
		Key max=pq[0];
		//末尾元素放入首元素并下沉
		pq[0]=pq[--N];
		
		pq[N]=null;
		sinkDown(0);
		return max;
	}
	public int size() {
		return N;
	}
	
	public void show() {
		show(pq);
	}
	
	
	public static void main(String[] args) {
		Integer arr[]= {0,3,1,6,11,9,5,7};
		MinPQ<Integer> pq=new MinPQ<>(arr.length);
		
		Stream.of(arr).forEach(pq::insert);
		
		pq.show();
		for (int i = 0; i < arr.length; i++) {
			System.out.println(pq.deleteMin());
		}
		
		pq.show();
	}
}
