package com.study.chp03;

import java.util.Stack;

//基于2-3-4树的左倾和红黑树。3-节点左斜，且不允许有单独存在右链接。保持black-link完美平衡
public class LLRBTree<Key extends Comparable<Key>, Value> {
	class Node {
		Key key;
		Value val;
		Node left, right;
		boolean color;

		public Node(Key key, Value val, boolean color) {
			this.color = color;
			this.key = key;
			this.val = val;
		}
	}

	static final boolean RED = true;
	static final boolean BLACK = false;

	Node root;// 根节点
	int size;

	public Value get(Key key) {
		Node x = get(root, key);
		if (x != null)
			return x.val;
		return null;
	}

	Node get(Node x, Key key) {
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp == 0)
				return x;
			else if (cmp > 0)
				x = x.right;
			else
				x = x.left;
		}
		return null;

	}

	public void insert(Key key, Value val) {
		assert key != null;

		Node x = root;
		root = insert(x, key, val);
		root.color = BLACK;
		size++;
	}

	private LLRBTree<Key, Value>.Node insert(LLRBTree<Key, Value>.Node x, Key key, Value val) {
		if (x == null) {
			return new Node(key, val, RED);// 插入底部
		}
		// 由上而下搜索路径，分裂四节点
		if (isRed(x.left) && isRed(x.right))
			flipColor(x);

		// 继续查找插入点
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			x.val = val;// 替换节点为新值
		else if (cmp > 0)
			x.right = insert(x.right, key, val);// 右边插入
		else
			x.left = insert(x.left, key, val);

		// 插入完毕，检测插入节点的颜色，插入了左边还是右边。向上递归操作
		if (isRed(x.right) && !isRed(x.left))
			x = rotateLeft(x);// 插入到了右边，因不允许单独存在红色右链接，所以需要左旋，去除红色右链接

		// 出现左左红色链接。向上递归操作
		if (isRed(x.left) && isRed(x.left.left))
			// 以x右旋
			x = rotateRight(x);
		return x;
	}

	public Key getMax() {
		Node max = max();
		if (max != null)
			return max.key;
		return null;
	}

	Node max() {
		Node x = root;
		while (x != null) {
			if (x.right == null)
				return x;
			x = x.right;
		}
		return null;
	}

	public Key getMin() {
		Node x = min();
		if (x != null)
			return x.key;
		return null;
	}

	Node min() {
		Node x = root;
		while (x != null) {
			if (x.left == null)
				return x;
			x = x.left;
		}
		return null;
	}

	// TODO 删除最小值
	// 沿着删除路径向下变换，使得当前节点不是2节点。
	public void deleteMin() {
		root = deleteMin(root);
		root.color = BLACK;
	}

	private LLRBTree<Key, Value>.Node deleteMin(LLRBTree<Key, Value>.Node x) {
		if (x.left == null)
			return null;
		// 判断节点是否是2-节点，是2-节点必须从右边兄弟节点借一个或者合并为把右边兄弟合并成4-节点
		if (!isRed(x.left) && !isRed(x.left.left)) {
			x = moveRedLeft(x);
		}
		// 继续向下进行变换
		x.left = deleteMin(x.left);

		// 向上补全红色右边链接和4-节点
		if (isRed(x.right))
			x = rotateLeft(x);
		// 2条相连的红色左连接
		if (isRed(x.left) && isRed(x.left.left))
			x = rotateRight(x);
		// 分裂4-节点，并把红色父节点向上传递
		if (isRed(x.left) && isRed(x.right))
			flipColor(x);
		return x;
	}

	private Node moveRedLeft(Node x) {
		// 合并为4-节点
		flipColor(x);

		if (isRed(x.right.left)) {// 右兄弟节点不是2-节点，则借一个节点过来。右边兄弟节点没有红色右链接，所以只判断左链接是否是红色即可
			x.right = rotateRight(x.right);// 此时x.right变红
			// 以x左旋
			x = rotateLeft(x);
			// 分裂4-节点
			flipColor(x);
		}
		return x;
	}

	// TODO 删除最大值
	public void deleteMax() {
		root = deleteMax(root);
		root.color = BLACK;
	}

	// 沿着右边向下，确保当前节点不是二节点且最终右链接是红色，这样删除后才不会破坏黑色平衡
	// 1.当前节点不是二节点，说明有红色左连接，则需要把红色左连接右旋变成红色右链接。有了红色右边链接，则继续递归删除红色右边链接。
	// 2.当前节点是2节点，
	// 3.到了这一步，
	private LLRBTree<Key, Value>.Node deleteMax(LLRBTree<Key, Value>.Node x) {
		if (isRed(x.left) && !isRed(x.right))// 右边变红
			x = rotateRight(x);
		if (x.right == null)
			return null;

		// 右边不为空，此时需要判断右边是否是二节点
		if (!isRed(x.right) && !isRed(x.right.left)) {// x的右边节点是二节点
			x = moveRedRight(x);
		}
		x.right = deleteMax(x.right);

		return fixUp(x);
	}

	private Node moveRedRight(Node x) {
		flipColor(x);// 合并四节点

		if (isRed(x.left.left)) {
			x = rotateRight(x);
			flipColor(x);// 继续向下变换
		}
		return x;
	}

	private Node fixUp(Node x) {
		// 向上变换，调整红色右边链接，左左红色链接，和4-节点
		if (isRed(x.right))
			x = rotateLeft(x);
		if (isRed(x.left) && isRed(x.left.left))
			x = rotateRight(x);
		if (isRed(x.right) && isRed(x.left))
			flipColor(x);
		return x;
	}

	public void delete(Key key) {
		if (size() == 0)
			return;
		root = delete(root, key);
		root.color = BLACK;
	}

	private LLRBTree<Key, Value>.Node delete(Node x, Key key) {
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {// left
			// 左边，沿着路上向下进行变换
			if (!isRed(x.left) && !isRed(x.left.left)) {
				x = moveRedLeft(x);
			}
			x.left = delete(x.left, key);
		} else {
			// 相等或者向右走
			if (isRed(x.left))// 出现红色右链接
				x = rotateRight(x);
			if (cmp == 0 && x.right == null)
				return null;
			if (!isRed(x.right) && !isRed(x.left.left))
				x = moveRedRight(x);

			if (cmp == 0) {
				Node min = min();
				x.key = min.key;
				x.val = min.val;
				x.right = deleteMin(x.right);
			} else
				x.right = delete(x.right, key);
		}
		return fixUp(x);
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

	public int size() {
		return this.size;
	}

	private int blank() {
		int nblanks = 1;
		int size = size();
		while (nblanks < size) {
			nblanks = nblanks * 2;
		}
		return nblanks;
	}

	// ------------------help methods-----------
	boolean isRed(Node x) {
		if (x == null)
			return false;
		return x.color == RED ? true : false;
	}

	void flipColor(Node x) {
		x.color = !x.color;
		x.left.color = !x.left.color;
		x.right.color = !x.right.color;
	}

	Node rotateLeft(Node x) {// right-red
		Node right = x.right;
		x.right = right.left;

		right.left = x;

		right.color = x.color;

		x.color = RED;

		return right;
	}

	Node rotateRight(Node h) {// left-red
		Node x = h.left;
		h.left = x.right;

		x.right = h;

		x.color = h.color;

		h.color = RED;

		return x;
	}

}
