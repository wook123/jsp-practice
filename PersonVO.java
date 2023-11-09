package vo;

public class PersonVO {
	//VO(value Object) : 한 개 또는 그 이상의 속성들을 묶은 객체를 의미한다.
	//DB를 연동하고 사람의 정보를 DB에서 가져왔다고 가정하면 결과적으로 jsp부분의 body부분에서
	//출력을 하게 된다.
	
	private String name;//이름
	private int age;//나이
	private String tel;//전화번호
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
