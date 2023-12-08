package action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import util.Common;
import util.Paging;
import vo.BoardVO;

/**
 * Servlet implementation class BoardListAction
 */
@WebServlet("/board_list.do")
public class BoardListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		int nowPage = 1;
		String page = request.getParameter("page");
		
		//board_list.do  -> null
		//board_list,do?page=  ->empty
		
		if(page != null && !page.isEmpty()) {
			nowPage = Integer.parseInt(page);
		}
		
		//한페이지에 표시될 게시물의 시작과 끝번호 계산
		//page가 1이면 1~10까지 계산이 되어야 한다.
		//page가 2이면 11~20까지 계산되어야 한다.
		
		int start = (nowPage -1)*Common.Board.BLOCKLIST+1;
		int end = start+ Common.Board.BLOCKLIST-1;
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		
		//전체게시글 조회하기 X
		//페이지 번호에 따른 게시글 조회
		List<BoardVO> list = BoardDAO.getInstance().select(map);
		
		//전체 게시물 개수 조회
		int rowTotal = BoardDAO.getInstance().getRowTotal();
		
		//페이지 메뉴 생성하기
		
		String pageMenu = Paging.getPaging("board_list.do",
										   nowPage, //현재 페이지 번호
										   rowTotal, //전체 게시물 개수
										   Common.Board.BLOCKLIST, //한 페이지에 보여질 게시물 수
										   Common.Board.BLOCKPAGE); // 페이지 메뉴 수
		
		//조회수를 위해 저장해뒀던 "show"라는 정보를 세션에서 제거
		HttpSession session = request.getSession();
		session.removeAttribute("show");
		//request.getSession().removeAttribute("show");
		
		//바인딩
		request.setAttribute("list", list);
		request.setAttribute("pageMenu", pageMenu);
		//포워딩
		RequestDispatcher disp = request.getRequestDispatcher("board_list.jsp?page="+nowPage);
		disp.forward(request, response);
		
		
		
	}

}
