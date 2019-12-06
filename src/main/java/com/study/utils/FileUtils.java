package com.study.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class FileUtils {

	public static List<String> file2WoldArray(String fileName) {
		FileInputStream fis = null;
		BufferedReader bufferedReader = null;
		try {
			List<String> list = new ArrayList<>();
			fis = new FileInputStream(fileName);
			bufferedReader = new BufferedReader(new InputStreamReader(fis));
			String line = bufferedReader.readLine();
			while (!isNull(line)) {
				if(!blank(line)) {
					String[] data = line.split("\\s+");
					List<String> as=Arrays.asList(data);
					list.addAll(as);
				}
				line = bufferedReader.readLine();
			}
			return list;
		}catch(

	Exception e)
	{
		e.printStackTrace();
	}finally
	{
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}return null;
	}

	static boolean isNull(Object o) {
		return o==null;
	}

	static boolean blank(String in) {
		return "".equals(in);
	}
}
