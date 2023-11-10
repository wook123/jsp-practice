package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.net.aso.r;
import service.DBservice;
import vo.DeptVO;

public class DeptDAO {
	//쿼리문을 작성하고 데이터를 가져오는 역할을 하는 클래스
	//DAO(Data Access Object)
	//DTO(Data Transfer Object)
	// single-ton pattern: 
	// 객체1개만생성해서 지속적으로 서비스하자
	static DeptDAO single = null;

	public static DeptDAO getInstance() {
		//생성되지 않았으면 생성
		if (single == null)
			single = new DeptDAO();
		//생성된 객체정보를 반환
		return single;
	}
	
	//모든 부서를 조회
	public List<DeptVO> selectList(){
		
		List<DeptVO> list = new ArrayList<DeptVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from dept";
		
		try {
			//1. Connection정보를 얻어온다
			conn = DBservice.getInstance().getConnection();
			
			//2. 명령처리객체정보를 얻어오기
			pstmt = conn.prepareStatement(sql);
			
			//3. 결과행 처리객체 얻어오기
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				DeptVO vo = new DeptVO();
				
				vo.setDeptno(rs.getInt("deptno"));
				vo.setDname(rs.getString("dname"));
				vo.setLoc(rs.getString("loc"));
				
				list.add(vo);
	
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return list;
	}
	
}
