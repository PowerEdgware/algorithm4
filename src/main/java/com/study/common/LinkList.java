package com.study.common;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

//单向链表
public class LinkList<Item> implements Iterable<Item> {
	private int N;

	class Node {
		Item val;
		Node next;
	}

	private Node first;
	private Node last;

	/**
	 * 从表头插入 节点
	 * 
	 * @param item
	 */
	public void addFirst(Item item) {
		checkNull(item);

		Node oldFirst = first;
		Node newFirst = new Node();
		newFirst.val = item;
		newFirst.next = oldFirst;
		N++;

		first = newFirst;
	}

	/**
	 * 从表头删除节点
	 */
	public Item removeFirst() {
		if (first == null) {
			return null;
		}
		Node remNode = first;
		Node next = remNode.next;
		first = next;

		remNode.next = null;
		N--;
		return remNode.val;
	}

	/**
	 * 从表尾部插入节点(新增指向表尾节点的last属性)
	 * 
	 * @param item
	 */
	public void addLast(Item item) {
		checkNull(item);

		Node newNode = new Node();
		newNode.val = item;
		N++;
		if (first == null) {
			first = last = newNode;
			return;
		}
		Node oldLast = last;
		last = newNode;
		oldLast.next = last;
	}

	// TODO 单项链表的删除不可取
	public boolean remove(Item item) {
		LinkList<Item>.Node current;
		Node prev = current = first;
		while (current != null) {
			if (current.val.equals(item)) {
				break;
			}
			prev = current;
			current = prev.next;
		}
		if (current == null) {
			return false;
		}
		N--;
		// 匹配到
		if (current == first) {
			first = null;
			last = null;
		} else {
			// 删除current
			prev.next = current.next;
			if (current == last) {
				last = prev;
			}
			current.next = null;
		}

		return true;
	}

	public int size() {
		return N;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public LinkList<Item> reverse(Node head) {
		Node first = head;
		Node newNode = null;
		while (first != null) {
			Node next = first.next;
			// 头插法反向构建新链表
			first.next = newNode;
			newNode = first;

			first = next;
		}
		LinkList<Item> newList = new LinkList<>();
		newList.first = newNode;
		return newList;
	}

	void checkNull(Object o) {
		Objects.requireNonNull(o, "Element Cannot be null");
	}

	private class SequenceIterator implements Iterator<Item> {

		private Node current;

		public SequenceIterator(Node head) {
			this.current = head;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			Node ret = current;
			this.current = ret.next;
			return ret.val;
		}

	}

	@Override
	public Iterator<Item> iterator() {
		return new SequenceIterator(first);
	}

	public static void main(String[] args) {
		// LinkedList
		LinkList<String> list = new LinkList<String>();
		for (int i = 0; i < 5; i++) {
			list.addLast(i + "");
		}

		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		LinkList reversedNode = list.reverse(list.first);

		System.out.println();
		System.out.println("after reserve");

		Iterator<String> reversedIt = reversedNode.iterator();
		while (reversedIt.hasNext()) {
			System.out.print(reversedIt.next() + " ");
		}
	}
}
