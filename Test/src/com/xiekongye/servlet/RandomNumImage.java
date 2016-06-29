package com.xiekongye.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RandomNumImage
 */
@WebServlet(description = "????????", urlPatterns = { "/RandomNumImage" })
public class RandomNumImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomNumImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("refresh", "5");//5秒钟刷新一次
		
		//1.在内存中创建一张图片
		BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);
		//2.得到图片
		Graphics2D graphics2d = (Graphics2D) image.getGraphics();
		//graphics2d.setBackground(Color.BLUE);
		//graphics2d.setColor(Color.ORANGE);
		graphics2d.fillRect(0, 0, 80, 20);
		//3.向图片上写数据
		graphics2d.setFont(new Font(null, Font.BOLD, 20));
		graphics2d.drawString(makeNum(), 0, 20);
		//4.设置响应头控制浏览器以图片的方式打开
		response.setContentType("image/jpeg");
		//5.不缓存图片
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		//6.将图片写给浏览器
		ImageIO.write(image, "jpg", response.getOutputStream());
	}

	private String makeNum() {
		// TODO Auto-generated method stub
		Random random = new Random();
        String num = random.nextInt(9999999)+"";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 7-num.length(); i++) {
            sb.append("0");
        }
        num = sb.toString()+num;
        return num;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
