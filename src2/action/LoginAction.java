package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.MemberVO;

/**
 * Servlet implementation class LoginAction
 */
@WebServlet("/login.do")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//login.do?id=aaaa&pw=aaa
		String id = request.getParameter("id");
		String pw = request.getParameter("pwd");
		
		//id가 틀리면 id가 틀렸다.
		//pw가 틀리면 pw가 틀렸다.
		
		MemberVO vo = MemberDAO.getInstance().selectOne(id);
		
		String param = "";
		String resultStr = "";
		
		//vo null인 경우 id자체가 DB에 존재하지 않는다는 의미
		if(vo == null) {
			param ="no_id";
			resultStr = String.format("[{'param':'%s'}]", param);
			response.getWriter().print(resultStr);
			return;
		}
		
		//내가 입력한 비밀번호와 DB에서 넘어온 비밀번호가 일치하지 않을 때
		if(!vo.getPwd().equals(pw)) {
			param ="no_pw";
			resultStr = String.format("[{'param':'%s'}]", param);
			response.getWriter().print(resultStr);
			return;
		}
		
		//아이디와 비밀번호 체크의 문제가 없다면 세션에 바인딩을 한다.
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3600);
		session.setAttribute("vo", vo);
		
		
		//로그인 성공한 경우
		param ="clear";
		resultStr = String.format("[{'param':'%s'}]", param);
		response.getWriter().print(resultStr);
	}

}
