/**
 * Created by sarazhu on 15/5/15.
 */
(function ($) {
    $.fn.spinner = function (opts) {
        return this.each(function () {
            var defaults = {value: 0, min: 0}
            var options = $.extend(defaults, opts)
            var keyCodes = {up: 38, down: 40}
            var container = $('<div style="font-size: medium"></div>')
            container.addClass('spinner')
            var textField = $(this).addClass('value').attr('maxlength', '2').val(options.value)
                .bind('keyup paste change', function (e) {
                    var field = $(this)
                    if (e.keyCode == keyCodes.up) changeValue(1)
                    else if (e.keyCode == keyCodes.down) changeValue(-1)
                    else if (getValue(field) != container.data('lastValidValue')) validateAndTrigger(field)
                })
            textField.wrap(container)

            var increaseButton = $('<button class="increase"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>').click(function () {
                changeValue(1);
                changeTotalCost($(this));
            })
            var decreaseButton = $('<button class="decrease"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></button>').click(function () {
                changeValue(-1);
                changeTotalCost($(this));
            })

            validate(textField)
            container.data('lastValidValue', options.value)
            textField.before(decreaseButton)
            textField.after(increaseButton)

            function changeValue(delta) {
                textField.val(getValue() + delta)
                textField.parent().parent().find('span.item_bought').text(getValue());

                validateAndTrigger(textField)
            }

            function validateAndTrigger(field) {
                clearTimeout(container.data('timeout'))
                var value = validate(field)
                if (!isInvalid(value)) {
                    textField.trigger('update', [field, value])
                    setTimeout(formatNumber, 10);
                }
            }

            function formatNumber() {
                textField.parent().parent().parent().find('div.price>span').text((parseFloat(textField.parent().parent().parent().find('div.price>span').text()).toFixed(2)));
                //console.log(textField.parent().parent().parent().find('div.price>span').text());
            }

            function validate(field) {
                var value = getValue()
                if (value <= options.min)
                    decreaseButton.attr('disabled', 'disabled');
                else
                    decreaseButton.removeAttr('disabled');
                if (value >= options.max)
                    increaseButton.attr('disabled', 'disabled');
                else
                    increaseButton.removeAttr('disabled');
                field.toggleClass('invalid', isInvalid(value)).toggleClass('passive', value === 0)

                if (isInvalid(value)) {
                    var timeout = setTimeout(function () {
                        textField.val(container.data('lastValidValue'))
                        validate(field)
                    }, 500)
                    container.data('timeout', timeout)
                } else {
                    container.data('lastValidValue', value)
                }
                return value
            }

            function isInvalid(value) {
                return isNaN(+value) || value < options.min;
            }

            function getValue(field) {
                field = field || textField;
                return parseInt(field.val() || 0, 10)
            }
        })
    }
})(jQuery)