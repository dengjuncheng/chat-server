$( document ).ready(function() {
                
                $("#about_scroll").fadeOut();   
                $("#post_scroll").fadeOut();
                $("#private_letter_scroll").fadeOut();
                $("#modify_scroll").fadeOut();

                $("#about").click(function(){
                    $("#index").fadeOut();
                    $("#about_scroll").fadeIn();
                    $('#about_left').addClass('animated slideInLeft');
                    $('#about_right').addClass('animated slideInRight');
                    });
                $("#my_post").click(function(){
                    $("#index").fadeOut();
                    $("#post_scroll").fadeIn();
                    $('#work_left').addClass('animated slideInLeft');
                    $('#work_right').addClass('animated slideInRight');
                    });
                $("#private_letter").click(function(){
                    $("#index").fadeOut();
                    $("#private_letter_scroll").fadeIn();
                    $('#contact_left').addClass('animated slideInLeft');
                    $('#contact_right').addClass('animated slideInRight');
                    });
                
                $(".back").click(function(){
                    $(".pages").fadeOut();
                    $("#index").fadeIn();
                    $('#index_left').addClass('animated slideInLeft');
                    $('#index_right').addClass('animated slideInRight');
                    });
                $("#modify_info").click(function () {
                    $("#about_scroll").fadeOut();
                    $("#modify_scroll").fadeIn();
                    $("#modify_left").addClass('animated slideInLeft');
                    $("#modify_right").addClass('animated slideInRight');
                });
           
		});