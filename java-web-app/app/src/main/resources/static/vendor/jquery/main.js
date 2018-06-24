(function($) {
	"use strict";

	/*
	 * ================================================================== [
	 * Focus Contact2 ]
	 */
	$('.input100').each(function() {
		$(this).on('blur', function() {
			if ($(this).val().trim() != "") {
				$(this).addClass('has-val');
			} else {
				$(this).removeClass('has-val');
			}
		})
	})

	$('.input100Reg').each(function() {
		$(this).on('blur', function() {
			if ($(this).val().trim() != "") {
				$(this).addClass('has-val');
			} else {
				$(this).removeClass('has-val');
			}
		})
	})
	
	$('.input100Del').each(function() {
		$(this).on('blur', function() {
			if ($(this).val().trim() != "") {
				$(this).addClass('has-val');
			} else {
				$(this).removeClass('has-val');
			}
		})
	})
	/*
	 * ================================================================== [
	 * Validate ]
	 */
	var input = $('.validate-input .input100');

	$('.validate-form').on('submit', function() {
		var check = true;

		for (var i = 0; i < input.length; i++) {
			if (validate(input[i]) == false) {
				showValidate(input[i]);
				check = false;
			}
		}

		return check;
	});

	$('.validate-form .input100').each(function() {
		$(this).focus(function() {
			hideValidate(this);
		});
	});

	function validate(input) {
		if ($(input).attr('type') == 'email'
				|| $(input).attr('name') == 'email') {
			if ($(input)
					.val()
					.trim()
					.match(
							/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
				return false;
			}
		} else {
			if ($(input).val().trim() == '') {
				return false;
			}
		}
	}

	function showValidate(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).addClass('alert-validate');
	}

	function hideValidate(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).removeClass('alert-validate');
	}

	// ------------------

	var input1 = $('.validate-inputReg .input100Reg');

	$('.validate-formReg').on('submit', function() {
		var check = true;

		for (var i = 0; i < input1.length; i++) {
			if (validate1(input1[i]) == false) {
				showValidate1(input1[i]);
				check = false;
			}
		}

		return check;
	});

	$('.validate-formReg .input100Reg').each(function() {
		$(this).focus(function() {
			hideValidate1(this);
		});
	});

	function validate1(input) {
		if ($(input).attr('type') == 'emailReg'
				|| $(input).attr('name') == 'emailReg') {
			if ($(input)
					.val()
					.trim()
					.match(
							/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
				return false;
			}
		} else {
			if ($(input).val().trim() == '') {
				return false;
			}
		}
	}
	
	function showValidate1(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).addClass('alert-validate');
	}

	function hideValidate1(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).removeClass('alert-validate');
	}
	
	
//	-----
	var input2 = $('.validate-inputDel .input100Del');

	$('.validate-formDel').on('submit', function() {
		var check = true;

		for (var i = 0; i < input2.length; i++) {
			if (validate2(input2[i]) == false) {
				showValidate2(input2[i]);
				check = false;
			}
		}

		return check;
	});

	$('.validate-formDel .input100Del').each(function() {
		$(this).focus(function() {
			hideValidate2(this);
		});
	});

	function validate2(input) {
		if ($(input).attr('type') == 'emailReg'
				|| $(input).attr('name') == 'emailReg') {
			if ($(input)
					.val()
					.trim()
					.match(
							/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
				return false;
			}
		} else {
			if ($(input).val().trim() == '') {
				return false;
			}
		}
	}
	
	function showValidate2(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).addClass('alert-validate');
	}

	function hideValidate2(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).removeClass('alert-validate');
	}

})(jQuery);