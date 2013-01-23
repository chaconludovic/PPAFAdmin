(function ($) {

    $.extend(Tapestry.Initializer, {
        autocompleteSelect:function (specs) {
            if (specs.deletechoice) {
                $('#' + specs.elementId).attr('deletechoice', 'true');
            }
            if (specs.newItem) {$('#' + specs.elementId).attr('newItem', 'true');}
            $('#' + specs.elementId).combobox(specs);
            inputCourant = $('#' + specs.elementId).next(".ui-combobox").find("input");
            if (specs.placeholder) {
                inputCourant.watermark(specs.placeholder);
            }
            if (specs.focused) {
                inputCourant.focus();
            }
        },
        autocompleteSelectWithCss:function (specs) {
            $('#' + specs.elementId).attr('useCssValue', 'true');
            if (specs.deletechoice) {$('#' + specs.elementId).attr('deletechoice', 'true');}
            if (specs.newtem) {$('#' + specs.elementId).attr('newItem', 'true');}
            $('#' + specs.elementId).combobox(specs);
            if (specs.placeholder) {
                $('#' + specs.elementId).next(".ui-combobox").find("input").watermark(specs.placeholder);
            }
            if (specs.focused) {
                inputCourant.focus();
            }
        }
    });

})(jQuery);