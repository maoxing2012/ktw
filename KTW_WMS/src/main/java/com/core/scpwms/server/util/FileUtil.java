package com.core.scpwms.server.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;

public abstract class FileUtil {
    /**
     * 
     * <p>解析Excel文件，生成2维List</p>
     *
     * @param strFileName
     * @param encode
     * @return
     */
    public static List<List<String>> readExcelFile(String strFileName) throws BiffException, IOException{
    	
        List<List<String>> excel = null;

        // 打开文件
        Workbook book = Workbook.getWorkbook(new File(strFileName));

        // 取得第一个sheet
        Sheet sheet = book.getSheet(0);

        // 取得行数
        int rows = sheet.getRows();

        if (rows > 0) {

            excel = new ArrayList<List<String>>(rows);

            for (int i = 0; i < rows; i++) {
                // 行
                Cell[] cell = sheet.getRow(i);

                List<String> excelRow = new ArrayList<String>(cell.length);
                for (int j = 0; j < cell.length; j++) {
                    // 设置单元格内容
                    excelRow.add(cell[j].getContents().trim());
                }

                excel.add(excelRow);
            }
        }

        return excel;
    }
    
	public static boolean createPath(String path) {
		if (path == null || "".equals(path)) {
			return false;
		}
		File file = new File(path);
		file.mkdirs();
		return true;
	}
	
	public static boolean createMultiPath(String path) {
		if (path == null || "".equals(path)) {
			return false;
		}

		StringTokenizer st = new StringTokenizer(path, File.separator);
		String path1 = st.nextToken() + File.separator;
		String path2 = path1;
		while (st.hasMoreTokens()) {
			path1 = st.nextToken() + File.separator;
			path2 += path1;
			File inbox = new File(path2);
			if (!inbox.exists())
				inbox.mkdir();
		}
		return true;
	}
	
	public static void CreateFile(String source) {
		File file = new File(source);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static boolean move(String source, String desc) {
		File file = new File(source);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].renameTo(new File(desc + "/" + files[i].getName()));
			}
		}
		return true;
	}
	
	public static boolean hasFile(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	public static boolean deleteAll(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (!file.isDirectory()) {
				file.delete();
			} else {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteAll(files[i].getPath());
					if (files[i].exists()) {
						files[i].delete();
					}
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args){
		String path = "D:/FTP/Download/PO";
		
		if( FileUtil.hasFile(path) ){
			File fold = new File(path);
			File[] files = fold.listFiles();
			for( File file : files ){
				// 后缀名是XML
				if( file.getName().endsWith(".xml") ){
					// 移动到WMS的工作目录下，FTP上的文件删除。
					String defaultFileBasePath = "D:/tempFile";
					String descPath = defaultFileBasePath + File.separator + "RECV";
					String descFileName = descPath + File.separator + file.getName().substring(0, file.getName().indexOf('.')) + "_" +Calendar.getInstance().getTimeInMillis() + ".xml";
					
					File destFile = new File(descFileName);
					try{
						FileUtils.moveFile(file, destFile);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}