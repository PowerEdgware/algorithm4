package com.study.exercise;

import java.util.Arrays;

/**
 * 反转 int
 * 比如 389  反转后：983
 * @author all
 *
 */
public class IntReverse {

	public static  int reverse(int input) {
		if(input<0) {
			input=toPositive(input);
		}
		byte[] reversed=new byte[12];
		byte _1=-1;
		//填充-1
		Arrays.fill(reversed,_1);
		
		int pos=0;
		while(input>0) {
			//10的二进制: 0000 1010
			byte mod=(byte) (input%10);
			reversed[pos++]=mod;
			input/=10;
		}
		
		for (byte b : reversed) {
			if(b!=-1)
			System.out.print(b);
		}
		System.out.println("\r\npos="+pos);
		//还原
		int reverse=0;
		int idx=0;
		while(pos>0) {
			reverse+=reversed[idx++]*Math.pow(10, --pos);
		}
		return reverse;
	}
	static int  toPositive(int x) {
		return (x-1)^0xffffffff;//-1,取反
	}
	
	public static void main(String[] args) {
		int x=2930201;
		//-2 11111111111111111111111111111110
		System.out.println("orig:"+x);
		System.out.println((x-1)^0xffffffff);
		
		int ret=reverse(x);
		System.out.println(ret);
		
		//System.out.println(Math.pow(10, 2));
	}
}
