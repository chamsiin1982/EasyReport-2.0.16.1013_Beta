<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>系统配置</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/sys/conf.js?v=${version}"></script>
</head>
<body class="easyui-layout" id="body-layout">
<!-- 左边tree -->
<div id="west" data-options="region:'west',split:true" title="配置分类" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="dict-tree"></ul>
    </div>
</div>
<!-- 右边 -->
<div region="center" data-options="region:'center'">
    <div id="configDictDiv" style="width: 100%; height: 99%">
        <div id="dict-datagrid"></div>
        <input id="modal-action" type="hidden" name="action" value=""/>
    </div>
</div>
<!-- 新增与修改配置字典dialog  -->
<div id="dict-dlg">
    <form id="dict-form" name="dict-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr id="confParentNameDiv">
                    <td>父节点:</td>
                    <td colspan="3"><label id="confParentName"></label></td>
                </tr>
                <tr>
                    <td>名称:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="name" id="name"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>键(Key):</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="key" id="key"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>值(Value):</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="value" id="value"
                                           data-options="required:true,multiline:true"
                                           style="width: 380px;height:150px"/></td>
                </tr>
                <tr>
                    <td>顺序:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="sequence" id="sequence" value="10"
                                           data-options="required:true,validType:'digit'"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>说明:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="comment"
                                           style="width: 380px"/>
                        <input id="confPid" type="hidden" name="parentId" value="0"/>
                        <input id="confId" type="hidden" name="id" value="0"/>
                    </td>
                </tr>
            </table>
        </center>
    </form>
</div>
<!-- 查找树节点弹框  -->
<div id="search-node-dlg" title="查找树节点">
    <div id="toolbar" class="toolbar">
        选项:<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 120px">
        <option value="name">名称</option>
        <option value="key">对应键</option>
        <option value="value">对值</option>
    </select> 关键字:<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
        <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search"> 查找 </a>
    </div>
    <div style="height: 86%; padding: 2px">
        <div id="search-node-result"></div>
    </div>
</div>
<!-- tree右键菜单 -->
<div id="tree_ctx_menu" class="easyui-menu" style="width: 120px;">
    <div id="m-add" data-options="name:'add',iconCls:'icon-add'">增加</div>
    <div id="m-edit" data-options="name:'edit',iconCls:'icon-edit'">修改</div>
    <div id="m-remove" data-options="name:'remove',iconCls:'icon-remove'">删除</div>
    <div class="menu-sep"></div>
    <div id="m-search" data-options="name:'find',iconCls:'icon-search'">查找</div>
</div>
</body>
</html>