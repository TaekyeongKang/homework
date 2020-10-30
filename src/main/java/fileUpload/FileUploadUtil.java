package fileUpload;



public class FileUploadUtil {
	
	
	//form-data; name="img"; filename="moo.jpg"
	// ==> moo.jpg
	
	// FileUploadUtilTest 클래스 만들어서 테스트
	// ->paring 잘 됐는지 확인
	public static String getFilename(String contentDisposition) {
		String fileName = "";
		String[] contents = contentDisposition.split("; ");
		for(int i =0; i<contents.length; i++) {
			if(contents[i].contains("filename=")) {
				String[] contentFilename = contents[i].split("=");
				fileName = contentFilename[1].substring(1,contentFilename[1].lastIndexOf("\"")); 
			}
		}
		return fileName;
	}
	
	// filename : sally.png => png
	public static String getExtension(String filename) {
		/* 내코드
			String extension = filename.substring(filename.lastIndexOf(".")+1);
			return extension;
		 */
		
		// sem 코드
		if(filename == null || filename.indexOf(".") == -1) {
			return "";
		}
		else {
			return filename.split("\\.")[1];
		}
	}
}
