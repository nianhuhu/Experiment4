window.onload = function(){


   // var value = $("input[name='alternative']:checked").val();
    var flag = true;
    var  bulb ='关';
    var ray =0;
    var timer=null; //定时器
	$("#btn").click(function(){
		if(flag){
			$("#change #dim").hide();
			$("#change #light").show();
			flag = false;
			bulb ='开';
		}else{
			$("#change #light").hide();
			$("#change #dim").show();
		    flag = true;
		    bulb='关'
		}
	})

//手动状态
    $("#box").click(function () {
        var value = $("input[name='alternative']:checked").val();
        if(value == 1){
            clearInterval(timer);
            $("#btn").show();
            $.ajax({
                url: "/showLight",
                async: true,
                type: "post",
                data: JSON.stringify({light: light}),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                        if (parseInt(data) <= 50) {
                            alert("现在是手动状态，光照强度为:" + parseInt(data) + "Lux。请开灯!");
                            ray=parseFloat(data);
                            if (flag==true){
                                bulb='关';
                            } else{
                                bulb='开';
                            }
                        }

                        else if (parseInt(data) >= 200) {
                            alert("现在是手动状态，光照强度为:" + parseInt(data) + "Lux。请关灯!")
                            ray=parseFloat(data);
                            if (flag==false){
                                bulb='开';
                            }
                            else if (flag==true) {

                                bulb='关';
                            }
                        }
                        else{
                            alert("现在是手动状态，光照强度为:" + parseInt(data) + "Lux。状态保持不变");
                            ray=parseFloat(data);
                            if (bulb=='开') {
                                bulb='开';
                            }
                            else if (bulb=='关') {
                                bulb='关';
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
            $("#btn").hide();
            timer=setInterval(function()   //开启循环：每秒出现一次提示框
            {
                $.ajax({
                    url: "/showMessage",
                    async: true,
                    type: "post",
                    data: JSON.stringify({light: light}),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    success: function (data) {
                        if (parseInt(data) <= 50) {
                            ray=parseFloat(data);
                            $("#change #light").show();
                            $("#change #dim").hide();
                            bulb='开';
                        }

                        else if (parseInt(data) >= 200) {
                            ray=parseFloat(data);
                            $("#change #light").hide();
                            $("#change #dim").show();
                            bulb='关';
                        }
                        else{
                            ray=parseFloat(data);
                            if (bulb=='开') {
                                $("#change #light").show();
                                $("#change #dim").hide();
                                bulb='开';
                            }
                            else if (bulb=='关') {
                                $("#change #light").hide();
                                $("#change #dim").show();
                                bulb='关';
                            }
                        }
                    }
                })
            },1000);
        }
    })

    setInterval(function(){
        $(function(){
            $("#sub").html(bulb); //灯泡状态
            $("#sub2").html(ray); //光照强度
        })
    },1000);

}


