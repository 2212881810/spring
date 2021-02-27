package qinfeng.zheng.selfEditor;

import java.beans.PropertyEditorSupport;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/27 20:51
 * @dec  自定义一个属性编辑器
 */
public class AddressPropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] s = text.split("_");
		Address address = new Address();
		address.setProvince(s[0]);

		address.setCity(s[1]);
		address.setTown(s[2]);
		super.setValue(address);
	}
}
