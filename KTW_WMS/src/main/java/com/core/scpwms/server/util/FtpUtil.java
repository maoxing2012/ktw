package com.core.scpwms.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtil {
	private static Logger logger = Logger.getLogger(FtpUtil.class);  
	
	/** 
     * 获取FTPClient对象 
     * @param ftpHost FTP主机服务器 
     * @param ftpPassword FTP 登录密码 
     * @param ftpUserName FTP登录用户名 
     * @param ftpPort FTP端口 默认为21 
     * @return 
     */  
    public static FTPClient getFTPClient(String ftpHost, String ftpPassword, String ftpUserName, int ftpPort) {  
        FTPClient ftpClient = null;  
        try {  
            ftpClient = new FTPClient();  
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器  
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器  
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                logger.info("未连接到FTP，用户名或密码错误。");  
                ftpClient.disconnect();  
            } else {  
                logger.info("FTP连接成功。");  
            }  
        } catch (SocketException e) {  
            e.printStackTrace();  
            logger.info("FTP的IP地址可能错误，请正确配置。");  
        } catch (IOException e) {  
            e.printStackTrace();  
            logger.info("FTP的端口错误,请正确配置。");  
        }  
        return ftpClient;  
    }
    
    /**
     * 
     * <p>关闭连接</p>
     *
     * @param ftpClient
     * @throws IOException
     */
    public static void closeServer(FTPClient ftpClient) throws IOException {
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }
    
    public static boolean uploadFile2Ftp(String ftpUserName, String ftpPassword, String ftpHost, int ftpPort, String basePath, String[][] localPathDescPath){
    	Boolean isOk = true;
    	FTPClient ftpClient = null;
    	
    	 try {  
    		 ftpClient = FtpUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName, ftpPort);  
    		 ftpClient.setControlEncoding("UTF-8"); // 中文支持  
             ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
             ftpClient.enterLocalPassiveMode();
             
             ftpClient.changeWorkingDirectory(basePath);
             ftpClient.changeWorkingDirectory("UPLOAD");
             for( String[] paths : localPathDescPath ){
            	 String ftpPath = paths[0];
            	 String localFilePath = paths[1];
            	 
            	 if(!ftpClient.changeWorkingDirectory(ftpPath)){
            		 isOk = false;
                     return isOk;
 	             }
            	 
 	             File localFile = new File(localFilePath);
 	             FileInputStream in=new FileInputStream(localFile);
 	             String fileName = localFile.getName();
 	             String tempFileName = fileName.replace(".xml", ".temp");
 	             isOk = ftpClient.storeFile(tempFileName, in);
 	             in.close();
 	             ftpClient.rename(tempFileName, fileName);
 	             ftpClient.changeToParentDirectory();
 	             
 	             if( !isOk ){
 	            	 return isOk;
 	             }
             }
             
             closeServer(ftpClient);
    	 }catch (Exception e) {  
             e.printStackTrace();  
             logger.error("文件读取错误。");
             isOk = false;
             return isOk;
         } 
    	 
    	 return isOk;
    }
    
    
    /** 
     * 检查FTP路径下有没有文件，如果有，复制到目标路径下，并修改文件名。 
     *  
     * @param ftpUserName 
     * @param ftpPassword 
     * @param ftpPath 
     * @param FTPServer 
     * @return 
     */  
    public static List<String[]> downloadFileFromFTP(String ftpUserName, String ftpPassword, String basePath, String[] ftpPaths, String ftpHost, int ftpPort, String localPath) {  
        FTPClient ftpClient = null;  
        List<String[]> result = new ArrayList<String[]>();
        
        int maxFileNum = 60;
        int i=0;
        try {  
            ftpClient = FtpUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName, ftpPort);  
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
            ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            boolean isOk = ftpClient.changeWorkingDirectory(basePath);
            isOk = ftpClient.changeWorkingDirectory("DOWNLOAD");
            
            for( String ftpPath : ftpPaths ){
	            if(!ftpClient.changeWorkingDirectory(ftpPath)){
	            	continue;
	            }
	            FTPFile[] fs = ftpClient.listFiles();
	            
	            if( fs != null && fs.length > 0 ){
	            	for(FTPFile ff:fs){
	            		String ftpFileName = ff.getName();
	            		String[] ftpFileNames = ftpFileName.split("[.]");
	            		
	            		if( ftpFileNames != null && "xml".equalsIgnoreCase(ftpFileNames[ftpFileNames.length - 1])){
	            			String dateString = String.valueOf(Calendar.getInstance().getTimeInMillis());
	            			String localFileName = ftpFileNames[0] + "_" + dateString + "." + ftpFileNames[ftpFileNames.length - 1];
	            			File localFile = new File(localPath+ File.separatorChar +localFileName); 
	            			
	            			OutputStream is = new FileOutputStream(localFile);   
	            			ftpClient.retrieveFile(ftpFileName, is); 
	            			is.close();
	            			// 偶尔有删除失败的情况，这里删2次。
	            			boolean isDeleted = ftpClient.deleteFile(ftpFileName);
	            			if( !isDeleted ){
	            				isDeleted = ftpClient.deleteFile(ftpFileName);
	            				if( !isDeleted ){
	            					logger.error("文件删除失败。"+ftpFileName);  
	            				}
	            			}
	            			result.add(new String[]{ftpPath, ftpFileName, localPath+File.separatorChar+localFileName});
	            			
	            			// 一次最多传30个文件
	            			i++;
	            			if( i >= maxFileNum ){
	            				return result;
	            			}
	            		}
	            	}
	            }
	            isOk = ftpClient.changeToParentDirectory();//回到上级目录
            }
            closeServer(ftpClient);
        }  catch (Exception e) {  
            e.printStackTrace();  
            logger.error("文件读取错误。");   
        } 
        return result;
    }
}
