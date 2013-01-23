(function ($) {

    $.extend(Tapestry.Initializer, {
        stickemConfiguration:function (specs) {
            $("#" + specs.elementId).affix();
        }
    });

})(jQuery);