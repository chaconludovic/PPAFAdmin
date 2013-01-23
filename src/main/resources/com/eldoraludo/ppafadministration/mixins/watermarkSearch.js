(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureWatermarkSearch: function(specs) {
            $('#datatable_filter > input').watermark(specs.placeholder);

         }
    });
})(jQuery);