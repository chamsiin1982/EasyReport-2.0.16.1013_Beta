package com.easytoolsoft.easyreport.common.form.control;

import java.util.List;

public class HtmlTreeComboBox extends HtmlComboBox {

	public HtmlTreeComboBox(String name, String text, List<HtmlSelectOption> value) {
		super(name, text, value);
		this.type="treecombox";
	}
}
