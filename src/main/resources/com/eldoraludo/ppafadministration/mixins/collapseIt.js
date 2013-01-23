(function ($) {

    $.extend(Tapestry.Initializer, {
        setupCollapseIt:function (specs) {
            new jQueryCollapse($('#' + specs.elementId), {
              open: function() {
                this.slideDown(150);
              },
              close: function() {
                this.slideUp(150);
              }
            });

        }
    });
})(jQuery);