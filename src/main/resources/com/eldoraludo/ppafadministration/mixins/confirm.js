(function($) {

    $.extend(Tapestry.Initializer, {
        setupConfirm: function(specs) {
            var element = $('#' + specs.elementId);
            var message = specs.message;

            element.attr("onClick", 'return confirm("' + message + '");');
        }
    });
})(jQuery);
