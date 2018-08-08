<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
	<head>
		<script src="/js/jquery.min.js"></script>
		<script src="/laydate/laydate.js"></script>
		<script src="/layui/layui.js"></script>

		<script>


            layui.use('upload', function(){
                var upload = layui.upload;

                //执行实例
                var uploadInst = upload.render({
                    elem: '#upload_btn' //绑定元素
					,size: 2000
					,method:'post'
                    ,url: '/pic/uploadPhoto' //上传接口
                    ,done: function(res){
                        //上传完毕回调
						layer.msg("上传成功"+res.src);
						if(res.code==0){
						    layer.msg(res.msg);
                            $("#head_img").attr('src',res.src);
                            $("#head_pic").attr('value',$("#head_img")[0].src);
                        }else if(res.code==1){
						    layer.msg(res.msg);
						}else if(res.code==2){
                            layer.msg(res.msg)
						}else{
                            layer.msg("上传失败,服务器错误");
						}
                    }
                    ,error: function(index){
                        //请求异常回调
						layer.msg("上传失败,服务器错误");
                    }
                });
            });


		</script>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>用户注册</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">


	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
	<link rel="shortcut icon" href="favicon.ico">

	<%--<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>--%>
	
	<link rel="stylesheet" href="/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/animate.css">
	<link rel="stylesheet" href="/css/style.css">

	<!-- Modernizr JS -->
	<script src="/js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="/js/respond.min.js"></script>
	<![endif]-->

	</head>
	<body class="style-2">
	<script>
        //执行一个laydate实例
//        laydate.render({
//            elem: '#birthday' //指定元素
//        });

		window.onload=function () {
            $(document).ready(function () {
                if(${registerResult != null}){
                    if(${registerResult.code==1}){
                        layer.msg("${registerResult.msg}");
                    }else{
                        layer.msg('未知结果:'+ "${registerResult.msg}");
                    }
                }
            });

            laydate.render({
                elem: '#birthday' //指定元素
            });
        }
	</script>

		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<ul class="menu">
						<li><h2><a href="/post/main">Peanut论坛</a> </h2></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					

					<!-- Start Sign In Form -->
					<form action="/user/register" id="registForm" class="fh5co-form animate-box" data-animate-effect="fadeInLeft" method="post">
						<h2>Sign Up</h2>
						<div class="form-group">
							<div class="alert alert-success" role="alert">
								<img src="/images/default_head_pic.jpg" width="48" height="48" id="head_img"/>
								<<input type="hidden" id="upload_btn">
								<input type="hidden"  id="head_pic" name="head_pic" value="">
							</div>
						</div>
						<div class="form-group">
							<label for="username" class="sr-only">Name</label>
							<input type="text" class="form-control" id="username" name="username" placeholder="Name" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="student_id" class="sr-only">Student_ID</label>
							<input type="email" class="form-control" id="student_id" name="student_id" placeholder="student_id" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="password" class="sr-only">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="repassword" class="sr-only">Re-type Password</label>
							<input type="password" class="form-control" id="repassword" name="repassword" placeholder="Re-type Password" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="sex">sex &nbsp</label>
							<select name="sex" id="sex" name="sex">
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="birthday" name="birthday" placeholder="BIRTHDAY">
						</div>
						<%--<div class="form-group">--%>
							<%--<label for="remember"><input type="checkbox" id="remember"> Remember Me</label>--%>
						<%--</div>--%>
						<div class="form-group">
							<p>Already registered? <a href="/user/center">Sign In</a></p>
						</div>
						<div class="form-group">
							<input type="button" value="Sign Up" id="sub" name="sub" class="btn btn-primary">
						</div>
					</form>
					<!-- END Sign In Form -->


				</div>
			</div>
		</div>
	
	<script>
        $('#sub').click(function () {
            var picPath = $("#head_pic")[0].value;
            var userName = $("#username")[0].value;
            var studentId = $("#student_id")[0].value;
            var password = $("#password")[0].value;
            var rePassword = $("#repassword")[0].value;
            var sex = $("#sex")[0].value;
            var birthday = $("#birthday")[0].value;
            if(picPath.trim() == ""){
                $("#head_pic").attr('value',$("#head_img")[0].src);
			}

			if(userName.trim() == ""){
				layer.msg("注册失败，昵称不能为空");
				return;
			}

			if(studentId.trim() == ""){
                layer.msg("注册失败，学号不能为空");
                return;
			}

			if(password.trim() == ""){
                layer.msg("注册失败，密码不能为空");
                return;
			}

			if(rePassword.trim() == ""){
                layer.msg("注册失败，密码不能为空");
                return;
			}
			if(password.trim() != rePassword){
                layer.msg("注册失败，两次密码输入不一致");
                return;
			}
			if(birthday.trim() == ""){
                layer.msg("注册失败，生日不能为空");
                return;
			}

			if(studentId.trim().length != 11 || isNaN(studentId.trim())){
                layer.msg("注册失败，学号格式错误");
                return;
			}

			if(password.trim().length < 6 || password.trim().length > 14){
                layer.msg("注册失败，密码长度6-14位");
                return;
			}

			$("#registForm").submit();
        });
	</script>
	<!-- Bootstrap -->
	<script src="/js/bootstrap.min.js"></script>
	<!-- Placeholder -->
	<script src="/js/jquery.placeholder.min.js"></script>
	<!-- Waypoints -->
	<script src="/js/jquery.waypoints.min.js"></script>
	<!-- Main JS -->
	<script src="/js/main.js"></script>
	</body>
</html>

