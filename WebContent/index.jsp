<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Try</title>
<!-- 1.引入 echarts.js -->
<script type="text/javascript" src="js/echarts.min.js"></script>
<!-- 引入jquery.js -->
<script type="text/javascript" src="js/jquery_3.4.1.js"></script>
</head>
<body style="margin: 5% 26% 0% 29%;">
	<!-- 2.为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="width: 600px; height: 400px;"></div>

	
	<script type="text/javascript">
		var myChart = echarts.init(document.getElementById('main'));

		//3.初始化，默认显示标题，图例和xy空坐标轴
		myChart.setOption({

			title : {
				text : '疫情信息'
			},
			tooltip : {},

			legend : {

				data : [ '人数' ]
			},
			xAxis : {
				data : []
			},
			yAxis : {},
			series : [ {
				name : '人数',
				type : 'bar',
				data : []
			} ]
		});
		</script>
	<script type="text/javascript">

	 function realtime()
     {
			//5.定义数据存放数组(动态变)
			var names = []; //建立一个类别数组（实际用来盛放X轴坐标值）
			var nums = []; //建立一个销量数组（实际用来盛放Y坐标值）

			//6.ajax发起数据请求
			$.ajax({
				type : "post",
				async : true, //异步请求（同步请求将会锁住浏览器，其他操作须等请求完成才可执行）
				url : "Getnew", //请求发送到TestServlet
				data: [],
				dataType : "json", //返回数据形式为json

				//7.请求成功后接收数据name+num两组数据
				success : function(result) {
					//result为服务器返回的json对象
					if (result) {
						//8.取出数据存入数组
						for (var i = 0; i < result.length; i++) {
							names.push(result[i].name); //迭代取出类别数据并填入类别数组
						}
						for (var i = 0; i < result.length; i++) {
							nums.push(result[i].num); //迭代取出销量并填入销量数组
						}

						myChart.hideLoading(); //隐藏加载动画

						//9.覆盖操作-根据数据加载数据图表
						myChart.setOption({
							xAxis : {
								data : names
							},
							series : [ {
								// 根据名字对应到相应的数据
								name : '人数',
								data : nums
							} ]
						});

					}

				},
				error : function(errorMsg) {
					//请求失败时执行该函数
					alert("图表请求数据失败!");
					myChart.hideLoading();
				}
			})
		 
     }
       function sub()
       {
		//5.定义数据存放数组(动态变)
		var names = []; //建立一个类别数组（实际用来盛放X轴坐标值）
		var nums = []; //建立一个销量数组（实际用来盛放Y坐标值）

		//6.ajax发起数据请求
		$.ajax({
			type : "post",
			async : true, //异步请求（同步请求将会锁住浏览器，其他操作须等请求完成才可执行）
			url : "GetDate", //请求发送到TestServlet
			data: $('#GetDate').serialize(),
			dataType : "json", //返回数据形式为json

			//7.请求成功后接收数据name+num两组数据
			success : function(result) {
				//result为服务器返回的json对象
				if (result) {
					//8.取出数据存入数组
					for (var i = 0; i < result.length; i++) {
						names.push(result[i].name); //迭代取出类别数据并填入类别数组
					}
					for (var i = 0; i < result.length; i++) {
						nums.push(result[i].num); //迭代取出销量并填入销量数组
					}

					myChart.hideLoading(); //隐藏加载动画

					//9.覆盖操作-根据数据加载数据图表
					myChart.setOption({
						xAxis : {
							data : names
						},
						series : [ {
							// 根据名字对应到相应的数据
							name : '人数',
							data : nums
						} ]
					});

				}

			},
			error : function(errorMsg) {
				//请求失败时执行该函数
				alert("图表请求数据失败!");
				myChart.hideLoading();
			}
		})
       }
	</script>
	<form action="GetDate" id="GetDate" name="GetDate" method="post">
请输入要搜寻的时间:
<br>
    起始时间:<input type='text' id="time1" name="time1" value=""/> 终止时间: <input type='text' id="time2" name="time2" value=""/> 
<br>
    <input type="button" value="确认" onclick="sub()">
    <br>
    <input type="button" value="查看最新情况" onclick="realtime()">
</form>
</body>
</html>
