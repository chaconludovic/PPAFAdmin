(function ($) {

    $.extend(Tapestry.Initializer, {
        setupSelectChosen:function (specs) {
            $('#' + specs.elementId).chosen({placeholder_text:specs.placeholder, allow_single_deselect:specs.deselect});
        }
    });
})(jQuery);