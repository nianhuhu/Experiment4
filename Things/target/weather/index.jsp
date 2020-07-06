<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
用户名:<input type="text" name="rootname" id="rootname" value="yw"></br>
密码:<input type="password" name="password" id="password" value="123456"></br>

<input id="commitdata" value="连接" type="button" onclick="commit()"/></br>

<div id="box">
    手动<input type="radio" name="alternative" id="hand" value="1"/><br/><br/>
    自动<input type="radio" name="alternative" id="auto" value="2"/>
</div>
<div id="change">
    <img id="dim" src="img/dim.svg" alt=""/>
    <img id="light" src="img/light.svg"/>
</div>
<table>
    <tr>
        <td>
            <span class="name">当前开关状态:</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="now_switch"
                                                                           id="now_switch">关</span><br/>
            <div class="icon">
                <span>当前光照强度:</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="now_lux" id="now_lux">0</span>
            </div>
        </td>
    <tr>
        <td>
            <div class="dataBox">
                <input name="inputs" id="inputs" placeholder="输入查询的日期"/>
                <button id="search" onclick="receive()">查询</button>
                <br/>
            </div>

            <span class="name" style="margin-top: 25px;">当日开灯时间:</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="day_on"
                                                                                                     id="day_on">xxxx-xx-xx xx:xx</span><br/>
            <div class="icon">
                <span>当日关灯时间:</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="day_off" id="day_off">xxxx-xx-xx xx:xx</span>
            </div>
        </td>
    </tr>
    </tr>
</table>
<div>
    <button id="btn">切换</button>
</div>


<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="css/things.css"/>

<script type="text/javascript">


    function show(data) {
        var luxNumber = data;
        $.ajax({
            url: "/historyData",
            type: "post",
            data: JSON.stringify({luxNumber: luxNumber}),
            contentType: "application/json;charset=UTF-8",
            dateType: "json",
            success: function (data) {

            }
        });

    }

    function receive() {
        var date = $("#inputs").val();
        $.ajax({
            url: "/switchData",
            type: "post",
            data: JSON.stringify({date: date}),
            contentType: "application/json;charset=UTF-8",
            dateType: "json",
            success: function (data) {
                console.log(data);
                var date1 = new Date();
                date1.setTime(data[0].luxTime);
                var dateTimeType1 = date1.getFullYear() + "-" + getMonth(date1) + "-"
                    + getDay(date1) + "&nbsp;" + getHours(date1) + ":"
                    + getMinutes(date1) + ":" + getSeconds(date1);

                var date2 = new Date();
                date2.setTime(data[1].luxTime);
                var dateTimeType2 = date2.getFullYear() + "-" + getMonth(date2) + "-"
                    + getDay(date2) + "&nbsp;" + getHours(date2) + ":"
                    + getMinutes(date2) + ":" + getSeconds(date2);


                document.getElementById("day_on").innerHTML = dateTimeType2;//开时间
                document.getElementById("day_off").innerHTML = dateTimeType1;//关时间
            }
        });

    }

    //返回 01-12 的月份值
    function getMonth(date) {
        var month = "";
        month = date.getMonth() + 1; //getMonth()得到的月份是0-11
        if (month < 10) {
            month = "0" + month;
        }
        return month;
    }

    //返回01-30的日期
    function getDay(date) {
        var day = "";
        day = date.getDate();
        if (day < 10) {
            day = "0" + day;
        }
        return day;
    }

    //返回小时
    function getHours(date) {
        var hours = "";
        hours = date.getHours();
        if (hours < 10) {
            hours = "0" + hours;
        }
        return hours;
    }

    //返回分
    function getMinutes(date) {
        var minute = "";
        minute = date.getMinutes();
        if (minute < 10) {
            minute = "0" + minute;
        }
        return minute;
    }

    //返回秒
    function getSeconds(date) {
        var second = "";
        second = date.getSeconds();
        if (second < 10) {
            second = "0" + second;
        }
        return second;
    }


</script>
<script type="text/javascript">
    window.onload = function () {
        var flag = true;
        var now_switch = '关';
        var luxNumber = "0";
        var now_lux = 0;
        var now_number = 0;
        var automaticFlag = true;
        var manualFlag = true;

        var timer = null; //定时器
        $("#btn").click(function () {
            if (flag) {
                $("#change #dim").hide();
                $("#change #light").show();
                flag = false;
                now_switch = '开';
                show(now_number);
            } else {
                $("#change #light").hide();
                $("#change #dim").show();
                flag = true;
                now_switch = '关';
                show(now_number);
            }
        })

        //手动状态
        $("#box").click(function () {

            var value = $("input[name='alternative']:checked").val();
            if (value == 1) {
                alert("手动状态!");
                clearInterval(timer);
                $("#btn").show();
                $.ajax({
                    url: "/showMessage",
                    async: true,
                    type: "post",
                    data: JSON.stringify({luxNumber: luxNumber}),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    success: function (data) {

                        if (parseInt(data) <= 50) {
                            alert("光照强度为:" + parseInt(data) + "Lux。请开灯!");
                            now_lux = parseFloat(data);
                            now_number = now_lux;
                            if (flag) {
                                now_switch = '关';

                                if (manualFlag) {
                                    show(data);
                                    manualFlag = false;

                                }
                            } else {
                                now_switch = '开';

                                if (manualFlag) {
                                    show(data);
                                    manualFlag = true;

                                }
                            }
                        } else if (parseInt(data) >= 200) {
                            alert("光照强度为:" + parseInt(data) + "Lux,请关灯!")
                            now_lux = parseFloat(data);
                            now_number = now_lux;
                            if (flag) {
                                now_switch = '关';
                                if (manualFlag) {
                                    show(data);
                                    manualFlag = true;
                                }
                            } else if (flag) {
                                now_switch = '开';
                                if (manualFlag) {
                                    show(data);
                                    manualFlag = false;
                                }
                            }
                        } else {
                            alert("光照强度为:" + parseInt(data) + "Lux。状态保持不变");
                            now_lux = parseFloat(data);
                            if (now_switch == '开') {
                                now_switch = '开';

                            } else if (now_switch == '关') {
                                now_switch = '关';
                            }
                        }

                    }


                })
            }
        })
        //自动状态
        $("#box").click(function () {

            var value = $("input[name='alternative']:checked").val();
            if (value == 2) {
                alert("自动状态!");
                $("#btn").hide();
                timer = setInterval(function ()   //开启循环：每秒出现一次提示框
                {
                    $.ajax({
                        url: "/showMessage",
                        async: true,
                        type: "post",
                        data: JSON.stringify({luxNumber: luxNumber}),
                        contentType: "application/json;charset=UTF-8",
                        dataType: "json",
                        success: function (data) {
                            if (parseInt(data) <= 50) {
                                now_lux = parseFloat(data);

                                $("#change #light").show();
                                $("#change #dim").hide();
                                now_switch = '开';

                                if (automaticFlag) {
                                    show(data);
                                    automaticFlag = false;

                                }

                            } else if (parseInt(data) >= 200) {
                                now_lux = parseFloat(data);

                                $("#change #light").hide();
                                $("#change #dim").show();
                                now_switch = '关';

                                if (automaticFlag) {
                                    show(data);
                                    automaticFlag = true;
                                }

                            } else {
                                now_lux = parseFloat(data);
                                if (now_switch == '开') {
                                    $("#change #light").show();
                                    $("#change #dim").hide();
                                    now_switch = '开';
                                } else if (now_switch == '关') {
                                    $("#change #light").hide();
                                    $("#change #dim").show();
                                    now_switch = '关';
                                }
                            }
                        }
                    })
                }, 1000);
            }
        })

        setInterval(function () {
            $(function () {
                document.getElementById("now_switch").innerHTML = now_switch;//灯泡状态
                document.getElementById("now_lux").innerHTML = now_lux + "lux";//光照强度
            })
        }, 1000);

    }
</script>

<script type="text/javascript">

    function commit() {
        var username = $("#rootname").val();
        var password = $("#password").val();

        $.ajax({
            url: "/sendMessage",
            type: "post",
            data: JSON.stringify({username: username, password: password}),
            contentType: "application/json;charset=UTF-8",
            dateType: "json",
            success: function (data) {

                if (data > 0) {
                    alert("连接成功");
                } else {
                    alert("连接失败,请联系管理员!");
                }
            }
        });

    }

</script>


</body>
</html>
