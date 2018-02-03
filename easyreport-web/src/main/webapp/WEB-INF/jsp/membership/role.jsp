<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>角色管理</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/membership/role.js?v=${version}"></script>
</head>
<body class="easyui-layout">
<div id="toolbar" class="toolbar">
    角色名:<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
    <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search"> 搜索 </a>
    <input id="modal-action" type="hidden" name="action" value=""/>
</div>
<div style="height: 93%; padding: 2px">
    <div id="role-datagrid"></div>
</div>
<div id="role-dlg">
    <form id="role-form" name="role-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr>
                    <td>角色名称:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="name" id="name"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>角色代号:</td>
                    <td><input class="easyui-textbox" type="text" name="code" id="code"
                               data-options="required:true,validType:'code'"/></td>
                    <td>系统角色:</td>
                    <td><select class="easyui-combobox" id="isSystem" name="isSystem" style="width: 148px">
                        <option selected="selected" value="0">否</option>
                        <option value="1">是</option>
                    </select></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td><select class="easyui-combobox" id="status" name="status" style="width: 148px">
                        <option selected="selected" value="1">启用</option>
                        <option value="0">禁用</option>
                    </select></td>
                    <td>排序：</td>
                    <td><input class="easyui-textbox" type="text" name="sequence" id="sequence" value="10"
                               data-options="required:true,validType:'digit'"/></td>
                </tr>
                <tr>
                    <td>描述:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="comment"
                                           data-options="required:true" style="width: 380px"/></td>
                    <input id="roleId" type="hidden" name="id"/>
                </tr>
            </table>
        </center>
    </form>
</div>
<div id="perm-tree-dlg" title="给角色授权">
    <form id="perm-tree-form" name="perm-tree-form" method="post">
        <div id="perm-tree-dlg-layout" class="easyui-layout" style="width: 542px; height: 381px;">
            <!-- 左边tree -->
            <div id="west" data-options="region:'west',split:true,collapsible:false" title="权限树" style="width: 538px;">
                <div id="perm-tree"></div>
                <input id="permissions" type="hidden" name="permissions"/>
                <input id="perm-role-id" type="hidden" name="id"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>