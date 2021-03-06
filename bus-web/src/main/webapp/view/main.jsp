<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>移动位置平台公交管理系统</title>
    <script type="text/javascript">
        $(function () {
            $('#centerTab').tabs({
                onBeforeClose: function (title) {
                    return confirm('您确定要关闭当前页面吗?');
                },
                tools: [
                    {
                        id: 'updateUserBtn',
                        text: '修改个人信息',
                        iconCls: 'icon-edit',
                        handler: function () {
                            var ps = "?id=" + '${user.id}';
                            var url = '<%=root%>/user/update' + ps + "&updateType=2";
                            addTab('tabId_user_update', '更新用户信息', url);
                        }
                    },
                    {
                        iconCls: 'icon-back',
                        handler: function () {
                            $.messager.confirm('注销提示', '你确定注销吗?', function (r) {
                                if (r) {
                                    window.location = '<%=root%>/view/login.jsp';
                                }
                            });
                        }
                    }

                ]
            });
        })

        /**
         * 创建新选项卡
         * @param tabId    选项卡id
         * @param title    选项卡标题
         * @param url      选项卡远程调用路径
         */
        function addTab(tabId, title, url) {
            //如果当前id的tab不存在则创建一个tab
            if ($("#" + tabId).html() == null) {
                var name = 'iframe_' + tabId;
                $('#centerTab')
                        .tabs(
                        'add',
                        {
                            title: title,
                            closable: true,
                            cache: false,
                            id: tabId,
                            //注：使用iframe即可防止同一个页面出现js和css冲突的问题
                            content: '<iframe name="'
                                    + name
                                    + '"id="'
                                    + tabId
                                    + '"src="'
                                    + url
                                    + '" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>'
                        });
            } else {
                $('#centerTab').tabs('select', title);
            }
        }

        function closeTab() {
            var tab = $('#centerTab').tabs('getSelected');
            var title = tab.panel('options').tab.text();
            $('#centerTab').tabs('close', title);
        }
    </script>
    <style>
        a {
            text-decoration: none;
        }
    </style>
</head>
<!-- 设置了class就可在进入页面加载layout -->
<body class="easyui-layout">
<!-- 正上方panel -->
<div region="north" style="height: 80px; padding: 5px;background:url(<%=root%>/img/top.jpg);overflow:hidden">
    <h1 style="margin-left:10px ">移动位置平台公交管理系统</h1>
</div>
<!-- 正左边panel -->
<div region="west" title="菜单栏" split="true"
     style="width: 280px; padding1: 1px; overflow: hidden;">
    <div class="easyui-accordion" fit="true" border="false">
        <!-- selected -->

        <div title="系统配置表维护" style="overflow: hidden;">
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_cslist','城市交通站点管理','<%=root%>/view/citystation/cslist.jsp');">城市交通站点管理</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_tslist','城际交通站点管理','<%=root%>/view/transstation/tslist.jsp');">城际交通站点管理</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_poilist','地标点管理','<%=root%>/view/poi/poilist.jsp');">地标点管理</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_bclist','出租车费用管理','<%=root%>/view/taxi/taxilist.jsp');">出租车费用管理</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_splist','时速配置管理','<%=root%>/view/speed/speedlist.jsp');">时速配置管理</a>
                </li>
            </ul>
        </div>

        <c:if test="${user.role == '0'}">
            <div title="权限管理" style="overflow: hidden;">
                <ul>
                    <li>
                        <a href="javascript:addTab('tabId_keylist','权限配置管理','<%=root%>/view/userkey/userkeylist.jsp');">权限配置管理</a>
                    </li>
                </ul>
                <ul>
                    <li>
                        <a href="javascript:addTab('tabId_userlist','用户管理','<%=root%>/view/user/userlist.jsp');">用户管理</a>
                    </li>
                </ul>
            </div>
        </c:if>

        <div title="统计功能" style="overflow: hidden;">
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_namelist','业务统计','<%=root%>/view/analysis/namelist.jsp');">业务统计</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_typelist','服务统计','<%=root%>/view/analysis/typelist.jsp');">服务统计</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_city_list','城市统计','<%=root%>/view/analysis/citylist.jsp');">城市统计</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_group_tab','时段统计','<%=root%>/view/analysis/grouplist.jsp');">时段统计</a>
                </li>
            </ul>
        </div>
        <div title="日志管理" style="overflow: hidden;">
            <ul>
                <li>
                    <a href="javascript:addTab('tabId_time_log','访问日志','<%=root%>/view/analysis/timelog.jsp');">访问日志</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<!-- 正中间panel -->
<div region="center" title="功能区">

    <div class="easyui-tabs" id="centerTab" fit="true" border="false">
        <div title="欢迎页" style="padding: 20px; overflow: hidden;">
            <img src='<%=root%>/img/jk.jpg' style="width: 100%; height: 100%">
        </div>
    </div>

</div>
<!-- 正下方panel -->
<div region="south" style="height: 10px;" align="center"></div>
</body>
</html>
