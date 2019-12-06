package com.study.chp03;

import java.util.Iterator;
import java.util.List;

import com.study.utils.FileUtils;

//字符串频率统计
public class FrequencyCounter {

	public static void main(String[] args) {

		int minLen = 8;
		/**
		 *TODO tale.txt 135650
build data source cost=633 ms
distinct size=5127
business 122 cost=212 ms
		 */
		
		/**
		 *leipzig1M.txt 21191455 单词总数
build data source cost=25084 ms
distinct size=299594
government 24763 cost=890455 ms
		 */
		BinarySearchST<String, Integer> bst = new BinarySearchST<>(1024);
		
		//TODO 替换成二叉查找树
		/**
		 *TODO tale.txt 135650  单词总数
build data source cost=186 ms
distinct size=5127
business 122 cost=43 ms
		 */
		
		/**
		 *TODO leipzig1M.txt 21191455 单词数
build data source cost=23797 ms
distinct size=299594 不同的单词数
government 24763 cost=7453 ms
		 */
		//BST<String, Integer> bst=new BST<>();

		//tale.txt
		//leipzig1M.txt
		String fileName="leipzig1M.txt";
		long start = System.currentTimeMillis();
		List<String> wolds = FileUtils.file2WoldArray("algs4-data/"+fileName);
		System.out.println(wolds.size());

		System.out.println("build data source cost=" + (System.currentTimeMillis() - start) + " ms");

		start = System.currentTimeMillis();
		// 构造符号表并统计频率
		Iterator<String> it = wolds.iterator();
		for (; it.hasNext();) {
			String wold = it.next();
			if (wold.length() < minLen)
				continue;
			if (!bst.contains(wold))
				bst.put(wold, 1);
			else
				bst.put(wold, bst.get(wold) + 1);
		}

		// 取出出现频率最高的单词
		String max = "";
		bst.put(max, 0);

		for (String wold : bst.keys()) {
			if (bst.get(wold) > bst.get(max))
				max = wold;
		}
		long end = System.currentTimeMillis();

		System.out.println("distinct size="+bst.size());
		System.out.println(max + " " + bst.get(max) + " cost=" + (end - start) + " ms");

	}
}
