/*
@@程�?�代??? = QIAFileAccess.java 
@@程�?��?�稱 = 表格??��?�共?��程�??
@@程�?��?�本 = V1.000
@@?��?��?��??? = 2012/03/06
@@檢查�? = ?��容由YPM?��??�產???
 */
package lts.docrm.core.util;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class FileAccess {

	/**
	 * �解壓縮
	 * 
	 * @param source source
	 * @param target target
	 * @throws IOException IOException
	 * @throws ZipException ZipException
	 */
	public static void unzipCn(String source, String target) throws ZipException, IOException {
		FileOutputStream fileOut;
		File file;
		InputStream inputStream;
		int readedBytes;
		byte[] buf = new byte[1024];

		ZipFile zipFile = new ZipFile(source);

		for (Enumeration<?> entries = zipFile.getEntries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			file = new File(target + entry.getName());

			if (entry.isDirectory()) {
				file.mkdirs();
			} else {
				File parent = file.getParentFile();
				if (parent != null && !parent.exists()) {
					parent.mkdirs();
				}

				inputStream = zipFile.getInputStream(entry);

				fileOut = new FileOutputStream(file);
				while ((readedBytes = inputStream.read(buf)) > 0) {
					fileOut.write(buf, 0, readedBytes);
				}
				fileOut.close();

				inputStream.close();
			}
		}

		zipFile.close();
	}
}
