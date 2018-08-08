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
        <link rel="icon" type="/image/icon" href="/assets/images/tabicon.ico">

        <script src="/assets/js/jquery-3.1.0.min.js"></script>
        <link rel="stylesheet" type="text/css" href="">
        <link href="/assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="/assets/css/bootstrap-theme.min.css" rel="stylesheet">
        <link href="/assets/css/font-awesome.min.css" rel="stylesheet">
        <link href="/assets/css/main.css" rel="stylesheet">
        <link rel="stylesheet" href="/layui/css/layui.css" media="all"/>
        <link rel="icon" href="/assets/images/logo.png">
        <script src="/layui/layui.js"></script>
        <script>
            layui.use('laypage',function () {
                var laypage = layui.laypage;
                laypage.render({
                   elem : 'postPage',
                   count: ${myPostPageCount},
                   limit: 6,
                    jump:function (obj, first) {
                       // var currentP = obj.curr;

                        $.ajax({
                            url:"/post/myposts",
                            type:"POST",
                            dataType:"json",
                            contentType:"application/x-www-form-urlencoded; charset=utf-8",
                            data:{"currentPage":obj.curr},
                            success:function (data) {

                                //alert(data[0].title);
                                //alert( $("myPost").eq(1));
                                var i =0;
                                for(i = 0 ; i < data.length ; i++){
                                    $(".myPost").eq(i).css("display","inline");
                                    $(".myPost").eq(i).text(data[i].title);
                                    $(".myPost").eq(i).attr("href","/post/post/"+data[i].id);
                                    //document.getElementById("myPost"+(i+1)).innerText = data[i].title;
                                }
                                for(;i < 6 ;i++){
                                    $(".myPost").eq(i).css("display","none");
                                }
                            }
                        });
                    }
                });
                var laypage1 = layui.laypage;
                laypage1.render({
                    elem : 'privatePage',
                    count: ${myLetterCount},
                    limit: 6,
                    jump:function (obj, first) {
                        $.ajax({
                            url :"/letter/myLetter",
                            type:"POST",
                            dataType:"json",
                            contentType:"application/x-www-form-urlencoded; charset=utf-8",
                            data:{"currentPage":obj.curr},
                            success:function (data) {
                                var i = 0;
                                for(i = 0 ;i < data.length ; i++){
                                    $(".fromName").eq(i).text(data[i].fromName);
                                    $(".content").eq(i).text(data[i].content);
                                    $(".deleteLetter").eq(i).attr("href","/letter/delete/"+data[i].id);
                                    $(".fromName").eq(i).attr("href","/user/other/"+data[i].fromId);
                                    $(".reply").eq(i).attr("id",data[i].fromId);
                                }
                                for(;i<6;i++){
                                    $(".fromName").eq(i).text("");
                                    $(".content").eq(i).text("");
                                    $(".deleteLetter").eq(i).attr("href","#");
                                    $(".fromName").eq(i).attr("href","#");
                                    $(".deleteLetter").eq(i).text("");
                                    $(".reply").eq(i).text("");
                                }
                            }
                        });
                    }
                });
            });

            layui.use('layer', function(){
                var layer = layui.layer;
                var flag = ${empty deleteResult};
                if(!flag){
                    var result = "${deleteResult}";
                    if(result == "success") {
                        layer.msg("删除成功");
                    }else{
                        layer.msg("删除失败");
                    }
                }
            });

            function replyOther(th) {
                //alert(th.id);
                layui.use('layer',function () {
                    var layer = layui.layer;
                    layer.prompt({
                        formType: 2,
                        value: '',
                        title: '回复:',
                        area: ['400px', '150px'] //自定义文本域宽高
                    }, function(value, index, elem){
                        //alert(value); //得到value
                        if(value.length < 5){
                            layer.close(index);
                            layer.msg("回复内容长度不能小于5");
                            return;
                        }

                        $.ajax({
                            url :"/letter/reply",
                            type:"POST",
                            dataType:"json",
                            contentType:"application/x-www-form-urlencoded; charset=utf-8",
                            data:{"toId":th.id,"msg":value},
                            success:function(result) {
                                if(result.result == "success") {
                                    layer.msg("回复成功");
                                }else{
                                    layer.msg("回复失败");
                                }
                            }
                        });

                        layer.close(index);
                    });
                })
            }

        </script>

    <body>
        <div id="index">                                           <!-- Index starts here -->
            <div class="container main">
                <div class="row home">
                    <div id = "index_left" class="col-md-6 left">
                        <img class="img-responsive img-rabbit" src="/assets/images/home-1.png">
                    </div>
                    <div id = "index_right" class="col-md-6 text-center right">
                        <div class="logo">
                            <img src="${sessionScope.get("user").headPic}" width="51" height="51">
                            <h4>${sessionScope.get("user").nickname}</h4>
                        </div>
                        <p class="home-description">
                            ${sessionScope.get("user").declaration} </p>
                        <div class="btn-group-vertical custom_btn animated slideinright">
                            <div id="about" class="btn btn-rabbit">个人信息</div>
                            <div id="my_post" class="btn btn-rabbit">我的发表</div>
                            <div id="private_letter" class="btn btn-rabbit">我的私信</div>
                            <div class="btn btn-rabbit"><a href="/post/main">返回主页</a> </div>
                        </div>
                    </div>
                </div>

                
            </div>
        </div>                                                      <!-- index ends here -->

        <div id="about_scroll" class="pages ">                      <!-- about strats here  -->
            <div class="container main">
                <div class="row">
                    <div class="col-md-6 left" id="about_left">
                        <img class="img-responsive img-rabbit" src="/assets/images/about-1.png">
                    </div>

                    <div class="col-md-6 right" id="about_right">
                        <a href="#index" class="btn btn-rabbit back"> <i class="fa fa-angle-left" aria-hidden="true"></i> 返回 </a>
                        <div id="watermark1">
                            <h2 class="page-title" text-center>个人信息</h2>
                            <div class="marker"></div>
                        </div>
                        <p class='subtitle'>${sessionScope.get("user").declaration}
                        </p>

                            <div class="form-group">
                                <%--<input type="name" class="form-control" id="" placeholder="Name">--%>
                                <label class="form-control" >姓名：${sessionScope.get("user").nickname}</label>
                            </div>

                            <div class="form-group">
                                <%--<input type="email" class="form-control" id="" placeholder="Email">--%>
                                    <label class="form-control" >性别：${sessionScope.get("user").sex}</label>
                            </div>

                            <div class="form-group">
                                <label class="form-control">生日：${sessionScope.get("user").birthday}</label>
                            </div>

                            <div class="form-group">
                                <textarea class="form-control" rows="5" readonly >个人介绍：${sessionScope.get("user").personalIntroduction}</textarea>
                            </div>

                            <button id="modify_info" type="button" class="btn btn-rabbit submit">修改个人信息</button>

                    </div>
                </div>
            </div>            
        </div>                                                                <!-- About ends here -->

        <div id="modify_scroll" class="pages ">                      <!-- modify strats here  -->
            <div class="container main">
                <div class="row">
                    <div class="col-md-6 left" id="modify_left">
                        <img class="img-responsive img-rabbit" src="/assets/images/about-1.png">
                    </div>

                    <div class="col-md-6 right" id="modify_right">
                        <a href="#index" class="btn btn-rabbit back"> <i class="fa fa-angle-left" aria-hidden="true"></i> 返回 </a>
                        <div id="modifymark1">
                            <h2 class="page-title" text-center>修改个人信息</h2>
                            <div class="marker"></div>
                        </div>
                        <form id="modifyForm" action="/user/modify" method="post">
                        <input class='subtitle' id="declaration" name="declaration" value="${sessionScope.get("user").declaration}">
                        </input>

                        <div class="form-group">
                            <%--<input type="name" class="form-control" id="" placeholder="Name">--%>
                            <label class="form-control" >昵称：${sessionScope.get("user").nickname}</label>
                        </div>

                        <div class="form-group">
                            <%--<input type="email" class="form-control" id="" placeholder="Email">--%>
                            <label class="form-control" >性别：${sessionScope.get("user").sex}</label>
                        </div>

                        <div class="form-group">
                            <label class="form-control">生日：${sessionScope.get("user").birthday}</label>
                        </div>

                        <div class="form-group">
                            <textarea class="form-control" id="personalIn" name="personalIn" rows="5" placeholder="个人介绍">${sessionScope.get("user").personalIntroduction}</textarea>
                        </div>

                        <button type="button" class="btn btn-rabbit submit" onclick="modify()">确认修改</button>
                        </form>
                        <script>
                            if(${modifyResult != null}){
                                layui.use('layer',function () {
                                    var layer = layui.layer;
                                    layer.msg("${modifyResult.msg}");
                                })
                            }

                            function modify() {

                                var declaration = $("#declaration").val();
                                if(declaration.length > 30){
                                    layui.use('layer', function(){
                                        var layer = layui.layer;

                                        layer.msg('修改失败，个性签名太长');
                                    });
                                    return;
                                }
                                $("#modifyForm").submit();

                            }
                        </script>
                    </div>
                </div>
            </div>
        </div>

        <div id="post_scroll" class="pages">                                  <!-- Work starts here -->
            <div class="container main">
                <div class="row">
                    <div class="col-md-6" id="work_left">
                        <div id="owl-demo" class="owl-carousel owl-theme">
                            <div class="item"><img class="img-responsive img-rabbit" src="/assets/images/about-1.png"></div>
                            <div class="item"><img class="img-responsive img-rabbit" src="/assets/images/about-1.png"></div>
                            <div class="item"><img class="img-responsive img-rabbit" src="/assets/images/about-1.png"></div>
                        </div>
                    </div>

                    <div class="col-md-6" id="work_right">
                        <a href="#index" class="btn btn-rabbit back"> <i class="fa fa-angle-left" aria-hidden="true"></i> 返回 </a>
                        <div id="watermark2">
                            <h2 class="page-title" text-center>我发表的帖子</h2>
                        </div>
                        <p class="info"><a href="#" class="myPost"></a> </p>
                        <p>——————————————————————————————————————————————</p>
                        <p class="info"><a href="#" class="myPost"></a> </p>
                        <p>——————————————————————————————————————————————</p>
                        <p class="info"><a href="#" class="myPost"></a> </p>
                        <p>——————————————————————————————————————————————</p>
                        <p class="info"><a href="#" class="myPost"></a> </p>
                        <p>——————————————————————————————————————————————</p>
                        <p class="info"><a href="#" class="myPost"></a> </p>
                        <p>——————————————————————————————————————————————</p>
                        <p class="info"><a href="#" class="myPost"></a> </p>
                        <p>——————————————————————————————————————————————</p>
                        <div id="postPage"></div>
                    </div>
                </div>
            </div>    
        </div>                                                                 <!-- Work ends here  -->


        <div id="private_letter_scroll" class="pages">                             <!-- Contact starts here -->
            <div class="container main">
                <div class="row">
                    <div class="col-md-6 left" id="contact_left">
                        <img class="img-responsive img-rabbit" src="/assets/images/about-1.png">
                    </div>

                    <div class="col-md-6 right" id="contact_right">
                        <a href="#index" class="btn btn-rabbit back"> <i class="fa fa-angle-left" aria-hidden="true"></i> 返回 </a>
                        <div id="watermark3">
                            <h2 class="page-title" text-center>我的私信</h2>
                            <div class="marker">c</div>
                        </div>
                        <p class='subtitle'>${sessionScope.get("user").declaration}
                        </p>
                        <!-- form -->
                        <table class="layui-table" lay-skin="line">
                            <colgroup>
                                <col width="150">
                                <col width="400">
                                <col width="100">
                                <col>
                            </colgroup>
                            <thead>
                            <tr class="form-group">
                                <th>发送人</th>
                                <th>内容</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td ><a href="#" class = "fromName"></a> </td>
                                <td class="content"></td>
                                <td><a class="deleteLetter">删除</a>&nbsp;<a class="reply" onclick="replyOther(this)">回复</a></td>
                            </tr>
                            <tr>
                                <td ><a href="#" class = "fromName"></a> </td>
                                <td class="content"></td>
                                <td><a class="deleteLetter">删除</a>&nbsp;<a class="reply" onclick="replyOther(this)">回复</a></td>
                            </tr>
                            <tr>
                                <td ><a href="#" class = "fromName"></a> </td>
                                <td class="content"></td>
                                <td> <a class="deleteLetter">删除</a>&nbsp;<a class="reply" onclick="replyOther(this)">回复</a></td>
                            </tr>
                            <tr>
                                <td ><a href="#" class = "fromName"></a> </td>
                                <td class="content"></td>
                                <td> <a class="deleteLetter">删除</a>&nbsp;<a class="reply" onclick="replyOther(this)">回复</a></td>
                            </tr><tr>
                                <td ><a href="#" class = "fromName"></a> </td>
                                <td class="content"></td>
                                <td> <a class="deleteLetter">删除</a>&nbsp;<a class="reply" onclick="replyOther(this)">回复</a></td>
                            </tr><tr>
                                <td ><a href="#" class = "fromName"></a> </td>
                                <td class="content"></td>
                                <td> <a class="deleteLetter">删除</a>&nbsp;<a class="reply" onclick="replyOther(this)">回复</a></td>
                            </tr>
                            </tbody>
                        </table>

                        <div id="privatePage"></div>
                    </div>
                </div>
            </div>
       
            <footer class="text-center">

            </footer>
            
        </div>                                                              <!-- Contact ends here -->

        <script src="/assets/js/bootstrap.min.js"></script>
        <script src="/assets/js/script.js"></script>
    </body>
</html>