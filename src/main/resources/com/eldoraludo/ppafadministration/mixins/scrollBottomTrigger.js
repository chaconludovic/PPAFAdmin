(function ($) {

    $.extend(Tapestry.Initializer, {
        scrollBottomTrigger:function (specs) {
            $(window).scroll(function() {

               if($(window).scrollTop() + $(window).height() == $(document).height()) {
                   if ($('#' + specs.elementId).hasClass('unbind-event')) {
                       $('#loader-calendar').removeClass('loading-on');
                       $('#loader-calendar').addClass('loading-off');
                   } else {
                       $('#' + specs.elementId).trigger('click');
                       $('#loader-calendar').removeClass('loading-off');
                       $('#loader-calendar').addClass('loading-on');
                   }
               }


            });


        }
    });

})(jQuery);