<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Rabbit : Home</title>
        <script src="/assets/js/jquery-3.1.0.min.js"></script>
        <link rel="icon" type="image/icon" href="assets/images/tabicon.ico">

        <link rel="stylesheet" type="text/css" href="">
        <link href="/assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="/assets/css/bootstrap-theme.min.css" rel="stylesheet">
        <link href="/assets/css/font-awesome.min.css" rel="stylesheet">
        <link href="/assets/css/main.css" rel="stylesheet">
        <link rel="icon" href="/assets/images/logo.png">
        <link rel="stylesheet" href="/layui/css/layui.css" media="all"/>
        <script src="/layui/layui.js"></script>
    </head>

    <body>

        <div id="contact_scroll" class="pages">                             <!-- Contact starts here -->
            <div class="container main">
                <div class="row">
                    <div class="col-md-6 left" id="contact_left">
                        <img class="img-responsive img-rabbit" src="/assets/images/home-1.png">
                    </div>

                    <div class="col-md-6 right" id="about_right">
                        <a href="#" class="btn btn-rabbit back" onclick="history.back(-1)"> <i class="fa fa-angle-left" aria-hidden="true"></i> 返回 </a>
                        <div id="watermark1">
                            <h2 class="page-title" text-center>Ta的信息</h2>
                            <div class="marker"></div>
                        </div>
                        <p class='subtitle'>${otherUser.declaration}
                        </p>

                        <div class="form-group">
                            <%--<input type="name" class="form-control" id="" placeholder="Name">--%>
                            <label class="form-control" >姓名：${otherUser.nickname}</label>
                        </div>

                        <div class="form-group">
                            <%--<input type="email" class="form-control" id="" placeholder="Email">--%>
                            <label class="form-control" >性别：${otherUser.sex}</label>
                        </div>

                        <div class="form-group">
                            <label class="form-control">生日：${otherUser.birthday}</label>
                        </div>

                        <div class="form-group">
                            <textarea class="form-control" rows="5" readonly >个人介绍：${otherUser.personalIntroduction}</textarea>
                            <br>
                            <button id="modify_info" type="button" class="btn btn-rabbit submit" onclick="sendMsg()">发送私信</button>
                            <script>
                                function sendMsg() {
                                    if(${sessionScope.get("user") == null}){
                                        layui.use('layer', function () {
                                            var layer = layui.layer;
                                            layer.msg("请登录后再发送私信");
                                            return;
                                        });
                                    }
                                    var toId = ${otherUser.id}
                                    layui.use('layer', function () {
                                        var layer = layui.layer;
                                        layer.prompt({
                                                formType: 2,
                                                value: '',
                                                title: '回复:',
                                                area: ['400px', '150px'] //自定义文本域宽高
                                            }, function (value, index, elem) {
                                                if(value.length < 5){
                                                    layer.close(index);
                                                    layer.msg("私信内容长度不能小于5");
                                                    return;
                                                }
                                                $.ajax({
                                                    url :"/letter/msg",
                                                    type:"POST",
                                                    dataType:"json",
                                                    contentType:"application/x-www-form-urlencoded; charset=utf-8",
                                                    data:{"toId":toId,"msg":value},
                                                    success:function(result) {
                                                        layer.msg(result.msg);
                                                        layer.close(index);
                                                    }
                                                });
                                            }
                                        );
                                    });
                                }
                            </script>
                        </div>

                    </div>
                </div>
            </div>
            
        </div>                                                              <!-- Contact ends here -->

        <script src="/assets/js/bootstrap.min.js"></script>
    </body>
</html>