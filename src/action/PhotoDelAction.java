package action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PhotoDAO;

/**
 * Servlet implementation class PhotoDelAction
 */
@WebServlet("/photo_del.do")
public class PhotoDelAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//photo_del.do?idx=26&filename=aaa
		request.setCharacterEncoding("utf-8");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String filename = request.getParameter("filename");
		
		//idx에 해당하는 글 삭제하기
		int res = PhotoDAO.getInstance().delete(idx);
		
		//절대경로 찾기
		String web_path = "/upload/";
		ServletContext app = request.getServletContext();
		String path = app.getRealPath(web_path);
		
		if( res > 0) {
			File f = new File(path,filename);
			if(f.exists()) {
				f.delete();
			}
		}
		String param = "no";
		if(res > 0) {
			param = "yes";
		}
		//결과값인 param을 json구조로 포장
		String resultStr = String.format("[{'param' : '%s'}] ",param);
		
		//콜백메서드로 복귀
		response.getWriter().print(resultStr);
	}
	

}
