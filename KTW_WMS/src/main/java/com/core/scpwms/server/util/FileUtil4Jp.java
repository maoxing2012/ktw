package com.core.scpwms.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * <p>
 * Title: file manager
 * </p>
 * <p>
 * Description: <br>
 * </p>
 * 
 * @author 陳彬彬 2010/10/13
 * @version 1.0
 */

public abstract class FileUtil4Jp {
    /** 编码:MS932  */
    public static final String ENCODE_MS932 = "MS932";

    /** 编码:ISO-8859-1  */
    public static final String ENCODE_ISO88591 = "ISO-8859-1";

    /** 编码:UTF-8 */
    public static final String ENCODE_UTF8 = "UTF-8";

    /** CSV后缀名 */
    public static final String NAME_EXTENSION = ".csv";

    /** Response Head Name */
    private static final String HEAD_NAME = "Content-disposition";

    /** Response Head Value */
    private static final String HEAD_VALUE = "attachment;filename=";

    /** Response Content Type */
    private static final String CONTENT_TYPE = "application/csv";

    /**
     * 
     * <p>新建目录</p>
     *
     * @param path
     * @return
     */
    public static boolean createPath(String path) {
        if (path == null || "".equals(path)) {
            return false;
        }
        File file = new File(path);
        file.mkdirs();
        return true;
    }

    /**
     * 
     * <p>递归新建目录</p>
     *
     * @param path
     * @return
     */
    public static boolean createMultiPath(String path) {
        if (path == null || "".equals(path)) {
            return false;
        }

        StringTokenizer st = new StringTokenizer(path, File.separator);
        
        String path1 = st.nextToken() + File.separator;
        if( path.startsWith("/") ){
        	path1 = "/" + path1;
        }
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

    /**
     * 
     * <p>新建文件</p>
     * @param source
     */
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

    /**
     * 
     * <p>移动文件位置</p>
     *
     * @param source
     * @param desc
     * @return
     */
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

    /**
     * 
     * <p>该目录下是否有文件存在</p>
     *
     * @param path
     * @return
     */
    public static boolean hasFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 
     * <p>删除该目录和该目录下所有文件</p>
     *
     * @param path
     * @return
     */
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

    /**
     * 
     * <p>判断该文件是否是CSV文件（通过后缀名）</p>
     *
     * @param argFileName
     * @return
     */
    public static boolean IsCSVFile(String argFileName) {
        String str = argFileName.substring(argFileName.length() - NAME_EXTENSION.length());
        return str.equalsIgnoreCase(NAME_EXTENSION);
    }

    /**
     * 
     * <p>解析CSV文件（标准CSV格式）</p>
     *
     * @param strFileName
     * @param encode
     * @return
     */
    public static String[][] readCSVFile(String strFileName, String encode, CsvPreference csvPreference) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String[][] cells = new String[0][0];
        try {
            fis = new FileInputStream(strFileName);
            isr = new InputStreamReader(fis, encode);
            CsvListReader clr = new CsvListReader(isr, csvPreference == null ? CsvPreference.STANDARD_PREFERENCE : csvPreference);
            List<String[]> allLines = new Vector<String[]>();
            List<String> oneLineData = null;
            int cols = -1;
            while ((oneLineData = clr.read()) != null) {
                cols = cols < oneLineData.size() ? oneLineData.size() : cols;
                allLines.add(oneLineData.toArray(new String[0]));
            }
            clr.close();
            if (allLines.size() != 0) {
                cells = new String[allLines.size()][cols];
                for (int i = 0; i < allLines.size(); i++) {
                    cells[i] = allLines.get(i);
                }
            }

        } catch (Exception e) {
            // empty
        } finally {
            closeFileInputStream(fis);
            closeInputStreamReader(isr);
        }
        return cells;
    }

    /**
     * 
     * <p>解析Excel文件，生成2维List</p>
     *
     * @param strFileName
     * @param encode
     * @return
     */
    public static List<List<String>> readExcelFile(String strFileName) {
        List<List<String>> excel = null;

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return excel;
    }

    /**
     * 
     * <p>解析CSV文件（指定CSV格式）</p>
     *
     * @param cpr
     * @param strFileName
     * @param encode
     * @return
     */
    public static String[][] readCSVFile(CsvPreference cpr, String strFileName, String encode) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String[][] cells = new String[0][0];
        try {
            fis = new FileInputStream(strFileName);
            isr = new InputStreamReader(fis, encode);
            CsvListReader clr = new CsvListReader(isr, cpr);
            List<String[]> allLines = new Vector<String[]>();
            List<String> oneLineData = null;
            int cols = -1;
            while ((oneLineData = clr.read()) != null) {
                cols = cols < oneLineData.size() ? oneLineData.size() : cols;
                allLines.add(oneLineData.toArray(new String[0]));
            }
            clr.close();
            if (allLines.size() != 0) {
                cells = new String[allLines.size()][cols];
                for (int i = 0; i < allLines.size(); i++) {
                    cells[i] = allLines.get(i);
                }
            }

        } catch (Exception e) {
            // empty
        } finally {
            closeFileInputStream(fis);
            closeInputStreamReader(isr);
        }
        return cells;
    }

    /**
     * 
     * <p>解析CSV文件（标准CSV格式,从画面直接导入）</p>
     *
     * @param is
     * @param encode
     * @return
     */
    public static String[][] readCSVFile(InputStream is, String encode) {
        InputStreamReader isr = null;
        String[][] cells = new String[0][0];
        try {
            isr = new InputStreamReader(is, encode);
            CsvListReader clr = new CsvListReader(isr, CsvPreference.STANDARD_PREFERENCE);
            List<String[]> allLines = new Vector<String[]>();
            List<String> oneLineData = null;
            int cols = -1;
            while ((oneLineData = clr.read()) != null) {
                cols = cols < oneLineData.size() ? oneLineData.size() : cols;
                allLines.add(oneLineData.toArray(new String[0]));
            }
            clr.close();
            if (allLines.size() != 0) {
                cells = new String[allLines.size()][cols];
                for (int i = 0; i < allLines.size(); i++) {
                    cells[i] = allLines.get(i);
                }
            }

        } catch (Exception e) {
            // empty
        } finally {
            closeInputStreamReader(isr);
        }
        return cells;
    }

    /**
     * 
     * <p>解析CSV文件（指定CSV格式,从画面直接导入）</p>
     *
     * @param cpr
     * @param is
     * @param encode
     * @return
     */
    public static String[][] readCSVFile(CsvPreference cpr, InputStream is, String encode) {
        InputStreamReader isr = null;
        String[][] cells = new String[0][0];
        try {
            isr = new InputStreamReader(is, encode);
            CsvListReader clr = new CsvListReader(isr, cpr);
            List<String[]> allLines = new Vector<String[]>();
            List<String> oneLineData = null;
            int cols = -1;
            while ((oneLineData = clr.read()) != null) {
                cols = cols < oneLineData.size() ? oneLineData.size() : cols;
                allLines.add(oneLineData.toArray(new String[0]));
            }
            clr.close();
            if (allLines.size() != 0) {
                cells = new String[allLines.size()][cols];
                for (int i = 0; i < allLines.size(); i++) {
                    cells[i] = allLines.get(i);
                }
            }

        } catch (Exception e) {
            // empty
        } finally {
            closeInputStreamReader(isr);
        }
        return cells;
    }

    /**
     * <p>生成CSV文件（标准格式）</p>
     *
     * @param contents
     * @param strFileName
     * @param encode
     */
    public static void writeCSVFile(String[][] contents, String strFileName, String encode) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            fos = new FileOutputStream(strFileName);
            osw = new OutputStreamWriter(fos, encode);
            CsvListWriter clw = new CsvListWriter(osw, CsvPreference.STANDARD_PREFERENCE);

            for (int i = 0; i < contents.length; i++) {
                List<String> lst = Arrays.asList(contents[i]);
                removeListNull(lst);
                clw.write(lst);
            }
            clw.close();
        } catch (Exception e) {
            // empty
        } finally {
            closeFileOutputStream(fos);
            closeOutputStreamWriter(osw);
        }
    }

    /**
     * <p>生成CSV文件（指定格式）</p>
     * 
     * @param cpr
     * @param contents
     * @param strFileName
     * @param encode
     */
    public static void writeCSVFile(CsvPreference cpr, String[][] contents, String strFileName, String encode) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            fos = new FileOutputStream(strFileName);
            osw = new OutputStreamWriter(fos, encode);
            CsvListWriter clw = new CsvListWriter(osw, cpr);

            for (int i = 0; i < contents.length; i++) {
                List<String> lst = Arrays.asList(contents[i]);
                removeListNull(lst);
                clw.write(lst);
            }
            clw.close();
        } catch (Exception e) {
            // empty
        } finally {
            closeFileOutputStream(fos);
            closeOutputStreamWriter(osw);
        }
    }

    /**
     * <p>生产CSV文件到Response</p>
     *
     * @param response
     * @param strFileName
     * @param Content
     * @param encode
     * @throws UnsupportedEncodingException
     */
    public static void writeCSVStream(HttpServletResponse response, String strFileName, String[][] Content,
            String encode) throws UnsupportedEncodingException {
        response.reset();
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HEAD_NAME, HEAD_VALUE + new String(strFileName.getBytes(ENCODE_MS932), ENCODE_ISO88591));
        ByteArrayOutputStream baos = getCSVStreamWithNoTitle(Content, encode);
        try {
            baos.writeTo(response.getOutputStream());
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * <p>解析TXT文件</p>
     *
     * @param fileName
     * @param encode
     * @return
     */
    public static String[][] readTXTFile(String fileName, String encode) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String[][] cells = new String[0][0];
        try {
            fis = new FileInputStream(fileName);
            isr = new InputStreamReader(fis, encode);

            BufferedReader br = new BufferedReader(isr);
            List<String[]> allLines = new Vector<String[]>();
            String[] oneLineData = null;
            while (br.ready()) {
                oneLineData = new String[] { br.readLine() };
                allLines.add(oneLineData);
            }
            br.close();
            if (allLines.size() != 0) {
                cells = new String[allLines.size()][1];
                for (int i = 0; i < allLines.size(); i++) {
                    cells[i] = allLines.get(i);
                }
            }

        } catch (Exception e) {
            // empty
        } finally {
            closeFileInputStream(fis);
            closeInputStreamReader(isr);
        }
        return cells;
    }

    /**
     * 
     * <p>解析TXT文件（从画面导入）</p>
     *
     * @param is
     * @param encode
     * @return
     */
    public static String[][] readTXTFile(InputStream is, String encode) {
        InputStreamReader isr = null;
        String[][] cells = new String[0][0];
        try {
            isr = new InputStreamReader(is, encode);
            BufferedReader br = new BufferedReader(isr);
            List<String[]> allLines = new Vector<String[]>();
            String[] oneLineData = null;
            while (br.ready()) {
                oneLineData = new String[] { br.readLine() };
                allLines.add(oneLineData);
            }
            br.close();
            if (allLines.size() != 0) {
                cells = new String[allLines.size()][1];
                for (int i = 0; i < allLines.size(); i++) {
                    cells[i] = allLines.get(i);
                }
            }
        } catch (Exception e) {
            // empty
        } finally {
            closeInputStreamReader(isr);
        }
        return cells;
    }

    /**
     * <p>生产TXT文件到Response</p>
     *
     * @param response
     * @param strFileName
     * @param Content
     * @param encode
     * @throws UnsupportedEncodingException
     */
    public static void writeTXTStream(HttpServletResponse response, String strFileName, String[][] Content, String encode) throws UnsupportedEncodingException {
        response.reset();
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HEAD_NAME, HEAD_VALUE + new String(strFileName.getBytes(ENCODE_MS932), ENCODE_ISO88591));
        ByteArrayOutputStream baos = getTXTStream(Content, encode);
        try {
            baos.writeTo(response.getOutputStream());
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * <p>生成CSV文件（标准格式）</p>
     *
     * @param contents
     * @param strFileName
     * @param encode
     */
    public static void writeTXTStream(String[][] contents, String strFileName, String encode) {
        FileOutputStream fos = null;
        ByteArrayOutputStream baos = null;
        try {
            fos = new FileOutputStream(strFileName);
            baos = getTXTStream(contents, encode);
            
            baos.writeTo(fos);
            baos.close();
        } catch (Exception e) {
            // empty
        } finally {
            closeFileOutputStream(fos);
        }
    }

    /**
     * <p>文件上传</p>
     *
     * @param is
     * @param encode
     * @param path
     * @param fileName
     */
    public static void uploadFile(InputStream is, String encode, String path, String fileName) {
        InputStreamReader isr = null;
        FileOutputStream fos = null;
        try {
            isr = new InputStreamReader(is, encode);
            BufferedReader br = new BufferedReader(isr);
            String fileFullPath = path;
            if (fileFullPath.lastIndexOf(File.separator) != fileFullPath.length()) {
                fileFullPath += File.separator;
            }
            fileFullPath += fileName;
            fos = new FileOutputStream(fileFullPath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, encode));
            while (br.ready()) {
                bw.write(br.readLine());
                bw.write("\r\n");
            }
            bw.flush();
            bw.close();
            br.close();
        } catch (Exception e) {
            // empty
        } finally {
            closeInputStreamReader(isr);
            if (fos != null) {
                closeFileOutputStream(fos);
            }
        }
    }

    private static void closeFileOutputStream(FileOutputStream fos) {
        try {
            fos.close();
        } catch (Exception e) {
            // empty
        }
    }

    private static void closeOutputStreamWriter(OutputStreamWriter osw) {
        try {
            osw.close();
        } catch (Exception e) {
            // empty
        }
    }

    private static void closeStringWriter(StringWriter sw) {
        try {
            sw.close();
        } catch (Exception e) {
            // empty
        }
    }

    private static void removeListNull(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                list.set(i, "");
            }

        }
    }

    private static ByteArrayOutputStream getCSVStreamWithNoTitle(String[][] contents, String encode) {
        StringWriter sw = new StringWriter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            CsvListWriter clw = new CsvListWriter(sw, CsvPreference.STANDARD_PREFERENCE);
            for (int i = 0; i < contents.length; i++) {
                List<String> lst = Arrays.asList(contents[i]);
                removeListNull(lst);
                clw.write(lst);
            }
            clw.close();
            sw.flush();
            baos.write(sw.toString().getBytes(encode));
        } catch (Exception e) {
            // empty
        } finally {
            closeStringWriter(sw);
        }

        return baos;
    }

    private static ByteArrayOutputStream getTXTStream(String[][] contents, String encode) {
        StringWriter sw = new StringWriter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            for (int i = 0; i < contents.length; i++) {
                sw.write(contents[i][0]);
                sw.write("\r\n");
            }
            sw.flush();
            baos.write(sw.toString().getBytes(encode));
        } catch (Exception e) {
            // empty
        } finally {
            closeStringWriter(sw);
        }
        return baos;
    }

    private static void closeFileInputStream(FileInputStream fis) {
        try {
            fis.close();
        } catch (Exception e) {
            // empty
        }
    }

    private static void closeInputStreamReader(InputStreamReader isr) {
        try {
            isr.close();
        } catch (Exception e) {
            // empty
        }
    }
}