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


		// 3] ���� ũ�⸦ ��� ���� ���ϰ�ü ���� (�ٿ�ε�� ���α׷����ٸ� ǥ���ϱ� ����)
		// File.separator �� ������� \(��ǥ��) �������� /(������)�� ǥ������
		File file = new File(filename);
		long length = file.length();
		System.out.println("saveDirectory" + saveDirectory);
		System.out.println("filename" + filename);
		System.out.println("������ ���ĸ�" + file);
		
		response.setContentType("application/octet-stream");

		response.setContentLengthLong(length);

		boolean isIe = request.getHeader("user-agent").toUpperCase().indexOf("MSIE") != -1
				|| request.getHeader("user-agent").indexOf("11.0") != -1;
		if (isIe) { // ���ͳ� �ͽ��÷η�
			filename = URLEncoder.encode(filename, "UTF-8");
		} else { // ��Ÿ ��������
			filename = new String(filename.getBytes("UTF-8"), "8859_1");
			System.out.println("ũ���ΰ�");
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

// 8] ��Ʈ�� �ݱ�
		bis.close();
		bos.close();
	}
}