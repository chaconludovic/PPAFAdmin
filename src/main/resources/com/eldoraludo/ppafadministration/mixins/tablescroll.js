(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureTableScroll: function(specs) {
            $('#' + specs.tableId).tableScroll();

         }
    });
})(jQuery);