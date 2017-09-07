package com.eai.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.List;

import org.xml.sax.InputSource;

import com.eai.common.EAIConstants;
import com.eai.common.exception.EAIException;
import com.eai.common.exception.FileException;
import com.eai.common.service.EAIFileConfigurationManager;

public class FileUtils {
	
	private FileUtils(){
	}
	
	private static String MISSING_INPUT = "O campo %1$s é de preenchimento obrigatório";
	private static String FILE_NOT_FOUND = "Ficheiro %1$s não encontrado ou não é um ficheiro válido.\r\nDirectório actual: %2$s";
	
	public static boolean existsFile(String filePath) {
		try{
			File file = new File(filePath);
			return file != null && file.exists();
		}catch (FileException e) {//ignore safe delete, don't ensure deletion
			return false;
		}
	}
	
	//region file management utils
	public static void deleteFileSafe(String filePath) {
		try{
			deleteFile(filePath);
		}catch (FileException e) {//ignore safe delete, don't ensure deletion
			EAILogger.error(e);
		}
	}
	public static void deleteFile(String filePath) {
		deleteFile(getFile(filePath));
	}
	public static void deleteFile(File file) {
		boolean result = file.delete();
		if( !result ){
			throw new FileException(String.format("Não foi possível apagar o ficheiro %1$s",file.getPath()));
		}
	}
	
	public static String getFileDirectoryPath(String filePath) {
		return getFileDirectoryPath(getFile(filePath));
	}
	public static String getFileDirectoryPath(File file) {//TODO: test
		if(file == null){
			return null;
		}
		return file.getParentFile().getPath();
	}
	
	public static String getCurrentFullPath() {
		try {
			return new File(".").getCanonicalPath();
		} catch (IOException e) {
			throw new FileException(e);
		}
	}
	
	public static File getFileOrDir(String filePath) {
		if(StringUtils.isNullOrEmpty(filePath)){
			throw new FileException(String.format(MISSING_INPUT, "filePath"));
		}
		
		File file = new File(filePath);
		return file;
	}
	
	public static List<String> getFilePathToURL(List<String> filePathLst) {
		if(filePathLst == null){
			return null;
		}
		for(int i = 0; i < filePathLst.size(); i++){
			filePathLst.add(i, getFilePathToURL(filePathLst.get(i)));
			filePathLst.remove(i+1);
		}
		return filePathLst;
	}
	public static String getFilePathToURL(String filePath) {
		if(filePath == null || filePath.contains("://")){//protocol path
			return filePath;
		}
		try{
			File file = getFile(filePath);
			return String.format("file:///%1$s", file.getCanonicalPath());
		}catch(Exception e){
			EAILogger.debug(e);
		}
		return filePath;
	}
	
	public static File getFile(String filePath) throws FileException{
		File file = getFileOrDir(filePath);
		if( !file.isFile()){
			throw new FileException(FileException.NOT_FOUND, String.format(FILE_NOT_FOUND, filePath, getCurrentFullPath()),
					" - Name:"+ file.getName() + "\r\n" +
					" - Parent:"+ file.getParent() + "\r\n" +
					" - Path:"+ file.getPath() + "\r\n" +
					" - IsFile:"+ file.isFile() + "\r\n" + 
					" - AbsolutePAth:"+ file.getAbsolutePath() + "\r\n");
		}
		return file;
	}
	
	public static String getPath(String originalPath, String... additionalPathLst){
		if( StringUtils.isNullOrEmpty(additionalPathLst) ){
			return getPath(originalPath, (String)null);
		}
		
		for( String additionalPath : additionalPathLst ){
			originalPath = getPath(originalPath, additionalPath);
		}
		return originalPath;
	}
	public static String getPath(String originalPath, String additionalPath){
		//additionalPath
		if( ! StringUtils.isNullOrEmpty( additionalPath ) ){
			for( String pathDelimiter : EAIConstants.PATH_DELIMITER ){
				additionalPath = additionalPath.replace(pathDelimiter, EAIConstants.DEFAULT_PATH_DELIMITER);
				if( additionalPath.startsWith( pathDelimiter ) ){
					if( additionalPath.length() == pathDelimiter.length() ){
						additionalPath = null;
					}else{
						additionalPath = additionalPath.substring(pathDelimiter.length());
					}
				}
			}
		}
		
		//originalPath
		if( ! StringUtils.isNullOrEmpty( originalPath ) ){
			for( String pathDelimiter : EAIConstants.PATH_DELIMITER ){
				originalPath = originalPath.replace(pathDelimiter, EAIConstants.DEFAULT_PATH_DELIMITER);
				if( originalPath.endsWith( pathDelimiter )){
					if( originalPath.length() == pathDelimiter.length() ){
						originalPath = null;
					}else{
						originalPath = originalPath.substring(0, originalPath.length() - pathDelimiter.length());
					}
				}
			}
		}
		
		if( StringUtils.isNullOrEmpty( originalPath ) && StringUtils.isNullOrEmpty( additionalPath ) ){
			return null;
		}
		
		if( StringUtils.isNullOrEmpty( originalPath ) ){
			return additionalPath;
		}
		
		if( StringUtils.isNullOrEmpty( additionalPath ) ){
			return originalPath;
		}
		
		return originalPath + EAIConstants.DEFAULT_PATH_DELIMITER + additionalPath ;
	}
	
	public static void moveFile(String sourceFilePath, String destinationFilePath){
		copyFile(sourceFilePath, destinationFilePath);
		deleteFile(sourceFilePath);
	}
	public static void moveFile(File sourceFile, File destFile){
		copyFile(sourceFile, destFile);
		deleteFile(sourceFile);
	}
	
	public static void createFile(String destinationFilePath, String content){
		createFile(destinationFilePath, content, false);
	}
	public static void createFile(String destinationFilePath, String content, boolean append){
		OutputStreamWriter osw = null;
		try{
			osw = getFileWriter(destinationFilePath,append);
			osw.write(content);
		}catch (EAIException e) {
			throw e;
		}catch (Throwable e) {
			throw new FileException(e);
		}finally{
			forceClose(osw);
		}
	}
	
	public static void createDir(String dirPath){
		File dir = FileUtils.getFileOrDir(dirPath);
		if(!dir.exists()){
			dir.mkdir();
		}
	}
	
	public static void copyFile(String sourceFilePath, String destinationFilePath){
		File destFile = new File(destinationFilePath);
		copyFile(getFile(sourceFilePath), destFile);
	}
	public static void copyFile(File sourceFile, File destFile){
	    FileChannel source = null;
	    FileChannel destination = null;
	    FileInputStream souceFileInputStream = null;
	    FileOutputStream destFileInputStream = null;
	    try {
	    	if(!destFile.exists()) {
	    		if(!destFile.getParentFile().exists()) {
	    			destFile.getParentFile().mkdirs();
	    		}
		        destFile.createNewFile();
		    }
	    	souceFileInputStream = getFileInputStream(sourceFile);
	    	destFileInputStream = getFileOutputStream(destFile);
	    	
	        source = souceFileInputStream.getChannel();
	        destination = destFileInputStream.getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }catch (Exception e){
			throw new FileException(String.format("Error while copying from file %1$s to %2$s\r\n%3$s", sourceFile, destFile, e.getMessage()));
		}finally{
			FileUtils.forceClose(source);
			FileUtils.forceClose(destination);
			FileUtils.forceClose(souceFileInputStream);
			FileUtils.forceClose(destFileInputStream);
	    }
	}
	//endregion
	
	//region Input Source
	public static InputSource getInputSource(String content) throws FileException{
		return getInputSource(content, EAIFileConfigurationManager.getDefaultEncoding());
	}
	public static InputSource getInputSource(String content, String encoding) throws FileException{
		try{
			InputSource is = new InputSource();
			is.setEncoding(encoding);
			is.setCharacterStream( new StringReader(content));
			return is; 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	//endregion
	
	//region Input/Output File Stream  - Require dependent close
	/**
	 * 
	 * @param filePath
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static FileInputStream getFileInputStream(String filePath) throws FileException{
		try{
			return getFileInputStream( getFile( filePath )  ); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	/**
	 * 
	 * @param file
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static FileInputStream getFileInputStream(File file) throws FileException{
		try{
			return new FileInputStream( file  ); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	
	/**
	 * 
	 * @param filePath
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static FileOutputStream getFileOutputStream(String filePath) throws FileException{
		try{
			return getFileOutputStream( getFile( filePath )  ); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	/**
	 * 
	 * @param file
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static FileOutputStream getFileOutputStream(File file) throws FileException{
		try{
			return new FileOutputStream( file  ); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	//endregion
	
	//region Input/Output Stream Reader/Writer  - Require dependent close
	/**
	 * 
	 * @param filePath
	 * @return InputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static InputStreamReader getFileReader(String filePath) throws FileException{
		return getFileReader(filePath, EAIFileConfigurationManager.getDefaultEncoding());
	}
	/**
	 * 
	 * @param filePath
	 * @param encoding
	 * @return InputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static InputStreamReader getFileReader(String filePath, String encoding) throws FileException{
		try{
			return new InputStreamReader(new FileInputStream( getFile( filePath )  ), encoding); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	
	/**
	 * 
	 * @param destinationFilePath
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 */
	public static OutputStreamWriter getFileWriter(String destinationFilePath){
		return getFileWriter(destinationFilePath, false);
	}
	/**
	 * 
	 * @param destinationFilePath
	 * @param append
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 */
	public static OutputStreamWriter getFileWriter(String destinationFilePath, boolean append){
		return getFileWriter(destinationFilePath, append, EAIFileConfigurationManager.getDefaultEncoding());
	}
	/**
	 * 
	 * @param destinationFilePath
	 * @param encoding
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 */
	public static OutputStreamWriter getFileWriter(String destinationFilePath, String encoding){
		return getFileWriter(destinationFilePath, false, encoding);
	}
	/**
	 * 
	 * @param destinationFilePath
	 * @param append
	 * @param encoding
	 * @return OutputStream - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 */
	public static OutputStreamWriter getFileWriter(String destinationFilePath, boolean append, String encoding){
		try {
			File file = new File(destinationFilePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(destinationFilePath, append),encoding);
			return out;
		} catch (Exception e) {
			throw new FileException(e);
		}
	}
	//endregion

	//region File Channel
	/**
	 * 
	 * @param filePath
	 * @return FileChannel - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static FileChannel getFileChannel(String filePath) throws FileException{
		try{
			return getFileChannel( getFile( filePath )  ); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	/**
	 * 
	 * @param file
	 * @return FileChannel - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static FileChannel getFileChannel(File file) throws FileException{
		try{
			return getFileChannel( getFileInputStream(file), true ); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	
	/**
	 * 
	 * @param fis
	 * @return FileChannel - Should be closed by dependents is closed ({@link com.eai.common.utils.FileUtils#forceClose forceClose})
	 * @throws FileException
	 */
	public static FileChannel getFileChannel(FileInputStream fis) throws FileException{
		try{
			return getFileChannel( fis, false ); 
		}catch (Exception e) {
			throw new FileException(e);
		}
	}
	
	private static FileChannel getFileChannel(FileInputStream fis, boolean close) throws FileException{
		try{
			return fis == null ? null : fis.getChannel(); 
		} catch (Exception e) {
			throw new FileException(e);
		} finally {
			if( close ){
				forceClose(fis);
			}
		}
	}
	//endregion
	
	public static String getFileAsString(String filePath) {
		return getFileAsString(filePath, EAIFileConfigurationManager.getDefaultEncoding());
	}
	public static String getFileAsString(String filePath, String encoding) {
		return getFileReaderAsString(getFileReader(filePath, encoding), encoding, true);
	}
	
	public static String getFileReaderAsString(InputStreamReader in) {
		return getFileReaderAsString(in, EAIFileConfigurationManager.getDefaultEncoding());
	}
	public static String getFileReaderAsString(InputStreamReader in, String encoding) {
		return getFileReaderAsString( in, encoding, false );
	}
	private static String getFileReaderAsString(InputStreamReader isr, String encoding, boolean close) {
		String result = null;
	    ByteArrayOutputStream buf = new ByteArrayOutputStream();
	    int bytesRead;
		try {
			bytesRead = isr.read();
			while(bytesRead != -1) {
				byte b = (byte)bytesRead;
				buf.write(b);
				bytesRead = isr.read();
			}        
			result = buf.toString(encoding);
		}catch (Exception e) {
			throw new FileException(e);
		}finally{
			FileUtils.forceClose(buf);
			if( close ){
				FileUtils.forceClose(isr);
			}
		}
		return result;
	}
	
	// region forceClose - Exception and nullable safe 
	public static void forceClose(Writer w){
		if( w != null ){
			try{
				w.close();
			}catch (Exception e) {
				EAILogger.error(e);
			}
		}
	}
	public static void forceClose(Reader r){
		if( r != null ){
			try{
				r.close();
			}catch (Exception e) {
				EAILogger.error(e);
			}
		}
	}
	public static void forceClose(OutputStream os){
		if( os != null ){
			try{
				os.close();
			}catch (Exception e) {
				EAILogger.error(e);
			}
		}
	}
	public static void forceClose(InputStream is){
		if( is != null ){
			try{
				is.close();
			}catch (Exception e) {
				EAILogger.error(e);
			}
		}
	}
	public static void forceClose(FileChannel fc){
		if( fc != null ){
			try{
				fc.close();
			}catch (Exception e) {
				EAILogger.error(e);
			}
		}
	}
	// endregion
}
