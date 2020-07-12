package com.study.exercise;

//判断两个字符串是否互为旋转词

public class RotateWord {

	//思路：找到第一个两者相等的单词，计算跨度，在依次比较相差跨度的对应元素
	//如果第一个数组循环完毕都和第二个对应相等则返回true,否则返回false
	public boolean isRotateWords(String w1,String w2) {
		if(w1.length()!=w2.length())
			return false;
		char[] chars_w1=w1.toCharArray();
		char[] chars_w2=w2.toCharArray();
		int pos=0;
		int firstEqualPos=0;
		while(pos<w1.length()) {
			if( chars_w1[pos]==chars_w2[firstEqualPos]) {
				break;
			}
			if(++firstEqualPos>=w1.length()) {
				return false;//没有一个元素和w1[0]相等，返回false
			}
		}
		System.out.println("span="+firstEqualPos);
		//根据跨度进行比较
		while(++pos<w1.length()) {
			if(chars_w1[pos]!=chars_w2[(pos+firstEqualPos)%w2.length()]){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		RotateWord rotateWord=new RotateWord();
		String w1="12345",w2="34512";
		boolean isRotateWord=rotateWord.isRotateWords(w1, w2);
		System.out.println(isRotateWord);
	}
}
