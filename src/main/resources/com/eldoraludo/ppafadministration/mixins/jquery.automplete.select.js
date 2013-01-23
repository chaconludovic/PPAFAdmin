(function( $ ) {
		$.widget( "ui.combobox", {
            options: {
                elementId: '',
                styleCss: '',
                useCssValue: '',
                deletechoice: '',
                itemNew: '',
                placeholder: ''
            },
			_create: function() {
				var input,
					self = this,
                    //useCssValue = specs.useCssValue;
					select = this.element.addClass('comboboxdone'),
					select = this.element.hide(),
					previousValue = "",
					previousId = this.element.attr('id'),
					styleCss=select.attr("class"),
					useCssValue = select.attr("useCssValue") ? true : false,
					deletechoice = select.attr("deletechoice") ? true : false,
					newItem = select.attr("newItem") ? true : false,
					selected = select.children( ":selected" ),
					value = selected.val() ? selected.text() : "",
					wrapper = this.wrapper = $( "<span>" )
						.addClass( "ui-combobox" )
						.attr("id", "ui-combobox-" + previousId )
						.insertAfter( select );
                    if (value != "" && useCssValue) {
                        previousValue = styleCss + value;
                    };
                    if (value != "" && deletechoice) {
                            wrapper.find('a.clearCombo').show();
                    } else {
                        wrapper.find('a.clearCombo').hide();
                    };

				input = $( "<input>" )
					.appendTo( wrapper )
					.val( value )
					.addClass( "ui-state-default ui-combobox-input " + previousValue )
                    .attr('onfocus', 'this.value = this.value;')
					.autocomplete({
						delay: 0,
						minLength: 0,
						source: function( request, response ) {
							var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
							response( select.children( "option" ).map(function() {
								var text = $( this ).text();
								if ( this.value && ( !request.term || matcher.test(text) ) )
									return {
										label: text.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ),
										value: text,
										option: this
									};
							}) );
						},
						select: function( event, ui ) {
							ui.item.option.selected = true;
							self._trigger( "selected", event, {
								item: ui.item.option
							});
                            select.trigger('change');
                            if (deletechoice && select.children().length > 1) {
                                wrapper.find('a.clearCombo').show();
                            }
                            if (useCssValue) {
                                $(this).removeClass(previousValue);
                                var valueCssi = '' + ui.item.value;
                                $(this).addClass( styleCss + valueCssi.toUpperCase());
                                previousValue = styleCss + valueCssi.toUpperCase();
                            }
                            var formToUpdate = $(this).closest('form');
                            setTimeout(function(){ $(input, formToUpdate).trigger('change', [true]); }, 1);
                            $(this).focus();
						},
						change: function( event, ui ) {
							if ( !ui.item ) {
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
									valid = false;
								select.children( "option" ).each(function() {
									if ( $( this ).text().match( matcher ) ) {
                                        if (deletechoice && select.children().length > 1 && $( this ).text() !="" ) {
                                            wrapper.find('a.clearCombo').show();
                                        }
										this.selected = valid = true;
                                        select.val( $(this).val() );
                                        input.val($( this ).text());
                                        $( this ).attr("selected", true);
                                        if (useCssValue) {
                                            input.removeClass(previousValue);
                                            var valueCss = '' + $(this).val();
                                            if (valueCss !='') {
                                                input.addClass(styleCss + valueCss.toUpperCase());
                                            }
                                            previousValue = styleCss + valueCss.toUpperCase();
                                        }
                                        select.trigger("change");
										return false;
									}
								});

								if ( !valid ) {
									// remove invalid value, as it didn't match anything
                                    if (select.children().length == 1) {
                                       $( this ).val( select.find('option:selected').text() );
                                       input.trigger("change");
                                    } else {
                                        $( this ).val( "" );
                                        select.find('option:selected').attr("selected", false);
                                        select.val( "" );
                                        input.data( "autocomplete" ).term = "";
                                        if (useCssValue) {
                                            $(this).removeClass(previousValue);
                                        }
                                        wrapper.find('a.clearCombo').hide();

                                        select.trigger("change");
                                    }
									return false;
								}
							}
						}
					})
					.addClass( "ui-widget ui-widget-content ui-corner-left" );

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
                    input.alterClass("autocomplete_*","");
                    if (newItem) {
                        //ul.append('<li><a class="new-item-select">New ' + previousId +'</a></li>');
                        $( "<a class='new-item-select'>" ).click(function(){
                          $(this).trigger("autocomplete.newItem");
                        }).text("New").appendTo('<li></li>').appendTo( ul );
                        newItem = false;
                    }
                    if (useCssValue) {
					    return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a><span class='" + styleCss + item.value + "'>" + item.label + "</span></a>" )
						.appendTo( ul );
                    } else {
					    return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a><span>" + item.label + "</span></a>" )
						.appendTo( ul );
                    }

				};

				$( "<a>" )
					.attr( "tabIndex", -1 )
					.appendTo( wrapper )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "ui-corner-right ui-combobox-toggle" )
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						// work around a bug (likely same cause as #5265)
						$( this ).blur();

						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
						input.focus();
					});
                $( "<a class='clearCombo'>" )
					.attr( "tabIndex", -1 )
					.appendTo( wrapper )
					.click(function() {
                        wrapper.find('.ui-autocomplete-input')
                            .focus()
                            .val('')
                            .autocomplete('close');
                        select.find('option:selected').prop("selected", false);
                        select.val( "" );
                        input.data( "autocomplete" ).term = "";
                        if (useCssValue) {
                            wrapper.find('.ui-autocomplete-input').removeClass(previousValue);
                        }
                        $(this).hide();
                        select.trigger("change");
                        return false;
					})
                if (value != "" && deletechoice && select.children().length > 1) {
                        wrapper.find('a.clearCombo').show();
                } else {
                    wrapper.find('a.clearCombo').hide();
                };

			},

			destroy: function() {
				this.wrapper.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			}
		});

    $.fn.alterClass = function ( removals, additions ) {

var self = this;

if ( removals.indexOf( '*' ) === -1 ) {
// Use native jQuery methods if there is no wildcard matching
self.removeClass( removals );
return !additions ? self : self.addClass( additions );
}

var patt = new RegExp( '\\s' +
removals.
replace( /\*/g, '[A-Za-z0-9-_]+' ).
split( ' ' ).
join( '\\s|\\s' ) +
'\\s', 'g' );

self.each( function ( i, it ) {
var cn = ' ' + it.className + ' ';
while ( patt.test( cn ) ) {
cn = cn.replace( patt, ' ' );
}
it.className = $.trim( cn );
});

return !additions ? self : self.addClass( additions );
};

	})( jQuery );


