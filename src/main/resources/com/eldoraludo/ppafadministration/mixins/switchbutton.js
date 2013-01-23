(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureSwitchButton: function(specs) {
            $('#' + specs.clientId).switchbutton({
                    checkedLabel: specs.checkedItemLabel,
                    uncheckedLabel: specs.uncheckedItemLabel
                });
         }
    });
})(jQuery);