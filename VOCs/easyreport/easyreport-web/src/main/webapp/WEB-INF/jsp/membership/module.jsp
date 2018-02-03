<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>模块管理</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/membership/module.js?v=${version}"></script>
</head>
<body class="easyui-layout" id="body-layout">
<!-- 左边tree -->
<div id="west" data-options="region:'west',split:true" title="模块树" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="module-tree"></ul>
    </div>
</div>
<div region="center" data-options="region:'center'">
    <div style="width: 100%; height: 99%">
        <div id="module-datagrid"></div>
        <input id="modal-action" type="hidden" name="action" value=""/>
    </div>
</div>
<div id="module-dlg">
    <form id="module-form" name="module-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr id="tr-parent-module-name">
                    <td>父模块:</td>
                    <td colspan="3"><label id="parent-module-name"></label></td>
                </tr>
                <tr>
                    <td>名称:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="name" id="name" value=""
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>编码:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="code" id="code" value="0x"
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>icon:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="icon" id="icon" value=""
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>Url:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="url" id="url" value=""
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>是否外链:</td>
                    <td><select class="easyui-combobox" id="linkType" name="linkType" style="width: 138px">
                        <option selected="selected" value="0">否</option>
                        <option value="1">是</option>
                    </select></td>
                    <td>Url Target:</td>
                    <td><select class="easyui-combobox" id="target" name="target" style="width: 138px">
                        <option selected="selected" value="0">_blank</option>
                        <option value="1">_self</option>
                    </select></td>
                </tr>
                <tr>
                    <td>Url参数:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="params" name="params"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td><select class="easyui-combobox" id="status" name="status" style="width: 148px">
                        <option selected="selected" value="1">启用</option>
                        <option value="0">禁用</option>
                    </select></td>
                    <td>排序:</td>
                    <td><input class="easyui-textbox" type="text" id="sequence" name="sequence"
                               data-options="required:true,validType:'digit'" style="width: 138px"/></td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="comment" name="comment"
                                           style="width: 380px"/>
                        <input id="parentId" type="hidden" name="parentId" value="0"/>
                        <input id="moduleId" type="hidden" name="id" value="0"/>
                    </td>
                </tr>
            </table>
        </center>
    </form>
</div>
<!-- tree右键菜单  -->
<div id="tree_ctx_menu" class="easyui-menu" style="width: 120px;">
    <div id="m-add" data-options="name:'add',iconCls:'icon-add'">增加</div>
    <div id="m-edit" data-options="name:'edit',iconCls:'icon-edit'">修改</div>
    <div id="m-remove" data-options="name:'remove',iconCls:'icon-remove'">删除</div>
</div>
</body>
</html>