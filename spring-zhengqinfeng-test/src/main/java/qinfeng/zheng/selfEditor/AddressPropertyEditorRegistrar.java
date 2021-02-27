package qinfeng.zheng.selfEditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/27 20:53
 * @dec 自定义一个属性注册器，然后将自定义的属性编辑器 {@link AddressPropertyEditor} 注册到spring中
 */
public class AddressPropertyEditorRegistrar implements PropertyEditorRegistrar {
	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		// 当属性是Address类时，会调用AddressPropertyEditor这个属性编辑器进行处理
		registry.registerCustomEditor(Address.class, new AddressPropertyEditor());
	}
}
