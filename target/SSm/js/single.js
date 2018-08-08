function addComment(data) {
    var $content =$("#commentContent");
    //alert(JSON.stringify(data));
    $("#commentContent").empty();
    for(var i=0; i< data.length ; i++){
        var $P_li = $('<li class="comment even thread-even depth-1"></li>');
        var $P_article = $('<article id="comment-2"></article>');
        var $P_a1 = $('<a href="#"></a>');
        var $P_img = $('<img class="avatar avatar-60 photo" height="60" width="60"/>');
        var $P_div1 = $('<div class="comment-meta"></div>');
        var $P_h5 = $('<h5 class="author"></h5>');
        var $P_cite = $('<cite class="fn"></cite>');
        var $P_a2 = $('<a href="#" rel="external nofollow" class="url"></a>');
        var $P_sp = $('<span>-</span>');
        var $P_a3 = $('<a class="comment-reply-link" href="#">回复</a>');
        var $P_p1 = $('<p class="date"></p>');
        //var $P_a4 = $('<a href="#"></a>');
        var $P_time = $('<time datetime="2013-02-26T13:18:47+00:00"></time>');
        var $P_div2 = $('<div class="comment-body"></div>');
        var $P_p2 = $('<p></p>');

        $P_li.append($P_article);
        $P_article.append($P_a1).append($P_div1).append($P_div2);
        $P_a1.append($P_img);
        $P_div1.append($P_h5).append($P_p1);
        $P_h5.append($P_cite).append($P_sp).append($P_a3);
        $P_cite.append($P_a2);
        $P_p1.append($P_time);
        //$P_a4.append($P_time);
        $P_div2.append($P_p2);
        $P_a1.attr("href","/user/other/"+data[i].userId);
        $P_img.attr("src", data[i].headPic);
        $P_a2.attr("href", "/user/other/"+data[i].userId);
        $P_a2.text(data[i].nickName);
        //回复接口还没实现$P_a3.attr("href","");
        $P_a3.attr("id",data[i].id+"^"+data[i].nickName);
        $P_a3.attr("onclick","reply(this.id)");
        $P_time.text(formatDate(data[i].publishDate));
        $P_p2.text(data[i].content);
        if(data[i].isReply == 0){
            $content.append($P_li);
            continue;
        }

        var $C_ul = $('<ul class="children"></ul>');
        var $C_li = $('<li class="comment byuser comment-author-saqib-sarwar bypostauthor odd alt depth-2" id="li-comment-3"></li>');
        var $C_article = $('<article id="comment-3"></article>');
        var $C_a1 = $('<a href="#"></a>');
        var $C_img = $('<img class="avatar avatar-60 photo" height="60" width="60"/>');
        var $C_div1 = $('<div class="comment-meta"></div>');
        var $C_h5 = $('<h5 class="author"></h5>');
        var $C_cite = $('<cite class="fn"></cite>');
        var $C_a2 = $('<a href="#" rel="external nofollow" class="url"></a>');
        var $C_sp = $('<span>-</span>');
        var $C_a3 = $('<a class="comment-reply-link" href="#">回复</a>');
        var $C_p1 = $('<p class="date"></p>');
        //var $C_a4 = $('<a href="#"></a>');
        var $C_time = $('<time datetime="2013-02-26T13:18:47+00:00"></time>');
        var $C_div2 = $('<div class="comment-body"></div>');
        var $C_p2 = $('<p></p>');

        $P_li.append($C_ul);
        $C_ul.append($C_li);
        $C_li.append($C_article);
        $C_article.append($C_a1).append($C_div1).append($C_div2);
        $C_a1.append($C_img);
        $C_div1.append($C_h5).append($C_p1);
        $C_h5.append($C_cite).append($C_sp).append($C_a3);
        $C_cite.append($C_a2);
        $C_p1.append($C_time);
        //$C_a4.append($C_time);
        $C_div2.append($C_p2);
        $C_a1.attr("href","/user/other/"+data[i].replyComment.userId);
        $C_img.attr("src", data[i].replyComment.headPic);
        $C_a2.attr("href", "/user/other/"+data[i].replyComment.userId);
        $C_a2.text(data[i].replyComment.nickName);
        //回复接口还没实现$P_a3.attr("href","");
        $C_a3.attr("id",data[i].replyComment.id+"^"+data[i].replyComment.nickName);
        $C_a3.attr("onclick","reply(this.id)");
        $C_time.text(formatDate(data[i].replyComment.publishDate));
        $C_p2.text(data[i].replyComment.content);
        $content.append($P_li);
    }
}

function add0(m){return m<10?'0'+m:m }
function formatDate(now) {
    var time = new Date(now);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    return now;
}
