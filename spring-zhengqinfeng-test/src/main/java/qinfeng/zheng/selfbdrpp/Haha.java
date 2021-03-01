package qinfeng.zheng.selfbdrpp;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 16:38
 * @dec
 */
public class Haha {
	private String name;
	private Integer age;


	public Haha() {
		System.out.println("create haha...");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Haha{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
