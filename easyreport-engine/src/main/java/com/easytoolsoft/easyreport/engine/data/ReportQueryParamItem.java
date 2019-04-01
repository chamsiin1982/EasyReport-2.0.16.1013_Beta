package com.easytoolsoft.easyreport.engine.data;

public class ReportQueryParamItem {
    private String name;
    private String text;
    private String parent;
    private boolean selected;

    public ReportQueryParamItem() {
    }

    public ReportQueryParamItem(String name, String text) {
        this.name = name;
        this.text = text;
    }
    
    public ReportQueryParamItem(String name, String text,String parent) {
        this.name = name;
        this.text = text;
        this.parent=parent;
    }

    public ReportQueryParamItem(String name, String text, boolean selected) {
        this(name, text);
        this.selected = selected;
    }
    
    public ReportQueryParamItem(String name, String text,String parent, boolean selected) {
        this(name, text,parent);
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
