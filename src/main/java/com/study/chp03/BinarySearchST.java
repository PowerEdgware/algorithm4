package com.study.chp03;


import com.study.common.LinkList;

//基于有序数组的符号表
public class BinarySearchST<Key extends Comparable<Key>, Value> {

	private volatile Key[] keys;
	private volatile Value[] vals;
	private int N;// size

	public BinarySearchST(int capacity) {
		this.keys = (Key[]) new Comparable[capacity];// 可动态调整数组大小
		this.vals = (Value[]) new Object[capacity];
	}

	public int size() {
		return N;
	}

	public Value get(Key key) {
		if (size() == 0)
			return null;
		int rank = rank(key);
		// 命中
		if (rank < N && key.compareTo(keys[rank]) == 0) {
			return vals[rank];
		}
		// 未命中
		return null;
	}

	public Value put(Key key, Value val) {
		// 计算元素位置，也即排名
		int pos = rank(key);
		// 存在则更新
		if (pos < N && key.compareTo(keys[pos]) == 0) {
			Value old = vals[pos];
			vals[pos] = val;
			return old;
		}
		// TODO 自动扩容
		if (N == keys.length) {
			int newSize = N + (N >> 2);
			resize(newSize);
		}
		// 不存在，说明插入新元素
		// 需要把新元素插入到keys数组，pos位置，所以从pos处开始后面的元素都需要后移一位
		for (int j = N; j > pos; j--) {
			keys[j] = keys[j - 1];
			vals[j] = vals[j - 1];
		}
		// 新元素插入pos位置
		N++;
		keys[pos] = key;
		vals[pos] = val;
		return null;
	}

	private void resize(int newSize) {
		Key[] newKeys = (Key[]) new Comparable[newSize];
		Value[] newVals = (Value[]) new Object[newSize];
		for (int i = 0; i < N; i++) {
			newKeys[i] = keys[i];
			newVals[i] = vals[i];
		}
		this.keys = newKeys;
		this.vals = newVals;
	}

	// 二分查找计算key的排名
	public int rank(Key key) {
		int lo = 0, hi = N - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(keys[mid]);
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return lo;
	}

	public Key min() {
		return keys[0];
	}

	public Key max() {
		return keys[N - 1];
	}

	public Key select(int k) {
		return keys[k];
	}

	// 向上取整:大于等于key的最小值
	public Key ceiling(Key key) {
		int rnk = rank(key);
		if (rnk < N)
			return keys[rnk];
		return keys[N - 1].compareTo(key) >= 0 ? keys[N - 1] : null;
	}

	// 向下取整：小于等于key的最大值
	public Key floor(Key key) {
		int rnk = rank(key);
		if (rnk > 0 && rnk < N)
			return keys[rnk];
		return keys[0].compareTo(key) <= 0 ? keys[0] : null;
	}

	public boolean contains(Key key) {
		int rnk = rank(key);
		if (rnk < N && key.compareTo(keys[rnk]) == 0)
			return true;
		return false;
	}

	public Iterable<Key> keys() {
		return keys(keys[0], keys[N - 1]);
	}

	// 返回介于low与high之间的key集合
	public Iterable<Key> keys(Key low, Key high) {
		LinkList<Key> list = new LinkList<>();
		int i = rank(low), j = rank(high);
		for (; i < j; i++) {
			list.addLast(keys[i]);
		}
		// j位置的数据，如果存在keys中，则直接加入 list，否则不加入，。
		if (j < N && high.compareTo(keys[j]) == 0) {
			list.addLast(keys[j]);
		}
		return list;
	}

	// 删除键，存在则返回其对于的值，否则返回null
	public Value delete(Key key) {
		int rnk = rank(key);// 计算排名
		// 检查是否命中
		if (rnk < N && key.compareTo(keys[rnk]) == 0) {
			// 把排名为rnk的键值都删除，数组从rnk位置逐一向前挪移1
			Value removedVal = vals[rnk];
			for (int i = rnk; i < N - 1; i++) {
				keys[i] = keys[i + 1];
				vals[i] = vals[i + 1];
			}
			N--;
			return removedVal;
		}
		return null;
	}

}
