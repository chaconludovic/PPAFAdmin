(function ($) {

    $.extend(Tapestry.Initializer, {
        setupSelectUniform:function (specs) {
            $('#' + specs.elementId).uniform();
        }
    });
})(jQuery);