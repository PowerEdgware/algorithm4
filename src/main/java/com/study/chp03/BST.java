package com.study.chp03;

import com.study.common.LinkList;

/**
 * 二叉查找树
 *
 */
//TreeMap
public class BST<Key extends Comparable<Key>, Value> {

	private Node root;

	private class Node {
		Key key;
		Value val;
		int N;// 以该节点为根节点的子树的节点个数
		Node left;
		Node right;

		public Node(Key key, Value val, int n) {
			this.key = key;
			this.val = val;
			this.N = n;
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

	public Value get(Key key) {
		Node x = get(root, key);
		if (x != null)
			return x.val;
		return null;
	}

	private Node get(Node x, Key key) {
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

	public void put(Key key, Value val) {
		root = put(root, key, val);
	}

	private Node put(Node x, Key key, Value val) {
		if (x == null)
			return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if (cmp > 0)
			x.right = put(x.right, key, val);
		else if (cmp < 0)
			x.left = put(x.left, key, val);
		else
			x.val = val;

		x.N = size(x.left) + size(x.right) + 1;

		return x;
	}

	public Key min() {
		Node x = min(root);
		if (x != null)
			return x.key;
		return null;
	}

	private BST<Key, Value>.Node min(BST<Key, Value>.Node x) {
		if (x == null)
			return null;
		if (x.left == null)
			return x;
		return min(x.left);
	}

	// 向下取整：找出小于等于key的最大键(假设key 不小于集合中最小值)
	public Key floor(Key key) {
		// key 小于二叉树根节点的键，则小于等于key的最大键一定在左子树中。floorX<=key 因为key<root floorX一定在左子树中。
		// key 大于二叉树根节点的键，因为 floorX <=key,root<key 所以只有当右子树存在小于等于key的节点时，floorX才存在于右子树，
		// 否则根节点就是小于等于key的最大键。
		Node x = floor(root, key);
		if (x != null)
			return x.key;
		return null;
	}

	private Node floor(Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return floor(x.left, key);
		else if (cmp == 0)
			return x;

		Node r = floor(x.right, key);
		if (r != null)
			return r;

		return x;
	}

	// 向上取整
	public Key ceiling(Key key) {
		Node x = ceiling(root, key);
		if (x != null)
			return x.key;
		else
			return null;
	}

	// 找出ceilX >=key
	// 分析
	// 如果 key 大于根节点 ，说明ceiling存在于右子树中；
	// 如果小于根节点，那么如果左子树存在大于等于key的键，那么ceiling存在于左子树中，否则根节点就是key的ceiling节点
	private Node ceiling(Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp > 0)
			return ceiling(x.right, key);
		else if (cmp == 0)
			return x;
		Node l = ceiling(x.left, key);
		if (l != null)
			return l;
		return x;
	}

	// 选择操作，类比快排的划分。可利用节点计数器变量操作
	/**
	 * 找到排名为k的键。（树种有k个小于该key的键）
	 * 
	 * @param k
	 * @return 排名为k的键
	 */
	public Key select(int k) {
		return select(root, k).key;
	}

	private Node select(Node x, int k) {
		if (x == null)
			return null;
		int s = size(x.left);
		if (s > k)
			return select(x.left, k);
		else if (s < k)
			return select(x.right, k - s - 1);
		else
			return x;
	}

	// 删除最小键
	public void deleteMin() {
		root = deleteMin(root);
	}

	private BST<Key, Value>.Node deleteMin(BST<Key, Value>.Node x) {
		if (x == null)
			return null;
		if (x.left == null)
			return x.right;
		x.left = deleteMin(x.left);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	// 删除任意节点x，并用x的后继节点去替换被删除节点x
	// 分析：删除具有两个子节点的节点x。思路是删除x后用他的后继节点来填补x的位置，因为x具有一个右子节点，因此x的后继节点就是右子树中的最小值。
	// 步骤：
	// 1，保存被删除x节点为t；
	// 2，找出x后继节点，并由x指向它；(x的后继节点就是x右子树中的最小节点)
	// 3，删除x右子节点的最小值，并把返回值赋值给x的右链接；
	// 4，设置x的左连接为t的左连接。
	// 5，递归修复被删除节点上父节点的链接，并调整由此节点到根节点路径上所有节点计数器。
	public void delete(Key key) {
		// 查找待删除结点位置
		Node x = delete(root, key);
		if (x != null)
			root = x;
	}

	private BST<Key, Value>.Node delete(BST<Key, Value>.Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp > 0)
			x.right = delete(x.right, key);
		else if (cmp < 0)
			x.left = delete(x.left, key);
		else {// 找到带删除的节点x
			if (x.left == null)
				return x.right;
			if (x.right == null)
				return x.left;
			// x 具有左右子节点
			Node t = x;// 保存x到t
			x = min(x.right);// x的后继节点，此后继节点会替换x的位置
			Node r = deleteMin(x.right);// 删除x右节点为根的右子树中最小值
			x.right = r;// 返回值赋值给x的右链接
			x.left = t.left;// 设置左连接为原位置处x的左连接

		}
		x.N = size(x.left) + size(x.right) + 1;// 调整节点计数器
		return x;// 返回x方便递归向上调整路径上被删除节点的父节点的链接指向。
	}

	public Key max() {
		Node x = max(root);
		if (x != null)
			return x.key;
		return null;
	}

	private BST<Key, Value>.Node max(BST<Key, Value>.Node x) {
		if (x == null)
			return null;
		if (x.right == null)
			return x;
		return max(x.right);
	}

	public boolean contains(Key key) {
		return get(key) != null;
	}

	// 迭代key
	public Iterable<Key> keys() {
		return keys(min(), max());
	}

	// 范围迭代 可参考 TreeMap
	public Iterable<Key> keys(Key low, Key high) {
		// TODO 使用 LinkList

		// 返回具有顺序的key，先左子树查找，再根节点，最后右子树
		LinkList<Key> listKeys = new LinkList<>();
		keys(root, low, high, listKeys);
		return listKeys;
	}

	private void keys(BST<Key, Value>.Node x, Key low, Key high, LinkList<Key> listKeys) {
		if (x == null)
			return;
		int lcmp = low.compareTo(x.key);
		int hcmp = high.compareTo(x.key);
		if (lcmp < 0)
			keys(x.left, low, high, listKeys);// 向左子树从再次查找
		if (lcmp <= 0 && hcmp >= 0)
			listKeys.addLast(x.key);// low<=x.key <=high 符合条件的key 看作根节点
		if (hcmp > 0)
			keys(x.right, low, high, listKeys);// 向右子树查找 x.key<high
	}

	// TODO EXERCISE
	// 思路：根节点高度等于左右高度最大值+1
	public int height() {
		return height(root);
	}

	private int height(BST<Key, Value>.Node x) {
		if (x == null)
			return -1;

		return 1 + Math.max(height(x.left), height(x.right));
	}

	// 按照层次遍历，以该节点为根，先打印根节点，同一层节点从左到右打印
	public void printLevel(Key key) {
		Node x = get(root, key);
		if (x == null)
			return;

		LinkList<Node> stack = new LinkList<>();
		stack.addFirst(x);// push

		print(stack);
	}

	void print(LinkList<Node> stack) {
		if (stack.isEmpty()) {
			return;
		}
		LinkList<Node> next = new LinkList<BST<Key, Value>.Node>();
		while (!stack.isEmpty()) {
			Node x = stack.removeFirst();
			System.out.print(x.key + "->" + x.val + " ");
			if (x.right != null) {
				next.addFirst(x.right);
			}
			if (x.left != null) {
				next.addFirst(x.left);
			}
		}
		System.out.println();

		print(next);
	}

}
