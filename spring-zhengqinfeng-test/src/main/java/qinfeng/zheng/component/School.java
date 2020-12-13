package qinfeng.zheng.component;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/13 21:30
 * @dec
 */
public class School {
	private Integer id;
	private String name;

	public School(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
