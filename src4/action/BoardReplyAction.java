package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.BoardVO;

/**
 * Servlet implementation class BoardReplyAction
 */
@WebServlet("/reply.do")
public class BoardReplyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//reply.do?idx=5&subject=답글&name=답글+쓰는+사람&content=1111&pwd=1111
		request.setCharacterEncoding("utf-8");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		String ip = request.getRemoteAddr();
		
		//같은 ref를 가지고 있는 데이터들 중에서 지금 내가 추가하려고 하는
		//step보다 큰 애들을 +1을 해놔야 하기 떄문에 insert를 먼저하지 않는다.
		
		BoardDAO dao = BoardDAO.getInstance();
		
		//기준글의 idx를 이용히여 답글을 달고싶은 게시글의 정보를 가져온다.
		BoardVO base_vo = dao.selectOne(idx);
		
		//기준글의 step보다 큰 값들에 대해 + 1 처리하기
		int res = dao.update_step(base_vo);
		
		//댓글 객체
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		vo.setIp(ip);
		
		vo.setRef(base_vo.getRef());
		vo.setStep(base_vo.getStep()+1);
		vo.setDepth(base_vo.getDepth()+1);
		
		//답글 실제로 등록하기
		int res1 = dao.reply(vo);
		
		if(res1 > 0) {
			int page = Integer.parseInt(request.getParameter("page"));
			response.sendRedirect("board_list.do?page="+page);
		}
	}

}
