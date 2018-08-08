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

                <title>Peanut论坛</title>

                <link rel="shortcut icon" href="/images/favicon.png" />


                

                <!-- Style Sheet-->
                <%--<link rel="stylesheet" href="style.css"/>--%>
                <link rel='stylesheet' id='bootstrap-css-css'  href='/css/bootstrap5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='responsive-css-css'  href='/css/responsive5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='pretty-photo-css-css'  href='/js/prettyphoto/prettyPhotoaeb9.css?ver=3.1.4' type='text/css' media='all' />
                <link rel='stylesheet' id='main-css-css'  href='/css/main5152.css?ver=1.0' type='text/css' media='all' />
                <link rel='stylesheet' id='custom-css-css'  href='/css/custom5152.html?ver=1.0' type='text/css' media='all' />
                <script type='text/javascript' src='/js/jquery-1.8.3.min.js'></script>
                <meta name="renderer" content="webkit">
                <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
                <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
                <link href="/layui/layui.all.js">
                <link rel="stylesheet" href="/layui/css/layui.css"  media="all">
                <script type='text/javascript' src='/layui/layui.js'></script>
                <script type="text/javascript" src="/js/single.js"></script>
                <script type="text/javascript" src="/js/jwplayer.js"></script>

                <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
                <!--[if lt IE 9]>
                <!--<script src="js/html5.js"></script></script>-->
                <!--<![endif]&ndash;&gt;-->
                <script>
                    layui.use("laypage",function () {
                        var laypage = layui.laypage;
                        var postId = ${post.id};
                        laypage.render({
                            elem : 'commentPage',
                            count: ${commentPageCount},
                            limit: 5,
                            jump:function (obj, first) {
                                $.ajax({
                                    url:"/comment/page",
                                    type:"POST",
                                    dataType:"json",
                                    contentType:"application/x-www-form-urlencoded; charset=utf-8",
                                    data:{"currentPage":obj.curr,"postId":postId},
                                    success:function (data) {
                                        addComment(data);
                                    }
                                });
                            }
                        });
                    });

                    function reply(info) {
                        var idName = [];
                        var postId = ${post.id}
                         idName= info.split("^");//第一个元素是评论id，第二个元素是原评论的人
                         if(info[0]===null){
                             return;
                         }
                        layui.use('layer',function () {
                            var layer = layui.layer;
                            layer.prompt({
                                formType: 2,
                                value: '',
                                title: '回复 '+idName[1]+'：',
                                area: ['400px', '150px'] //自定义文本域宽高
                            }, function(value, index, elem){
                                if(value === null ||value === ""){
                                    layer.msg("回复内容不能为空");
                                    layer.close(index);
                                    return;
                                }
                                $.ajax({
                                    url :"/comment/reply",
                                    type:"POST",
                                    dataType:"json",
                                    contentType:"application/x-www-form-urlencoded; charset=utf-8",
                                    data:{"toId":info[0],"content":value,"postId":postId},
                                    success:function(result) {
                                        if(result.code !== 0){
                                            layer.msg(result.msg);
                                            return;
                                        }
                                        layer.msg(result.msg);
                                        window.location.reload(true);
                                    }
                                });
                                layer.close(index);
                            });
                        });
                    }
                    layui.use('layer',function () {
                            var layer = layui.layer;
                            if(${commentResult != null}){
                                layer.msg("${commentResult.msg}");
                            }
                    });
                </script>
        </head>

        <body>

                <!-- Start of Header -->
                <div class="header-wrapper">
                        <header>
                                <div class="container">


                                        <div class="logo-container">
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

                                                <%--<ul class="breadcrumb">--%>
                                                        <%--<li><a href="#">Knowledge Base Theme</a><span class="divider">/</span></li>--%>
                                                        <%--<li><a href="#" title="View all posts in Server &amp; Database">Server &amp; Database</a> <span class="divider">/</span></li>--%>
                                                        <%--<li class="active">Integrating WordPress with Your Website</li>--%>
                                                <%--</ul>--%>

                                                <article class=" type-post format-standard hentry clearfix">

                                                        <h1 class="post-title">${post.title}</h1>

                                                        <div class="post-meta clearfix">
                                                                <span class="date"><fmt:formatDate type="date" value="${post.publishDate}"/></span>
                                                                <c:choose>
                                                                        <c:when test="${sessionScope.user.id == post.stuId}">
                                                                                 <span class="category"><a href="/user/user" title="发布人">${post.userName}</a></span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                                <span class="category"><a href="/user/other/${post.stuId}" title="发布人">${post.userName}</a></span>
                                                                        </c:otherwise>
                                                                </c:choose>
                                                                <span class="comments"><a href="#" title="查看数">${post.browserNum} 查看</a></span>
                                                                <span class="like-count">${post.likeNum}</span>
                                                        </div><!-- end of post meta -->

                                                        <!-- 这里插入内容，-->
                                                        <p>${post.content}</p>
                                                </article>

                                                <div class="like-btn">

                                                        <form id="like-it-form" action="#" method="post">
                                                                <span class="like-it "  id="likeBtn">${post.likeNum}</span>
                                                                <input type="hidden" name="post_id" id="post_id" value="${post.id}">
                                                                <input type="hidden" name="action" value="like_it">
                                                        </form>
                                                        <%--<script>--%>
                                                                <%--function addLike() {--%>
                                                                    <%----%>
                                                                <%--}--%>
                                                        <%--</script>--%>

                                                </div>

                                                <section id="comments">

                                                        <h3 id="comments-title">(${commentPageCount}) 评论</h3>
                                                        <div id="respond">

                                                                <form action="/comment/new_comment" method="post" id="commentform">

                                                                        <div>
                                                                                <label for="comment">添加新评论:</label>
                                                                                <textarea class="span8" name="comment" id="comment" cols="58" rows="10"></textarea>
                                                                        </div>
                                                                        <div>
                                                                                <input type="hidden" name="postId" id="postId" value="${post.id}"/>
                                                                                <input class="btn" name="submit" type="submit" id="submit"  value="评论">
                                                                        </div>

                                                                </form>

                                                        </div>


                                                        <ol class="commentlist" id="commentContent">

                                                                <li class="comment even thread-even depth-1" id="li-comment-2">
                                                                        <article id="comment-2">

                                                                                <a href="#">
                                                                                        <img alt="" src="http://1.gravatar.com/avatar/50a7625001317a58444a20ece817aeca?s=60&amp;d=http%3A%2F%2F1.gravatar.com%2Favatar%2Fad516503a11cd5ca435acc9bb6523536%3Fs%3D60&amp;r=G" class="avatar avatar-60 photo" height="60" width="60">
                                                                                </a>

                                                                                <div class="comment-meta">

                                                                                        <h5 class="author">
                                                                                                <cite class="fn">
                                                                                                        <a href="#" rel="external nofollow" class="url">John Doe</a>
                                                                                                </cite>
                                                                                                - <a class="comment-reply-link" href="#">Reply</a>
                                                                                        </h5>

                                                                                        <p class="date">
                                                                                                <a href="#">
                                                                                                        <time datetime="2013-02-26T13:18:47+00:00">February 26, 2013 at 1:18 pm</time>
                                                                                                </a>
                                                                                        </p>

                                                                                </div><!-- end .comment-meta -->

                                                                                <div class="comment-body">
                                                                                        <p>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.</p>
                                                                                        <p>Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Typi non habent claritatem insitam; est usus legentis in iis qui facit eorum claritatem.</p>
                                                                                </div><!-- end of comment-body -->

                                                                        </article><!-- end of comment -->

                                                                        <ul class="children">

                                                                                <li class="comment byuser comment-author-saqib-sarwar bypostauthor odd alt depth-2" id="li-comment-3">
                                                                                        <article id="comment-3">

                                                                                                <a href="#">
                                                                                                        <img alt="" src="http://0.gravatar.com/avatar/2df5eab0988aa5ff219476b1d27df755?s=60&amp;d=http%3A%2F%2F0.gravatar.com%2Favatar%2Fad516503a11cd5ca435acc9bb6523536%3Fs%3D60&amp;r=G" class="avatar avatar-60 photo" height="60" width="60">
                                                                                                </a>

                                                                                                <div class="comment-meta">

                                                                                                        <h5 class="author">
                                                                                                                <cite class="fn">saqib sarwar</cite>
                                                                                                                - <a class="comment-reply-link" href="#">Reply</a>
                                                                                                        </h5>

                                                                                                        <p class="date">
                                                                                                                <a href="#">
                                                                                                                        <time datetime="2013-02-26T13:20:14+00:00">February 26, 2013 at 1:20 pm</time>
                                                                                                                </a>
                                                                                                        </p>

                                                                                                </div><!-- end .comment-meta -->

                                                                                                <div class="comment-body">
                                                                                                        <p>Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Typi non habent claritatem insitam; est usus legentis in iis qui facit eorum claritatem. Investigationes demonstraverunt lectores legere me lius quod ii legunt saepius. Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum. Mirum est notare quam littera gothica, quam nunc putamus parum claram, anteposuerit litterarum formas humanitatis per seacula quarta decima et quinta decima. Eodem modo typi, qui nunc nobis videntur parum clari, fiant sollemnes in futurum.</p>
                                                                                                </div><!-- end of comment-body -->

                                                                                        </article><!-- end of comment -->

                                                                                </li>
                                                                        </ul>
                                                                </li>
                                                                <li class="comment even thread-odd thread-alt depth-1" id="li-comment-4">
                                                                        <article id="comment-4">

                                                                                <a href="#">
                                                                                        <img alt="" src="http://1.gravatar.com/avatar/50a7625001317a58444a20ece817aeca?s=60&amp;d=http%3A%2F%2F1.gravatar.com%2Favatar%2Fad516503a11cd5ca435acc9bb6523536%3Fs%3D60&amp;r=G" class="avatar avatar-60 photo" height="60" width="60">
                                                                                </a>

                                                                                <div class="comment-meta">

                                                                                        <h5 class="author">
                                                                                                <cite class="fn"><a href="#" rel="external nofollow" class="url">John Doe</a></cite>
                                                                                                - <a class="comment-reply-link" href="#">Reply</a>
                                                                                        </h5>

                                                                                        <p class="date">
                                                                                                <a href="http://knowledgebase.inspirythemes.com/integrating-wordpress-with-your-website/#comment-4">
                                                                                                        <time datetime="2013-02-26T13:27:04+00:00">February 26, 2013 at 1:27 pm</time>
                                                                                                </a>
                                                                                        </p>

                                                                                </div><!-- end .comment-meta -->

                                                                                <div class="comment-body">
                                                                                        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. </p>
                                                                                        <p>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum.</p>
                                                                                </div><!-- end of comment-body -->

                                                                        </article><!-- end of comment -->
                                                                </li>
                                                        </ol>
                                                    <div id="commentPage" ></div>

                                                </section><!-- end of comments -->

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
                                                        <h3 class="title">置顶文章</h3>
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
                                                </div>
                                                <div class="span6">
                                                </div>
                                        </div>
                                </div>
                        </div>
                        <!-- End of Footer Bottom -->

                </footer>
                <!-- End of Footer -->

                <a href="#top" id="scroll-top"></a>

                <!-- script -->
                <script type='text/javascript' src='/js/jquery.easing.1.3.js'></script>
                <script type='text/javascript' src='/js/prettyphoto/jquery.prettyPhoto.js'></script>
                <script type='text/javascript' src='/js/jflickrfeed.js'></script>
                <script type='text/javascript' src='/js/jquery.liveSearch.js'></script>
                <script type='text/javascript' src='/js/jquery.form.js'></script>
                <script type='text/javascript' src='/js/jquery.validate.min.js'></script>
                <script type='text/javascript' src='/js/custom.js'></script>

        </body>
</html>
