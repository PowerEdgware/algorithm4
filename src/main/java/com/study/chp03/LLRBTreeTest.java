package com.study.chp03;

import java.util.concurrent.ThreadLocalRandom;

public class LLRBTreeTest {

	public static void main(String[] args) {
		LLRBTree<String, Integer> llrbTree=new LLRBTree<>();
		
		int x=39;
		for (int count=0;count<x;) {
			llrbTree.insert(rnd(++count), count);
		}
		
		llrbTree.displayTree();
		System.out.println(llrbTree.getMax());
		
	}
	
	static String abc="abcdefghijklmnopqrstuvwxyz0";
	static int abcLen=abc.length();
	static String rnd(int i) {
		return abc.charAt(ThreadLocalRandom.current().nextInt(i>abcLen?abcLen:i))+""+i;
	}
}
