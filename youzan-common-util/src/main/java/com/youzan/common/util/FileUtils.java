package com.youzan.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public final class FileUtils {
	
	/**
	 * inputstream to byte
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}
	
	/**
	 * 写入文件
	 * @param string
	 * @param f
	 * @throws FileNotFoundException
	 */
	public static void write(String string,File f) throws FileNotFoundException {
		PrintStream ps = new PrintStream(f);
		ps.print(string);//写入的字符串
		ps.close();
	}
}
