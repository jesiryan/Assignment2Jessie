/* Password match */
$(document).ready(function() {
	$("#password").keyup(validate);
	$("#password").keyup(clearInfo);
	$("#passwordRetype").keyup(validate);
	$("#passwordRetype").keyup(clearInfo);
	$("#name").keyup(clearInfo);
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
function clearInfo(){
	$("#info").text("");
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
			$("#validate-status").text("");
			$("#info").text("member registered");
			document.getElementById("info").style.color= "#000000";
			return true;
		},
		error: function(error) {
			if(error.status == 409){
				$("#info").text("Name Taken");
			} else if (error.status == 400) {
				$("#info").text("Bad Request");
			} else {
				$("#info").text("unknown server error");
			}
			return false;
		}
	});
}
	
function getMember(memberName) {
	//alert("getMember(" + memberName + ");");
	$.ajax({
        url: "rest/members/getMember",
        type: "GET",
        cache: false,
        data:{name: memberName},
        success: function(data) {
        	if (data.length > 0) {
        		//alert("has data");
        		var member = JSON.stringify(eval("(" + data + ")"));
        		//alert(eval("(" + member + ")"));
        		return member;
        	} else {
        		//alert("null");
            }
        },
        error: function(error) {
        	alert('error');
        }
    });
}






function getMemberAndRegisterTodo(memberName, todoName, todoDesc) {
	$.ajax({
        url: "rest/members/getMemberRegisterTodo",
        type: "GET",
        cache: false,
        data:{name: memberName, todoName: todoName, todoDesc: todoDesc},
        success: function(data) {
//        	if (data.length > 0) {
        	if( data != null ){
//        		alert("has data");
        		$("#info").text("todo registered");
        		return true;
        	} else {
        		alert("null");
        		$("#info").text("todo failed");
            }
        },
        error: function(error) {
        	alert('error');
        	$("#info").text("error");
        }
    });
}

function getMembersTodos(memberName, todoName, todoDesc) {
	$.ajax({
        url: "rest/members/getMembersTodos",
        type: "GET",
        cache: false,
        data:{name: memberName, todoName: todoName, todoDesc: todoDesc},
        success: function(data) {
//        	if (data.length > 0) {
        	if( data != null ){
//        		alert("has data");
        		$("#info").text("found todos");
        		return true;
        	} else {
        		alert("null");
        		$("#info").text("todos not found");
            }
        },
        error: function(error) {
        	alert('error');
        	$("#info").text("error");
        }
    });
}

function getTodosGivenMemberName(memberName) {
	$.ajax({
        url: "rest/members/getTodosGivenName",
        type: "GET",
        cache: false,
        data:{name: memberName},
        success: function(data) {
//        	if (data.length > 0) {
        	if( data != null ){
        		alert("has data");
        		$("#info").text("got todos");
        		return true;
        	} else {
        		alert("null");
        		$("#info").text("todo retrieval failed");
            }
        },
        error: function(error) {
        	alert('error');
        	$("#info").text("error");
        }
    });
}



/* TO DO STUFF */

/* Get the todo template */
function getTodoTemplate(memberName) {
	$.ajax({
		url: "tmpl/todo.tmpl",
		dataType: "html",
		success: function( data ) {
			$( "head" ).append( data );
			updateTodoTable(memberName);
		}
	});
}

/* Builds the updated table for the todo list */
function buildTodoRows(todos) {
	return _.template( $( "#todo-tmpl" ).html(), {"todos": todos});
}

/* Uses JAX-RS GET to retrieve current todo list */
function updateTodoTable(memberName) {
	$.ajax({
		url: "rest/members/getTodosGivenName",
		cache: false,
		data:{name: memberName},
		success: function(data) {
			$('#todos').empty().append(buildTodoRows(data));
		},
		error: function(error) {
			alert("error updating table -" + error.status);
		}
	});
}

///* Uses JAX-RS GET to retrieve current todo list */
//function updateTodoTable() {
//	$.ajax({
//		url: "rest/todos",
//		cache: false,
//		success: function(data) {
//			$('#todos').empty().append(buildTodoRows(data));
//		},
//		error: function(error) {
//			alert("error updating table -" + error.status);
//		}
//	});
//}

function registerTodo(todoData) {
	$.ajax({
		url: 'rest/todos',
		contentType: "application/json",
		dataType: "json",
		type: "POST",
		data: JSON.stringify(todoData),
		success: function(data) {
			//mark success on the registration form
			$("#validate-status").text("");
			$("#info").text("todo registered");
			document.getElementById("info").style.color= "#000000";
			return true;
		},
		error: function(error) {
			if(error.status == 409){
				$("#info").text("Todo Taken");
			} else if (error.status == 400) {
				$("#info").text("Bad Request");
			} else {
				$("#info").text("unknown server error");
			}
			return false;
		}
	});
}
