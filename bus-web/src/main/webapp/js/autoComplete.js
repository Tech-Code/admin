/**
 * Created by xuefei on 6/19/14.
 */
var highlightindex = -1; //定义高亮显示索引,-1代表不高亮任何行
var timeOutId = null; //定义延迟时间Id
var delayTime = 2000; //默认延迟0.5秒
var minPrefix = 0; //定义最小几个字符开始搜索
var mouseOverCss = { background: "white", cursor: " pointer" }; //mouseover时样式
var mouseOutCss = { background: "white" }; //mouseout时样式
var grayCss = { background: "#cef", cursor: " pointer" };
var upDownGrayCss = { background: "#cef" };
var upDownWhiteCss = { background: "white" };

var ajaxProcessUrl = "<%=root%>/transstation/suggestlist"; //发送ajax请求调用处理url

$(document).ready(function () {
    var wordInput = $("#stationName");
    var wordInputOffset = wordInput.offset();


    //隐藏自动补全框,并定义css属性
//    $("#divAutoList").hide()
//        .css("border", "1px solid #B5B8C8")
//        .css("width", "256px")
//        .css("width", "256px")
//        .css("background", "#FFF repeat-x");

    $("#divAutoList").hide().css({
        "width": "250px",
        "color": "#555",
        "padding": "5px",
        "border": "1px solid #B5B8C8",
        "font - size":"14px",
        "margin - top":"5px",
        "background": "#FFFrepeat - x"
    });

    //给文本框添加键盘按下并弹起的事件
    wordInput.keyup(function (event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        var autoNode = $("#divAutoList"); //可供选择的项

        //        if (keyCode >= 65 && keyCode <= 90 || keyCode == 8 || keyCode == 46) { //输入字母,退格或删除,显示最新的内容
        if (keyCode != 13 && keyCode != 38 && keyCode != 40) { //不是三个特殊键，可以搜索
            var wordText = $("#stationName").val();
            if (wordText.length < minPrefix) return;
            //取消上次提交
            //window.clearTimeout(timeOutId);
            timeOutId = setTimeout(function () {
                $.ajax({
                    type: "GET",
                    url: ajaxProcessUrl,
                    data: 'stationName=' + wordText,
                    success: function (data) {
                        var wordNodes = $(data.result);
                        autoNode.html("");

                        for(var i=0;i < data.result.length;i++) {
                            var newDivNode = $("<div>").attr("id", data.result[i].id);
                            newDivNode.html(data.result[i].stationName).appendTo(autoNode);
                            //添加光标进入事件, 高亮节点
                            newDivNode.mouseover(function () {
                                if (highlightindex != -1) {
                                    $("#divAutoList").children("div")
                                        .eq(highlightindex)
                                        .css(mouseOverCss);
                                }
                                highlightindex = $(this).attr("id");
                                $(this).css(grayCss);
                            });
                            //添加光标移出事件,取消高亮
                            newDivNode.mouseout(function () {
                                $(this).css(mouseOutCss);
                            });
                            //添加光标mousedown事件  点击事件newDivNode.click可能不起作用?
                            newDivNode.mousedown(function () {
                                var comText = $(this).text();
                                $("#divAutoList").hide();
                                highlightindex = -1;
                                $("#stationName").val(comText);
                                $("#cityStationId").val($(this).attr("id"));
                                $("#cityCode").val($(this).attr("cityCode"));
                                $("#cityName").val($(this).attr("cityName"));
                                $("#transType").val($(this).attr("transType"));
                                $("#city_stationName").val($(this).attr("stationName"));
                                $("#transdetail").val($(this).attr("transdetail"));
                                $("#coordinate").val($(this).attr("coordinate"));
                            });
                        }
                        if (wordNodes.length > 0) {
                            autoNode.show();
                        }
                        else {
                            autoNode.hide();
                            highlightindex = -1;
                        }
                    },
                    dataType: "json"
                });
            }, delayTime);
        }
        else if (keyCode == 38) {//输入向上,选中文字高亮
            var autoNodes = $("#divAutoList").children("div")
            if (highlightindex != -1) {
                autoNodes.eq(highlightindex).css(upDownWhiteCss);
                highlightindex--;
            }
            else {
                highlightindex = autoNodes.length - 1;
            }

            if (highlightindex == -1) {
                highlightindex = autoNodes.length - 1;
            }
            autoNodes.eq(highlightindex).css(upDownGrayCss);
        }
        else if (keyCode == 40) {//输入向下,选中文字高亮
            var autoNodes = $("#divAutoList").children("div")
            if (highlightindex != -1) {
                autoNodes.eq(highlightindex).css(upDownWhiteCss);
            }
            highlightindex++;
            if (highlightindex == autoNodes.length) {
                highlightindex = 0;
            }
            autoNodes.eq(highlightindex).css(upDownGrayCss);

        }
        else if (keyCode == 13) {//输入回车
            if (highlightindex != -1) {
                var comText = $("#divAutoList").hide().children("div").eq(highlightindex).text();
                highlightindex = -1;
                $("#stationName").val(comText);
                return false;
            }
            else {
                alert("文本框中的[" + $("#stationName").val() + "]被提交了！");
                $("#divAutoList").hide();
                $("#stationName").get(0).blur();
                return true;
            }
        }
    });

    //给查询框添加blur事件，隐藏提示层
    $("#stationName").blur(function () {
        $("#divAutoList").hide();
    });

});