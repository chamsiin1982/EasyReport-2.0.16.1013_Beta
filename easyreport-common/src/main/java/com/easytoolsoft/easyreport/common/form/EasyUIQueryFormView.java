package com.easytoolsoft.easyreport.common.form;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.form.control.HtmlCheckBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlCheckBoxList;
import com.easytoolsoft.easyreport.common.form.control.HtmlComboBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlDateBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlSelectOption;
import com.easytoolsoft.easyreport.common.form.control.HtmlTextBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlTreeComboBox;

/**
 * JQueryEasyUI控件报表查询参数表单视图
 */
public class EasyUIQueryFormView extends AbstractQueryParamFormView implements QueryParamFormView {
    @Override
    protected String getDateBoxText(HtmlDateBox dateBox) {
        String template = "<input id=\"%s\" name=\"%s\" type=\"text\" class=\"easyui-datebox\" required=\"true\" value=\"%s\" />";
        String easyuiText = String.format(template, dateBox.getName(), dateBox.getName(), dateBox.getValue());
        return 
        		dateBox.isNeedHidden()==true?        		
        		String.format("<span class=\"j-item\" style=\"display:none;\" ><label style=\"width: 120px;\">%s:</label>%s</span>", dateBox.getText(), easyuiText)
        		: String.format("<span class=\"j-item\"  ><label style=\"width: 120px;\">%s:</label>%s</span>", dateBox.getText(), easyuiText)
        		;
    }

    @Override
    protected String getTexBoxText(HtmlTextBox textBox) {
        String template = "<input id=\"%s\" name=\"%s\" type=\"text\" value=\"%s\" />";
        String easyuiText = String.format(template, textBox.getName(), textBox.getName(), textBox.getValue());
        return 
        		textBox.isNeedHidden()==true?    
        		String.format("<span class=\"j-item\" style=\"display:none;\" ><label style=\"width: 120px;\">%s:</label>%s</span>", textBox.getText(), easyuiText)
        		:String.format("<span class=\"j-item\"><label style=\"width: 120px;\">%s:</label>%s</span>", textBox.getText(), easyuiText)
        		;
    }

    @Override
    protected String getCheckBoxText(HtmlCheckBox checkBox) {
        String checked = checkBox.isChecked() ? "" : "checked=\"checked\"";
        return 
        		checkBox.isNeedHidden()==true?    
        		String.format("<input id=\"%s\" style=\"display:none;\" name=\"%s\" type=\"checkbox\" value=\"%s\" %s />%s",
                checkBox.getName(), checkBox.getName(), checkBox.getValue(), checked, checkBox.getText())
        		: String.format("<input id=\"%s\" name=\"%s\" type=\"checkbox\" value=\"%s\" %s />%s",
                        checkBox.getName(), checkBox.getName(), checkBox.getValue(), checked, checkBox.getText())
        		;
    }

    @Override
    protected String getComboBoxText(HtmlComboBox comboBox) {
        String multiple = comboBox.isMultipled() ? "data-options=\"multiple:true\"" : "";
        StringBuilder htmlText = new StringBuilder("");
        
        if(comboBox.isNeedHidden()==true){
        	htmlText.append(String.format("<span class=\"j-item\" style=\"display:none;\"><label style=\"width: 120px;\">%s:</label>", comboBox.getText()));
        } else {
        	htmlText.append(String.format("<span class=\"j-item\"><label style=\"width: 120px;\">%s:</label>", comboBox.getText()));
        }
        
        htmlText.append(String.format("<select id=\"%s\" name=\"%s\" class=\"easyui-combobox\" style=\"width: 200px;\" %s>",
                comboBox.getName(), comboBox.getName(), multiple));
        for (HtmlSelectOption option : comboBox.getValue()) {
            String selected = option.isSelected() ? "selected=\"selected\"" : "";
            htmlText.append(String.format("<option value=\"%s\" %s>%s</option>", option.getValue(), selected, option.getText()));
        }
        htmlText.append("</select>");
        htmlText.append("</span>");
        return htmlText.toString();
    }

    @Override
    protected String getCheckboxListText(HtmlCheckBoxList checkBoxList) {
        boolean isCheckedAll = true;
        StringBuilder htmlText = new StringBuilder("");
        
        if(checkBoxList.isNeedHidden()==true){
        	htmlText.append(String.format("<span class=\"j-item\" style=\"display:none;\" data-type=\"checkbox\"><label style=\"width: 120px;\">%s:</label>",
                checkBoxList.getText()));
        }else {
        	htmlText.append(String.format("<span class=\"j-item\" data-type=\"checkbox\"><label style=\"width: 120px;\">%s:</label>",
                    checkBoxList.getText()));
        }
        
        for (HtmlCheckBox checkBox : checkBoxList.getValue()) {
            if (!checkBox.isChecked())
                isCheckedAll = false;
            String checked = checkBox.isChecked() ? "checked=\"checked\"" : "";
            htmlText.append(String.format("<input name=\"%s\" type=\"checkbox\" value=\"%s\" data-name=\"%s\" %s/>%s &nbsp;",
                    checkBoxList.getName(), checkBox.getName(), checkBox.getText(), checked, checkBox.getText()));
        }
        htmlText.append(String.format("<input id=\"checkAllStatColumn\" name=\"checkAllStatColumn\" type=\"checkbox\" %s />全选</span>",
                isCheckedAll ? "checked=\"checked\"" : ""));
        return htmlText.toString();
    }

	@Override
	/**
	 * 添加 下拉树型控件，解决多级联动交互问题
	 * 
	 * 多级树加载，数据比较大时 会很缓慢
	 */
	protected String getTreeComboBoxText(HtmlTreeComboBox treeComboBox) {
        
		String multiple = treeComboBox.isMultipled() ? "data-options=\"multiple:true\"" : "";
        StringBuilder htmlText = new StringBuilder("");
        
        
        if(treeComboBox.isNeedHidden()==true){
        	htmlText.append(String.format("<span class=\"j-item\" style=\"display:none;\"><label style=\"width: 120px;\">%s:</label>", treeComboBox.getText()));
        }else {
        	htmlText.append(String.format("<span class=\"j-item\"><label style=\"width: 120px;\">%s:</label>", treeComboBox.getText()));
        }
        
        htmlText.append(String.format("<select id=\"%s\" name=\"%s\" class=\"easyui-combotree\" style=\"width: 200px;\" %s>",
        		treeComboBox.getName(), treeComboBox.getName(), multiple));
        /*for (HtmlSelectOption option : treeComboBox.getValue()) {
            String selected = option.isSelected() ? "selected=\"selected\"" : "";
            htmlText.append(String.format("<option value=\"%s\"  parent=\"%s\"  %s>%s</option>", 
            		option.getValue(),option.getParent(), selected, option.getText()));
        }*/
        htmlText.append("</select>");
        //通过隐藏域+前端渲染 解决树型数据渲染
        //组织树型 数据       
        htmlText.append(String.format("<input type=\"hidden\" id=\"%s_treecombo\" class=\"extend_treecombo\" value='%s' >",
        		treeComboBox.getName(),getJSONString(treeComboBox.getTreeRootValue(),treeComboBox.getValue())));
        htmlText.append("</span>");
        return htmlText.toString();
	}
	

	
	public static String getJSONString(String rootNodeId, List<HtmlSelectOption> nodes) {      
		
        return JSONObject.toJSON(getNodes(nodes,rootNodeId)).toString();
    }

	
	/**
	 * 树形数据组织
	 * @param nodes
	 * @param node
	 */
	 private static void getChildNodes(List<HtmlSelectOption> nodes, HtmlSelectOption node) {
	        List<HtmlSelectOption> childNodes = nodes.stream()
	                .filter(x -> x.getParent().equals(node.getValue()))
	                .collect(Collectors.toList());

	        for (HtmlSelectOption childNode : childNodes) {
	        	if(null==node.getChildren()) {
	        		node.setChild(new ArrayList<HtmlSelectOption>(0));
	        	}
	            node.getChildren().add(childNode);
	            getChildNodes(nodes, childNode);
	        }
	    }
	 
	 /**
	  * 根节点不能为空
	  * @param nodes
	  * @param rootId
	  * @return
	  */
	 public static List<HtmlSelectOption> getNodes(List<HtmlSelectOption> nodes, String rootId) {
	        if (nodes == null || nodes.size() == 0) {
	            return new ArrayList<>(0);
	        }

	        List<HtmlSelectOption> rootNodes = nodes.stream()
	                .filter(x ->  
	                			x.getParent()==null
	                					? (null==rootId?true:"".equals(rootId)?true:false)
	                					:x.getParent().equals(rootId) )
	                .collect(Collectors.toList());

	        for (HtmlSelectOption rootNode : rootNodes) {
	            getChildNodes(nodes, rootNode);
	        }
	        return rootNodes;
	    }
	 
	 
	 public static void main(String[] args) {
		 System.out.println(System.currentTimeMillis());
		 List<HtmlSelectOption> options=new ArrayList<HtmlSelectOption>();
		 
		 options.add(new HtmlSelectOption("江苏","8",""));
		 options.add(new HtmlSelectOption("南京","025","8"));
		 options.add(new HtmlSelectOption("无锡","0510","8"));
		 options.add(new HtmlSelectOption("苏州","0512","8"));
		 options.add(new HtmlSelectOption("常州","0514","8"));
		 
		 options.add(new HtmlSelectOption("鼓楼","025001","025"));
		 options.add(new HtmlSelectOption("建邺","025001","025"));
		 options.add(new HtmlSelectOption("雨花","025001","025"));
		 
		 options.add(new HtmlSelectOption("惠山","051001","0510"));
		 options.add(new HtmlSelectOption("滨湖","051002","0510"));
		 
		System.out.println( EasyUIQueryFormView.getJSONString("",options) );
		System.out.println(System.currentTimeMillis());
	 }
}
