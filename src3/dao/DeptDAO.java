package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import service.MybatisConnector;
import vo.DeptVO;

public class DeptDAO {
	
	SqlSessionFactory factory;
	
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
	
	public DeptDAO() {
		//기본생성자에서 MybatisConnector를 호출하여 factory를 채워주자
		factory = MybatisConnector.getInstance().getFactory();
	}
	
	//부서테이블 전체 조회
	public List<DeptVO> select(){
		//fatory는 어떤 db로 연결하고 어떤 mapper로 접근해야 하는 것 까지만 알고있다.
		//그 정보를 가지고 실제로 쿼리문을 요청하는것은 SqlSession객체가 한다.
		SqlSession sqlSession = factory.openSession();
		List<DeptVO> list = sqlSession.selectList("dept.dept_list");
		
		sqlSession.close();
		return list;
	}
	
	
}
