(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureSwitchradio: function(specs) {
            $(document).ready( function(){
                $(".cb-enable").click(function(){
                    var parent = $(this).parents('.switch');
                    $('.cb-disable',parent).removeClass('selected');
                    $(this).addClass('selected');
                    $('.checkbox',parent).attr('checked', true);
                });
                $(".cb-disable").click(function(){
                    var parent = $(this).parents('.switch');
                    $('.cb-enable',parent).removeClass('selected');
                    $(this).addClass('selected');
                    $('.checkbox',parent).attr('checked', false);
                });
            });

         }
    });
})(jQuery);