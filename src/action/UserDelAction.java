package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

/**
 * Servlet implementation class UserDelAction
 */
@WebServlet("/user_del.do")
public class UserDelAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//user_del.do?idx=25
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		//dao에 접근해서 데이터 제거하기
		int res = UserDAO.getInstance().delete(idx);
		
		String param = "no";
		if( res > 0) {
			param = "yes";
		}
		
		String result = String.format("[{'res':'%s'}]",param);
		
		response.getWriter().print(result);
	}

}
