package com.study.common;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * 实现 push pop getMax getMin O(1)的栈
 *
 * @param <Item>
 */
public class O1Link<Item extends Comparable<Item>> {

	class Node {
		Item item;
		Node next;

		public Node(Item item) {
			this.item = item;
		}
	}

	int N;// size
	volatile Node head;
	volatile Node max;// 最大值链表
	volatile Node min;

	public void push(Item item) {
		if (head == null) {
			head = max = min = new Node(item);
			return;
		}
		Node n = new Node(item);

		Item newMaxItem = n.item;
		if (max.item.compareTo(n.item) > 0) {
			newMaxItem = max.item;
		}
		Node newMax = new Node(newMaxItem);
		newMax.next = max;
		max = newMax;

		Item newMinItem = n.item;
		if (min.item.compareTo(n.item) < 0) {
			newMinItem = min.item;
		}
		Node newMin = new Node(newMinItem);
		newMin.next = min;
		min = newMin;

		// 头插法
		Node oldHead = head;
		n.next = oldHead;

		head = n;

		N++;
	}

	public Item pop() {
		assert head != null;
		//remove head
		Node delNode=head;
		Item item=delNode.item;
		head=delNode.next;
		
		//remove max/min
		max=max.next;
		min=min.next;
		
		return item;
	}

	public Item max() {
		assert max != null;
		return this.max.item;
	}

	public Item min() {
		assert min != null;
		return this.min.item;
	}

	public int size() {
		return N;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Iterator<Item> iterator() {
		return new Itr(head);
	}

	private class Itr implements Iterator<Item> {

		private Node current;

		public Itr(Node n) {
			this.current = n;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			Node ret = current;
			current = current.next;
			return ret.item;
		}
	}
	
	public static void main(String[] args) {
		O1Link<String> o1Link=new O1Link<>();

		for (int i = 0; i < 10; i++) {
			o1Link.push(rnd()+i);
			if(i%2==0) {
				String ele=o1Link.pop();
				System.out.println("poped="+ele);
			}
		}
		System.out.println("Print All:");
		Iterator<String> it=o1Link.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
		System.out.println("max="+o1Link.max()+"\t min="+o1Link.min());
	}
	
	static String abc="abcdefghijklmnopqrst";
	static String rnd() {
		return abc.charAt(ThreadLocalRandom.current().nextInt(abc.length())) +"";
	}
}
