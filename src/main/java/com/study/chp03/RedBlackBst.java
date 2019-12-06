package com.study.chp03;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import com.study.utils.FileUtils;

import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * 红黑二叉查找树
 */
//TreeMap
public class RedBlackBst<Key extends Comparable<Key>, Value> {

	static final boolean RED = true;
	static final boolean BLACK = false;

	private Node root;

	private class Node {
		Key key;
		Value val;
		Node left;
		Node right;
		int N;// 以该节点为根节点的子树的节点个数
		boolean color = RED;

		public Node(Key key, Value val, int N, boolean color) {
			this.color = color;
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		return x.N;
	}

	// 获取节点
	public Value get(Key key) {
		Node x = get(root, key);
		if (x != null)
			return x.val;
		return null;
	}

	private RedBlackBst<Key, Value>.Node get(RedBlackBst<Key, Value>.Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			return x;
		else if (cmp > 0)
			return get(x.right, key);
		else
			return get(x.left, key);
	}

	// 插入节点
	public void put(Key key, Value val) {
		root = put(root, key, val);
		// 根节点是黑色
		root.color = BLACK;
	}

	private Node put(Node x, Key key, Value val) {
		if (x == null)
			return new Node(key, val, 1, RED);
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = put(x.left, key, val);
		else if (cmp > 0)
			x.right = put(x.right, key, val);
		else
			x.val = val;
		// 如果新节点是底部节点的左子节点，直接结束。否则需要通过旋转和颜色变换实现查找路径的由下向上的递归变换。
		// 1.x的右节点是红色，左节点是黑色，则左旋。且旋转返回的节点赋值给x
		if (isRed(x.right) && !isRed(x.left))
			x = rotateLeft(x);
		// 2.x的左节点是红色，左节点的左节点也是红色，则右旋。
		if (isRed(x.left) && isRed(x.left.left))
			x = rotateRight(x);
		// 3.x左右子节点都是红色，则着色。
		if (isRed(x.left) && isRed(x.right))
			flipColor(x);

		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	// 删除操作，删除最小节点
	public void deleteMin() {

	}

	// 删除
	public void delete() {

	}
	// ----------------------------旋转和着色--------------------------------------------

	private void flipColor(Node x) {
		x.left.color = BLACK;
		x.right.color = BLACK;
		x.color = RED;
	}

	private boolean isRed(Node x) {
		if (x == null)
			return false;
		return x.color;
	}

	private Node rotateLeft(Node x) {
		Node t = x.right;// 保存x.right
		x.right = t.left;
		t.left = x;
		t.color = x.color;
		x.color = RED;

		t.N = x.N;

		x.N = size(x.right) + size(x.left) + 1;
		return t;
	}

	// x left
	// left right --> x
	private Node rotateRight(Node x) {
		Node t = x.left;
		x.left = t.right;
		t.right = x;
		t.color = x.color;
		x.color = RED;
		t.N = x.N;
		x.N = size(x.left) + size(x.right) + 1;

		return t;
	}

	private int blank() {
		int nblanks = 1;
		int size = size();
		while (nblanks < size) {
			nblanks = nblanks * 2;
		}
		return nblanks;
	}

	public void displayTree() {
		Stack<Node> globalStack = new Stack<>();
		globalStack.push(root);
		int nBlanks = blank();
		int dotNum = nBlanks;
		System.out.println("nBlanks=" + nBlanks);
		boolean isRowEmpty = false;
		for (int k = 0; k < dotNum; k++) {
			System.out.print("==");
		}
		System.out.println();
		while (isRowEmpty == false) {
			Stack<Node> localStack = new Stack<>();
			isRowEmpty = true;
			for (int j = 0; j < nBlanks; j++)
				System.out.print(' ');
			while (globalStack.isEmpty() == false) {
				Node temp = globalStack.pop();
				if (temp != null) {
					System.out.print(temp.key + (temp.color == RED ? "R" : "B"));
					localStack.push(temp.left);
					localStack.push(temp.right);
					if (temp.left != null || temp.right != null) {
						isRowEmpty = false;
					}
				} else {
					System.out.print("..");
					localStack.push(null);
					localStack.push(null);
				}
				for (int j = 0; j < nBlanks * 2 - 2; j++) {
					System.out.print(' ');
				}
			}
			System.out.println();
			nBlanks /= 2;
			while (localStack.isEmpty() == false)
				globalStack.push(localStack.pop());
		}
		System.out.println();
		for (int k = 0; k < dotNum; k++) {
			System.out.print("==");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// edu.princeton.cs.algs4.RedBlackBST<Comparable<Key>, Value>
		RedBlackBst<String, Integer> bst = new RedBlackBst<>();
		// tale.txt
		// leipzig1M.txt
//		String fileName = "tinyTale.txt";
//		List<String> list = FileUtils.file2WoldArray("algs4-data/" + fileName);
//		list.stream().forEachOrdered(c -> {
//			bst.put(c, c.hashCode());
//		});
//		
		Scanner sc=new Scanner(System.in);
		while(sc.hasNext()) {
			String key=sc.nextLine();
			bst.put(key, key.hashCode());
			if("q".equals(key)) {
				break;
			}
		}
		sc.close();


		StdOut.println(bst.size());
		bst.displayTree();
	}
}
