package action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.PhotoDAO;
import vo.PhotoVO;

/**
 * Servlet implementation class PhotoInsertAction
 */
@WebServlet("/insert.do")
public class PhotoInsertAction extends HttpServlet {
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
    */
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // insert.do?title=aaaa&pwd=1111&photo=&^&^&^
      String web_path = "/upload/";
      ServletContext application = request.getServletContext();
      String path = application.getRealPath(web_path);
      System.out.println(path);
      
      //파일 업로드 최대 용량
      int max_size = 1024 * 1024 * 100;
      
      //파일업로드를 할 수 있는 클래스 생성
      MultipartRequest mr = new MultipartRequest(request, path,max_size,"utf-8", new DefaultFileRenamePolicy());
      
      String filename="";
      File f = mr.getFile("photo");
      if(f != null) {
         filename= f.getName(); //업로드된 파일의 실제 파일명
      }
      
      String title = mr.getParameter("title");
      String pwd = mr.getParameter("pwd");
      String ip = request.getRemoteAddr();
      
      PhotoVO vo = new PhotoVO();
      vo.setTitle(title);
      vo.setFilename(filename);
      vo.setPwd(pwd);
      vo.setIp(ip);
      
      int res = PhotoDAO.getInstance().insert(vo);
      
      if(res >=1) {
         response.sendRedirect("list.do");
      }

   }

}
