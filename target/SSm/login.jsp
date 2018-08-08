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

		<script src="/layer/layer.js"></script>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>用户登录</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<script type="text/javascript">
			window.onload = function(){
			    if(${loginFail != null}){
			        layer.msg("${loginFail}");
                }
                if(${registerResult != null}){
			        if(${registerResult.code == 0 || registerResult.code == 1}){
			            layer.msg("${registerResult.msg}");
                    }
                }

			    var ws = new WebSocket("ws://192.168.2.117:12345");
				ws.onopen=function (event) {
				    ws.close();
                    window.location.href="/user/fast_login";
				}
			}
		</script>

  


	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
	<link rel="shortcut icon" href="favicon.ico">

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
					<form action="/user/login" class="fh5co-form animate-box" data-animate-effect="fadeInLeft" method="post">
						<h2>登录</h2>
						<div class="form-group">
							<label for="username" class="sr-only">Username</label>
							<input type="text" class="form-control" value="${username}" id="username" name="username" placeholder="Username" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="password" class="sr-only">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" autocomplete="off">
						</div>
						<div class="form-group">

						</div>
						<div class="form-group">
							<p>没有账号? <a href="/user/regist">注册</a> | <a href="/user/fast_login">快速登录</a></p>
						</div>
						<div class="form-group">
							<input type="submit" value="Sign In" class="btn btn-primary">
						</div>
					</form>
					<!-- END Sign In Form -->

				</div>
			</div>
		</div>

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

