(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        /**
         * Links an element to a zone. The zone is updated on the given client event.
         * @param specs
         */
        statefulUI : function(specs) {
            var form = $('#' + specs.formId);
            if (specs.message) {
                message =  specs.message;
            }

            if (specs.beforeValidation) {
                form.bind(Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT, function(event) {
                    form.toggleClass('all-disabled');
                    return true;
                });
            } else {
                form.bind(Tapestry.FORM_PROCESS_SUBMIT_EVENT, function(event) {
                    form.toggleClass('all-disabled');
                    if (specs.dlgId) {
                        $('#' + specs.dlgId).dialog('close');
                    }
                    return true;
                });
            }




            if (specs.zoneId){
                $("#" + specs.zoneId).bind(Tapestry.ZONE_UPDATED_EVENT, function(event){
                    form.toggleClass('all-disabled');
                    return true;
                })
            }

        },
        unstatefulUI : function(specs) {
            $('.all-disabled').each(function(index) {
                $(this).toggleClass('all-disabled');
                return true;
            });
        }


    });
})(jQuery);
