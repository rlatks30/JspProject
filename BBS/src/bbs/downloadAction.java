package bbs;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/downloadAction")
public class downloadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String filename = request.getParameter("file");
		String saveDirectory = this.getServletContext().getRealPath("/upload");


		// 3] 파일 크기를 얻기 위한 파일객체 생성 (다운로드시 프로그레스바를 표시하기 위함)
		// File.separator 는 윈도우면 \(원표시) 리눅스면 /(슬래시)로 표시해줌
		File file = new File(filename);
		long length = file.length();
		System.out.println("saveDirectory" + saveDirectory);
		System.out.println("filename" + filename);
		System.out.println("파일은 뭐냐면" + file);
		
		response.setContentType("application/octet-stream");

		response.setContentLengthLong(length);

		boolean isIe = request.getHeader("user-agent").toUpperCase().indexOf("MSIE") != -1
				|| request.getHeader("user-agent").indexOf("11.0") != -1;
		if (isIe) { // 인터넷 익스플로러
			filename = URLEncoder.encode(filename, "UTF-8");
		} else { // 기타 웹브라우저
			filename = new String(filename.getBytes("UTF-8"), "8859_1");
			System.out.println("크롬인강");
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		//out.clear();
		//out = pageContext.pushBody();

		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

		int data;
		while ((data = bis.read()) != -1) {
			bos.write(data);
			bos.flush();
		}

// 8] 스트림 닫기
		bis.close();
		bos.close();
	}
}