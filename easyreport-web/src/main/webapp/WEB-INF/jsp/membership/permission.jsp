<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>权限管理</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/membership/permission.js?v=${version}"></script>
</head>
<body class="easyui-layout" id="body-layout">
<!-- 左边tree -->
<div id="west" data-options="region:'west',split:true" title="系统模块" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="module-tree"></ul>
    </div>
</div>
<!-- 右边 -->
<div region="center" data-options="region:'center'">
    <div style="width: 100%; height: 99%">
        <div id="perm-datagrid"></div>
        <input id="modal-action" type="hidden" name="action" value=""/>
    </div>
</div>
<!-- dialog  -->
<div id="perm-dlg">
    <form id="perm-form" name="perm-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr>
                    <td>名称:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="name" id="name"
                                           data-options="required:true"
                                           style="width: 280px"/></td>
                </tr>
                <tr>
                    <td>编号:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="code" id="code"
                                           data-options="required:true"
                                           style="width: 280px"/></td>
                </tr>
                <tr>
                    <td>顺序:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="sequence" id="sequence" value="10"
                                           data-options="required:true,validType:'digit'"
                                           style="width: 280px"/></td>
                </tr>
                <tr>
                    <td>说明:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="comment"
                                           style="width: 280px"/>
                        <input id="moduleId" type="hidden" name="moduleId"/>
                        <input id="permId" type="hidden" name="id"/>
                    </td>
                </tr>
            </table>
        </center>
    </form>
</div>
<!-- tree右键菜单  -->
<div id="tree_ctx_menu" class="easyui-menu" style="width: 120px;">
    <div id="m-add" data-options="name:'add',iconCls:'icon-add'">增加</div>
</div>
</body>
</html>