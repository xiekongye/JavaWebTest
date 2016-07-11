package com.xiekongye.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet(
		description = "?????????", 
		urlPatterns = { "/DownloadServlet" }, 
		initParams = { 
				@WebInitParam(name = "DownloadPath", value = "/download/", description = "?????????")
		})
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		this.config = config;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		//downloadFileByOutputStream(response);
		downloadFileByAttachment(response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * 下载文件(无法下载中文名的文件)
	 * @author xiekongye
	 * @param response
	 * */
	private void downloadFileByOutputStream(HttpServletResponse response) throws FileNotFoundException,IOException{
		ServletContext context = this.config.getServletContext();
		String relativePathString = this.config.getInitParameter("downloadPath");//获取配置文件中的文件下载相对路径
		String realPathString = context.getRealPath(relativePathString + "1.jpg");
		//int index = realPathString.lastIndexOf("/");
		String fileNameString = realPathString.substring(realPathString.lastIndexOf("/" + 1));
		
		response.setHeader("content-disposition", "attachment;filename" + fileNameString);
		FileInputStream inputStream = new FileInputStream(realPathString);//获取文件输入流
		OutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer,0,len);
		}
		inputStream.close();
		
	}
	
	/**
	 * 下载一个文件
	 * @author xiekongye
	 * @param response HttpServletResponse
	 * @throws FileNotFoundException,IOException
	 * */
	private void downloadFileByAttachment(HttpServletResponse response) throws FileNotFoundException,IOException{
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=JavaScript Program Design.pdf");
		OutputStream os = response.getOutputStream();
		ServletContext context = this.config.getServletContext();
		URL url = context.getResource("/files/JavaScript Program Design.pdf");
		InputStream is = url.openStream();
		byte[] bytearray = new byte[1024];
		int bytesread = 0;
		while((bytesread = is.read(bytearray)) != -1){
			//没有读到文件结尾
			os.write(bytearray);
		}
		os.flush();
		os.close();
	}
}
