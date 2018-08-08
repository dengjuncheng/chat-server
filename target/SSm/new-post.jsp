<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>

<!doctype html>
        <!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en-US"> <![endif]-->
        <!--[if IE 7]>    <html class="lt-ie9 lt-ie8" lang="en-US"> <![endif]-->
        <!--[if IE 8]>    <html class="lt-ie9" lang="en-US"> <![endif]-->
        <!--[if gt IE 8]><!--> <html lang="en-US"> <!--<![endif]-->
        <head>
                <!-- META TAGS -->
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0">

                <title>Knowledge Base Theme</title>

                <link rel="shortcut icon" href="/images/favicon.png" />


                

                <!-- Style Sheet-->
                <!--<link rel="stylesheet" href="style.css"/>-->
                <link rel='stylesheet' id='bootstrap-css-css'  href='/css/bootstrap5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='responsive-css-css'  href='/css/responsive5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='pretty-photo-css-css'  href='/js/prettyphoto/prettyPhotoaeb9.css?ver=3.1.4' type='text/css' media='all' />
                <link rel='stylesheet' id='main-css-css'  href='/css/main5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='custom-css-css'  href='/css/custom5152.html?ver=1.0' type='text/css' media='all' />

                <script type='text/javascript' src='/js/jquery-1.8.3.min.js'></script>
                <script type="text/javascript" src="/kindeditor/kindeditor-all.js"></script>
                <script type="text/javascript" src="/kindeditor/lang/zh-CN.js"></script>
                <%--<script type="text/javascript" src="/js/jwplayer.js"></script>--%>
                <script type="text/javascript" src="/layer/layer.js"></script>
                <%--<script type="text/javascript" src="/kindeditor/plugins/ckplayer/ckplayer.min.js"/>--%>
                <script type="text/javascript">
                    var editor = null;
                    KindEditor.ready(function(k){
                        editor = k.create('#kindeditor',{
                            width: '1000',
                            height: '470',
                            resizeType: 0,
                            cssPath : ['/kindeditor/plugins/code/prettify.css'],
                            filterMode: true,
                            allowFileManager: false,
                            uploadJson: '/file/upload'
                        });
                        editor.html($("#content").html());
                    });
                    function save() {
                        $("#addBtn").attr("disabled", true);
                        editor.sync();
                        var content = $("#kindeditor").val();
                        var title = $("#title").val();
                        $.ajax({
                            url:"/post/publish",
                            type:"POST",
                            dataType:"json",
                            contentType:"application/x-www-form-urlencoded; charset=utf-8",
                            data:{"content":content,"title":title},
                            success:function (result) {
                                if(result.code == 0){
                                    layer.msg(result.msg);
                                    //alert(data.msg);
                                    var b= result.src;
                                    setTimeout(function(){
                                        location.href="/post/post/"+result.src;
                                    }, 2000);
                                    //window.location="/post/post/"+data.src;
                                }else{
                                    layer.msg(result.msg);
                                    $("#addBtn").attr("disabled", false);
                                    //alert(data.msg);
                                }
                            }
                        });
                    }

                </script>


                <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
                <!--[if lt IE 9]>
                <!--<script src="js/html5.js"></script></script>-->
                <![endif]-->

        </head>

        <body>

                <!-- Start of Header -->
                <div class="header-wrapper">
                        <header>
                                <div class="container">


                                        <div class="logo-container">
                                                <!-- Website Logo -->
                                                <a href="/post/main"  title="Peanut论坛">
                                                        <img src="/images/logo.png" alt="Peanut论坛">
                                                </a>
                                                <span class="tag-line">Peanut论坛</span>
                                        </div>


                                        <!-- Start of Main Navigation -->
                                        <nav class="main-nav">
                                                <div class="menu-top-menu-container">
                                                    <ul id="menu-top-menu" class="clearfix">
                                                        <li><a href="/post/main">主页</a></li>
                                                        <li class="current-menu-item"><a href="/post/post_list">所有帖子</a></li>
                                                        <li><a href="/post/top">置顶</a></li>
                                                        <li><a href="/post/rank">排行榜</a></li>
                                                        <li><a href="#">Contact</a></li>
                                                        <c:choose>
                                                            <c:when test="${empty sessionScope.user}">
                                                                <li><a href="/user/center">登录</a></li>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li><a href="#">${sessionScope.user.nickname}</a>
                                                                    <ul class="sub-menu">
                                                                        <li><a href="/user/user">个人中心</a></li>
                                                                        <li><a href="/user/exit">退出</a></li>
                                                                    </ul>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <%--<li><a href="#">More</a>--%>
                                                        <%--<ul class="sub-menu">--%>
                                                        <%--<li><a href="#">Full Width</a></li>--%>
                                                        <%--<li><a href="#">Elements</a></li>--%>
                                                        <%--<li><a href="#">Sample Page</a></li>--%>
                                                        <%--</ul>--%>
                                                        <%--</li>--%>
                                                    </ul>
                                                </div>
                                        </nav>
                                        <!-- End of Main Navigation -->

                                </div>
                        </header>
                </div>
                <!-- End of Header -->

                <!-- Start of Search Wrapper -->
                <div class="search-area-wrapper">
                        <div class="search-area container">
                                <h3 class="search-header">Knowledge Base</h3>
                                <p class="search-tag-line">Fear can hold you prisoner ,hope can set you free.A strong man can save himself,a great man can save another</p>
                        </div>
                </div>
                <!-- End of Search Wrapper -->

                <!-- Start of Page Container -->
                <div class="page-container">
                        <div class="container">
                                <div class="row">

                                        <!-- start of page content -->
                                        <div class="span8 page-content">
                                                <h3>标题</h3><input id="title" type="text" size="30" style="width: 500px;"/>
                                                <textarea id="kindeditor" cols="0" rows="0" style="visibility:hidden"></textarea>
                                                <input id="addBtn" class="btn" type="button"  onclick="save()" value="发表">
                                        </div>
                                </div>
                        </div>
                </div>
                <!-- End of Page Container -->


                <!-- Start of Footer -->
                <footer id="footer-wrapper">
                        <div id="footer" class="container">
                                <div class="row">

                                        <div class="span3">
                                                <section class="widget">
                                                        <div class="textwidget">
                                                        </div>
                                                </section>
                                        </div>

                                        <div class="span3">
                                        </div>

                                        <div class="span3">
                                                <section class="widget">
                                                        <div id="twitter_update_list">
                                                                <ul>
                                                                </ul>
                                                        </div>
                                                        
                                                </section>
                                        </div>

                                        <div class="span3">
                                                <section class="widget">
                                                        <div class="flickr-photos" id="basicuse">
                                                        </div>
                                                </section>
                                        </div>

                                </div>
                        </div>
                        <!-- end of #footer -->

                        <!-- Footer Bottom -->
                        <div id="footer-bottom-wrapper">
                                <div id="footer-bottom" class="container">
                                        <div class="row">
                                                <div class="span6">
                                                        <p class="copyright">
                                                        </p>
                                                </div>
                                                <div class="span6">
                                                        <!-- Social Navigation -->
                                                </div>
                                        </div>
                                </div>
                        </div>
                        <!-- End of Footer Bottom -->

                </footer>
                <!-- End of Footer -->

                <!--<a href="#top" id="scroll-top"></a>-->

                <!-- script -->
                <script type='text/javascript' src='/js/jquery.easing.1.3.js'></script>
                <script type='text/javascript' src='/js/prettyphoto/jquery.prettyPhoto.js'></script>
                <%--<script type='text/javascript' src='/js/jflickrfeed.js'></script>--%>
                <script type='text/javascript' src='/js/jquery.liveSearch.js'></script>
                <script type='text/javascript' src='/js/jquery.form.js'></script>
                <script type='text/javascript' src='/js/jquery.validate.min.js'></script>
                <script type='text/javascript' src='/js/custom.js'></script>

        </body>
</html>
