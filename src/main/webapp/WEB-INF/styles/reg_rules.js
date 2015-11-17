$(document).ready(function() {

	$("#registration").validate({

		rules : {

			email : {
				required : true,
				email : true,
				maxlength : 64,
			},

			name : {
				required : true,
				minlength : 4,
				maxlength : 16,
			},

			password : {
				required : true,
				minlength : 6,
				maxlength : 16,
			},
			confirmpass : {
				required : true,
				minlength : 6,
				maxlength : 16,
				equalTo : "#password",
			},
		},

		messages : {

			name : {
				required : "Please enter your name",
				minlength : "The name must have at least 4 symbol",
				maxlength : "Max is 16 symbols",
			},

			password : {
				required : "Please enter password",
				minlength : "Password must have minimum 6 character",
				maxlength : "password must have maximally 16 characters",
			},

			email : {
				required : "Please enter email",
				email : "Incorrect email",
				maxlength : "Incorrect email",
			},
			confirmpass : {
					equalTo : "Password is not confirm",
			},
		}

	});
});
