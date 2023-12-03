package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import service.MybatisConnector;
import vo.GogekVO;

public class GogekDAO {
	SqlSessionFactory factory;
	
	public GogekDAO() {
		factory = MybatisConnector.getInstance().getFactory();
	}
	
	// single-ton pattern: 
	// 객체1개만생성해서 지속적으로 서비스하자
	static GogekDAO single = null;

	public static GogekDAO getInstance() {
		//생성되지 않았으면 생성
		if (single == null)
			single = new GogekDAO();
		//생성된 객체정보를 반환
		return single;
	}
	
	//모든 고객 조회하기
	public List<GogekVO> select(){
		SqlSession sqlSession = factory.openSession();
		List<GogekVO> list = sqlSession.selectList("gogek.gogek_list");
		sqlSession.close();
		return list;
	}
	
	//지역별로 고객 조회하기
	public List<GogekVO> select(String search){
		SqlSession sqlSession = factory.openSession();
		List<GogekVO> list = sqlSession.selectList("gogek.gogek_select",search);
		
		sqlSession.close();
		return list;
	}
}
