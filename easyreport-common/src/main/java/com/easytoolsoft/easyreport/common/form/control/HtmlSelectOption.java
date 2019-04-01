package com.easytoolsoft.easyreport.common.form.control;

import java.util.List;

public class HtmlSelectOption {
	
	private String id;
    private String text;
    private String value;
    
    //树形数据 添加
    private String parent;
    private boolean selected;
    
    
	//tree的下级节点
	private List<HtmlSelectOption> children;
	
	
	
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HtmlSelectOption(String text, String value) {
    	this.id=value;
        this.text = text;
        this.value = value;
    }
    
    public HtmlSelectOption(String text, String value,String parent) {
        this(text, value);
        this.parent=parent;
    }

    public HtmlSelectOption(String text, String value, boolean selected) {
        this(text, value);
        this.selected = selected;
    }
    public HtmlSelectOption(String text, String value,String parent, boolean selected) {
        this(text, value,selected);
        this.parent=parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
    	this.id=value;
        this.value = value;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	
    public List<HtmlSelectOption> getChildren() {
		return children;
	}

	public void setChild(List<HtmlSelectOption> child) {
		this.children = child;
	}
}
