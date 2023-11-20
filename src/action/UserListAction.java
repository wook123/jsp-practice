package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import vo.UserVO;

/**
 * Servlet implementation class UserListAction
 */
@WebServlet("/user_list.do")
public class UserListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//회원목록 얻어오기
		List<UserVO> list = UserDAO.getInstance().selectList();
		
		//request객체에 list를 바인딩하기
		request.setAttribute("list", list);
		
		//user_list.jsp로 포워딩하기
		RequestDispatcher disp = request.getRequestDispatcher("user_list.jsp");
		disp.forward(request, response);
	}

}
