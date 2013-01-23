(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        /**
         * Links an element to a zone. The zone is updated on the given client event.
         * @param specs
         */
        blockUI : function(specs) {
            var form = $('#' + specs.formId);
            var message = '<div class="t-loading">Chargement en cours...</div>';
            if (specs.message) {
                message = '<div class="t-loading">' + specs.message + '</div>';
            }

            if (specs.divblocked) {
                form.bind(Tapestry.FORM_PROCESS_SUBMIT_EVENT, function(event) {
                        $(event).log("Submit event: ")
                        $("#" + specs.divblocked).parent(".ui-dialog").block({
                            message: message,
                            css: {
                                border: 'none',
                                padding: '15px',
                                backgroundColor: '#fff',
                                '-webkit-border-radius': '10px',
                                '-moz-border-radius': '10px',
                                color: '#000'
                            }
                        })
                    return true;

                });
                if (specs.zoneId){
                    $("#" + specs.zoneId).bind(Tapestry.ZONE_UPDATED_EVENT, function(event){
                        $("#" + specs.divblocked).parent(".ui-dialog").unblock();
                        if (specs.growlTitle){
                            if (specs.growlMessage){
                                $.growlUI(specs.growlTitle, '');
                            } else {
                                $.growlUI(specs.growlTitle, specs.growlMessage);
                            }
                        }
                    })
                }

            } else {
                form.bind(Tapestry.FORM_PROCESS_SUBMIT_EVENT, function(event) {
                        $(event).log("Submit event: ")
                        $.blockUI({
                            message: message,
                            css: {
                                border: 'none',
                                padding: '15px',
                                backgroundColor: '#fff',
                                '-webkit-border-radius': '10px',
                                '-moz-border-radius': '10px',
                                color: '#000'
                            }
                        })
                    return true;

                });
                if (specs.zoneId){
                    $("#" + specs.zoneId).bind(Tapestry.ZONE_UPDATED_EVENT, function(event){
                        $.unblockUI();
                    })
                }
            }



        },

        unblockUI : function(specs) {
            $.unblockUI();
        }
    });
})(jQuery);
