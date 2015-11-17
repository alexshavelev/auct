$(document).ready(function() {

	$("#newLot").validate({

		rules : {

			name : {
				required : true,
				minlength : 2,
				maxlength : 32,
			},

			description : {
				required : true,
				minlength : 6,
				maxlength : 1024,
			},
			
			startprice : {
				required : true,
				number : true,
			},
		},

		messages : {

			name : {
				required : "Please enter name of lot",
				minlength : "The name of lot must have at least 2 characters",
				maxlength : "Max is 32 characters",
			},

			description : {
				required : "Please enter description of lot",
				minlength : "Description must have minimum 6 characters",
				maxlength : "Description must have maximally 1024 characters",
			},

			startprice : {
				required : "Please enter a start price",
				number: "Please enter a start price",
			},
		}

	});
});