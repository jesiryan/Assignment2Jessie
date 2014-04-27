/* Password match */
$(document).ready(function() {
	$("#password").keyup(validate);
	$("#passwordRetype").keyup(validate);
});
function validate() {
	var password = $("#password").val();
	var passwordRetype = $("#passwordRetype").val();
	
	if(password == "" && passwordRetype == ""){
		$("#validate-status").text(""); 
	}
	else if(password == passwordRetype) {
		$("#validate-status").text("passwords match :)"); 
		document.getElementById("validate-status").style.color= "#14993E";
	}
	else {
		$("#validate-status").text("passwords don't match");  
		document.getElementById("validate-status").style.color= "#CC0000";
	}
}

/* MEMBER STUFF */

/* Get the member template */
function getMemberTemplate() {
	$.ajax({
		url: "tmpl/member.tmpl",
		dataType: "html",
		success: function( data ) {
			$( "head" ).append( data );
			updateMemberTable();
		}
	});
}

/* Builds the updated table for the member list */
function buildMemberRows(members) {
	return _.template( $( "#member-tmpl" ).html(), {"members": members});
}

/* Uses JAX-RS GET to retrieve current member list */
function updateMemberTable() {
	$.ajax({
		url: "rest/members",
		cache: false,
		success: function(data) {
			$('#members').empty().append(buildMemberRows(data));
		},
		error: function(error) {
			alert("error updating table -" + error.status);
		}
	});
}

function registerMember(memberData) {
	$.ajax({
		url: 'rest/members',
		contentType: "application/json",
		dataType: "json",
		type: "POST",
		data: JSON.stringify(memberData),
		success: function(data) {
			//mark success on the registration form
			$("#validate-status").text("user registered");  
			document.getElementById("validate-status").style.color= "#000000";
		},
		error: function(error) {
			if ((error.status == 409) || (error.status == 400)) {
				alert("409 or 400 error");
			} else {
				alert("Unknown server error");
			}
		}
	});
}
	



/* TO DO STUFF */

/* Get the todo template */
function getTodoTemplate() {
	$.ajax({
		url: "tmpl/todo.tmpl",
		dataType: "html",
		success: function( data ) {
			$( "head" ).append( data );
			updateTodoTable();
		}
	});
}

/* Builds the updated table for the todo list */
function buildTodoRows(todos) {
	return _.template( $( "#todo-tmpl" ).html(), {"todos": todos});
}

/* Uses JAX-RS GET to retrieve current todo list */
function updateTodoTable() {
	$.ajax({
		url: "rest/todos",
		cache: false,
		success: function(data) {
			$('#todos').empty().append(buildTodoRows(data));
		},
		error: function(error) {
			//console.log("error updating table -" + error.status);
		}
	});
}

function registerTodo(todoData) {
	//clear existing  msgs
	$('span.invalid').remove();
	$('span.success').remove();

	$.ajax({
		url: 'rest/todos',
		contentType: "application/json",
		dataType: "json",
		type: "POST",
		data: JSON.stringify(todoData),
		success: function(data) {
			//clear input fields
			$('#reg')[0].reset();

			//mark success on the registration form
			$('#formMsgs').append($('<span class="success">Todo Registered</span>'));
		},
		error: function(error) {
			if ((error.status == 409) || (error.status == 400)) {

				var errorMsg = $.parseJSON(error.responseText);

				$.each(errorMsg, function(index, val) {
					$('#formMsgs').append($('<span class="invalid">' + val + '</span>').insertAfter($('#' + index)));
				});
			} else {
				$('#formMsgs').append($('<span class="invalid">Unknown server error</span>'));
			}
		}
	});
}
