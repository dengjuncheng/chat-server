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

                <title>Peanut</title>

                <link rel="shortcut icon" href="/images/favicon.png" />



                <!-- Style Sheet-->
                <link rel="stylesheet" href="style.css"/>
                <link rel='stylesheet' id='bootstrap-css-css'  href='/css/bootstrap5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='responsive-css-css'  href='/css/responsive5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='pretty-photo-css-css'  href='/js/prettyphoto/prettyPhotoaeb9.css?ver=3.1.4' type='text/css' media='all' />
                <link rel='stylesheet' id='main-css-css'  href='/css/main5152.css?ver=1.0' type='text/css' media='all' />
                <%--<link rel='stylesheet' id='custom-css-css'  href='/css/custom5152.html?ver=1.0' type='text/css' media='all' />--%>


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

                                <%--<form id="search-form" class="search-form clearfix" method="get" action="#" autocomplete="off">--%>
                                        <%--<input class="search-term required" type="text" id="s" name="s" placeholder="输入需要查询的内容" title="* Please enter a search term!" />--%>
                                        <%--<input class="search-btn" type="submit" value="Search" />--%>
                                        <%--<div id="search-error-container"></div>--%>
                                <%--</form>--%>
                        </div>
                </div>
                <!-- End of Search Wrapper -->

                <!-- Start of Page Container -->
                <div class="page-container">
                        <div class="container">
                                <div class="row">

                                        <!-- start of page content -->
                                        <div class="span8 main-listing">
                                                <c:forEach var="item" items="${postList}">
                                                <article class="format-standard type-post hentry clearfix">

                                                        <header class="clearfix">

                                                                <h3 class="post-title">
                                                                        <a href="/post/post/${item.id}">${item.title}</a>
                                                                </h3>

                                                                <div class="post-meta clearfix">
                                                                        <span class="date"><fmt:formatDate type="date" value="${item.publishDate}"/></span>
                                                                        <span class="category"><a href="/user/other/${item.stuId}" title="创建者">${item.userName}</a></span>
                                                                        <span class="comments"><a href="#" title="评论数量">${item.commentNum}人评论</a></span>
                                                                        <span class="like-count">${item.likeNum}</span>
                                                                </div><!-- end of post meta -->

                                                        </header>
                                                        <%--图片部分--%>
                                                        <%--<a href="#" title="${item.title}"><img width="770" height="501" src="/images/temp/living-room-770x501.jpg" class="attachment-std-thumbnail wp-post-image" alt="Living room"></a>--%>
                                                        <%--视频部分--%>
                                                        <%--<div class="post-video">--%>
                                                                <%--<div class="video-wrapper">--%>
                                                                        <%--<iframe src="" width="500" height="281" frameborder="0" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen=""></iframe>--%>
                                                                <%--</div>--%>
                                                        <%--</div>--%>
                                                        <p>${item.shortDesc} <a class="readmore-link" style="color: #EE2C2C;" href="/post/post/${item.id}">Read more</a></p>

                                                </article>
                                                </c:forEach>


                                                <div id="pagination">
                                                        <c:forEach var="i" begin="1" end="${pageTotal}">
                                                        <c:choose>
                                                                <c:when test="${pageNum==i}">
                                                                        <a href="/post/post_list/${i}" class="btn active">${i}</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                        <a href="/post/post_list/${i}" class="btn">${i}</a>
                                                                </c:otherwise>
                                                        </c:choose>
                                                        </c:forEach>
                                                        <c:choose>
                                                                <c:when test="${pageNum==pageTotal}">
                                                                        <a href="#" class="btn active">Next »</a>
                                                                        <a href="#" class="btn active">Last »</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                        <a href="/post/post_list/${pageNum+1}" class="btn">Next »</a>
                                                                        <a href="/post/post_list/${pageTotal}" class="btn">Last »</a>
                                                                </c:otherwise>
                                                        </c:choose>

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
                                                        <h3 class="title">置顶</h3>
                                                        <ul class="articles">
                                                                <c:forEach var="item" items="${topList}">
                                                                <li class="article-entry standard">
                                                                        <h4><a href="/post/post/${item.id}">${item.title}</a></h4>
                                                                        <span class="article-meta"><fmt:formatDate type="date" value="${item.publishDate}"/> by <a href="/user/other/${item.stuId}" title="创建者">${item.userName}</a></span>
                                                                        <span class="like-count">${item.likeNum}</span>
                                                                </li>
                                                                </c:forEach>
                                                        </ul>
                                                </section>



                                                <%--<section class="widget"><h3 class="title">Categories</h3>--%>
                                                        <%--<ul>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Advanced Techniques</a> </li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Designing in WordPress</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Server &amp; Database</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet, ">Theme Development</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">Website Dev</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet,">WordPress for Beginners</a></li>--%>
                                                                <%--<li><a href="#" title="Lorem ipsum dolor sit amet, ">WordPress Plugins</a></li>--%>
                                                        <%--</ul>--%>
                                                <%--</section>--%>

                                                <section class="widget">
                                                        <h3 class="title">最近评论</h3>
                                                        <ul id="recentcomments">
                                                                <c:forEach var="item" items="${commentList}">
                                                                <li class="recentcomments"><a href="/user/other/${item.userId}" rel="external nofollow" class="url">${item.userName}</a> on <a href="/post/post/${item.postId}">${item.postName}</a></li>
                                                                </c:forEach>
                                                        </ul>
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
                                                        <div id="twitter_update_list">
                                                                <%--<ul>--%>
                                                                        <%--<li>No Tweets loaded !</li>--%>
                                                                <%--</ul>--%>
                                                        </div>
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
                <script type='text/javascript' src='/js/jquery.easing.1.34e44.js?ver=1.3'></script>
                <script type='text/javascript' src='/js/prettyphoto/jquery.prettyPhotoaeb9.js?ver=3.1.4'></script>
                <script type='text/javascript' src='/js/jquery.liveSearchd5f7.js?ver=2.0'></script>
				<script type='text/javascript' src='/js/jflickrfeed.js'></script>
                <script type='text/javascript' src='/js/jquery.formd471.js?ver=3.18'></script>
                <script type='text/javascript' src='/js/jquery.validate.minfc6b.js?ver=1.10.0'></script>
                <script type='text/javascript' src='/js/custom5152.js?ver=1.0'></script>

        </body>
</html>
