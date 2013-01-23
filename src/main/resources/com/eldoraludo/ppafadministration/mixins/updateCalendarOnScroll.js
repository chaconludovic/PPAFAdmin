(function ($) {

    $.extend(Tapestry.Initializer, {
        updateCalendarOnScroll:function (specs) {
            $('#' + specs.clientId).bind(Tapestry.ZONE_UPDATED_EVENT, function(event){


                $('#' + specs.clientId + ' .periode-wrapper').children().clone().appendTo('#full-calendar');
                var idLastDate = $('#full-calendar div.event-dt-item:last-child').attr('id');
                var valueLastDate = $('#full-calendar div.event-dt-item:last-child div.dt-box h4').html();
                $('#nav-calendar-view li:last-child a').attr('href','#' + idLastDate);
                $('#nav-calendar-view li:last-child a span').text(valueLastDate);
                $('#loader-calendar').removeClass('loading-on');
                $('#loader-calendar').addClass('loading-off');
            });
        }
    });

})(jQuery);