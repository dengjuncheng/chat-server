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

                <title>peanut论坛</title>

                <link rel="shortcut icon" href="/images/favicon.png" />


                

                <!-- Style Sheet-->
                <link rel="stylesheet" href="/style.css"/>
                <link rel='stylesheet' id='bootstrap-css-css'  href='/css/bootstrap5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='responsive-css-css'  href='/css/responsive5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='pretty-photo-css-css'  href='/js/prettyphoto/prettyPhotoaeb9.css?ver=3.1.4' type='text/css' media='all' />
                <link rel='stylesheet' id='main-css-css'  href='/css/main5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='green-skin-css'  href='/css/green-skin5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='custom-css-css'  href='/css/custom5152.html?ver=1.0' type='text/css' media='all' />


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
                                                                <li class="current-menu-item"><a href="/post/main">主页</a></li>
                                                                <li><a href="/post/post_list">所有帖子</a></li>
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

                                                <!-- Basic Home Page Template -->
                                                <div class="row separator">
                                                        <section class="span4 articles-list">
                                                                <h3>置顶</h3>
                                                                <ul class="articles">
                                                                        <c:forEach var="item" items="${topList}">
                                                                        <li class="article-entry standard">
                                                                                <h4><a href="/post/post/${item.id}">${item.title}</a></h4>
                                                                                <span class="article-meta"><fmt:formatDate type="date" value="${item.publishDate}"/> by <a href="/user/other/${item.stuId}" title="创建者">${item.userName}</a></span>
                                                                                <span class="like-count" onclick="">${item.likeNum}</span>
                                                                        </li>
                                                                        </c:forEach>

                                                                </ul>
                                                        </section>


                                                        <section class="span4 articles-list">
                                                                <h3>最新</h3>
                                                                <ul class="articles">
                                                                        <c:forEach var="item" items="${recentList}">
                                                                        <li class="article-entry standard">
                                                                                <h4><a href="/post/post/${item.id}">${item.title}</a></h4>
                                                                                <span class="article-meta"><fmt:formatDate type="date" value="${item.publishDate}"/> by <a href="/user/other/${item.stuId}" title="创建者">${item.userName}</a></span>
                                                                                <span class="like-count">${item.likeNum}</span>
                                                                        </li>
                                                                        </c:forEach>
                                                                </ul>
                                                        </section>
                                                </div>
                                        </div>
                                        <!-- end of page content -->


                                        <!-- start of sidebar -->
                                        <aside class="span4 page-sidebar">

                                                <section class="widget">
                                                        <div class="support-widget">
                                                                <h3 class="title"><a href="/post/new_post">发布</a> </h3>
                                                                <p class="intro">请文明发帖</p>
                                                        </div>
                                                </section>

                                                <section class="widget">
                                                        <div class="quick-links-widget">
                                                                <h3 class="title">快速链接</h3>
                                                                <ul id="menu-quick-links" class="menu clearfix">
                                                                        <li><a href="/post/main">主页</a></li>
                                                                        <li><a href="/post/post_list">所有帖子</a></li>
                                                                        <li><a href="/post/top">置顶</a></li>
                                                                        <li><a href="/post/rank">排行榜</a></li>
                                                                </ul>
                                                        </div>
                                                </section>

                                                <section class="widget">
                                                        <h3 class="title">分类</h3>
                                                        <div class="tagcloud">
                                                                <a href="#" class="btn btn-mini">置顶</a>
                                                                <a href="#" class="btn btn-mini">最多点击</a>
                                                                <a href="#" class="btn btn-mini">最多赞</a>
                                                                <a href="#" class="btn btn-mini">最多评论</a>
                                                        </div>
                                                </section>

                                        </aside>
                                        <!-- end of sidebar -->
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
                                                        <%--<h3 class="title">How it works</h3>--%>
                                                        <div class="textwidget">
                                                                <%--<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. </p>--%>
                                                                <%--<p>Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. </p>--%>
                                                        </div>
                                                </section>
                                        </div>

                                        <div class="span3">
                                                <%--<section class="widget"><h3 class="title">Categories</h3>--%>
                                                        <ul>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Advanced Techniques</a> </li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Designing in WordPress</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Server &amp; Database</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet, ">Theme Development</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Website Dev</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">WordPress for Beginners</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet, ">WordPress Plugins</a></li>--%>
                                                        </ul>
                                                </section>
                                        </div>

                                        <div class="span3">
                                                <section class="widget">
                                                        <%--<h3 class="title">Latest Tweets</h3>--%>
                                                        <%--<div id="twitter_update_list">--%>
                                                                <%--<ul>--%>
                                                                        <%--<li>No Tweets loaded !</li>--%>
                                                                <%--</ul>--%>
                                                        <%--</div>--%>
                                                        <%--<script src="http://twitterjs.googlecode.com/svn/trunk/src/twitter.min.js" type="text/javascript"></script>--%>
                                                        <%--<script type="text/javascript" >--%>
                                                                <%--getTwitters("twitter_update_list", {--%>
                                                                        <%--id: "960development",--%>
                                                                        <%--count: 3,--%>
                                                                        <%--enableLinks: true,--%>
                                                                        <%--ignoreReplies: true,--%>
                                                                        <%--clearContents: true,--%>
                                                                        <%--template: "%text% <span>%time%</span>"--%>
                                                                <%--});--%>
                                                        <%--</script>--%>
                                                </section>
                                        </div>

                                        <div class="span3">
                                                <%--<section class="widget">--%>
                                                        <%--<h3 class="title">Flickr Photos</h3>--%>
                                                        <%--<div class="flickr-photos" id="basicuse">--%>
                                                        <%--</div>--%>
                                                <%--</section>--%>
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
                                                                <%--Copyright © 2013. All Rights Reserved by KnowledgeBase.Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a>--%>
                                                        </p>
                                                </div>
                                                <div class="span6">
                                                        <!-- Social Navigation -->
                                                        <ul class="social-nav clearfix">
                                                                <%--<li class="linkedin"><a target="_blank" href="#"></a></li>--%>
                                                                <%--<li class="stumble"><a target="_blank" href="#"></a></li>--%>
                                                                <%--<li class="google"><a target="_blank" href="#"></a></li>--%>
                                                                <%--<li class="deviantart"><a target="_blank" href="#"></a></li>--%>
                                                                <%--<li class="flickr"><a target="_blank" href="#"></a></li>--%>
                                                                <%--<li class="skype"><a target="_blank" href="skype:#?call"></a></li>--%>
                                                                <%--<li class="rss"><a target="_blank" href="#"></a></li>--%>
                                                                <%--<li class="twitter"><a target="_blank" href="#"></a></li>--%>
                                                                <%--<li class="facebook"><a target="_blank" href="#"></a></li>--%>
                                                        </ul>
                                                </div>
                                        </div>
                                </div>
                        </div>
                        <!-- End of Footer Bottom -->

                </footer>
                <!-- End of Footer -->

                <a href="#top" id="scroll-top"></a>

                <!-- script -->
                <script type='text/javascript' src='/js/jquery-1.8.3.min.js'></script>
                <script type='text/javascript' src='/js/jquery.easing.1.3.js'></script>
                <script type='text/javascript' src='/js/prettyphoto/jquery.prettyPhoto.js'></script>
                <script type='text/javascript' src='/js/jflickrfeed.js'></script>
                <script type='text/javascript' src='/js/jquery.liveSearch.js'></script>
                <script type='text/javascript' src='/js/jquery.form.js'></script>
                <script type='text/javascript' src='/js/jquery.validate.min.js'></script>
                <script type='text/javascript' src='/js/custom.js'></script>

        </body>
</html>