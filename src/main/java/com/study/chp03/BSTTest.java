package com.study.chp03;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.study.utils.FileUtils;

public class BSTTest {

	public static void main(String[] args) {
		
		BST<String,Integer> bst=new BST<>();
		
		int minLen=2;
		String fileName="tale.txt";
		List<String> wolds = FileUtils.file2WoldArray("algs4-data/"+fileName);
		System.out.println("wolds="+wolds.size());
		
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
		System.out.println("Bst size="+bst.size());
		String rndKey=wolds.get(new Random().nextInt(wolds.size()));
		System.out.println(rndKey);
		bst.printLevel(rndKey);
		

	}
}
