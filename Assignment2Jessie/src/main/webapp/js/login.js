//****************************LOGIN*******************************//

	// Length of time (in minutes before logout)
	var maxTime = 15;
	
	function pageRedirect(name, password) {
//		alert("got in pageRedirect");
        $.ajax({
            url: "rest/members/login?name="+name+"&password="+password,
//        	url: "rest/members/login/"+name+"/"+password,
            type: "GET",
            cache: false,
            data:{name: name,password:password },
            success: function(data) {
//            	alert("success");
            	if (data.length > 0) {
            		$('#info').empty();
            		alert(data[2]);
            		addCurrentUserToLocalStorage(data);
            	} else {
            		alert("Login for User: " + name + " failed. Please try again.");
                }
            },
            error: function(error) {
            	alert('login error');
            }
        });
    }

    
    function addCurrentUserToLocalStorage(data) {
    	localStorage.setItem("currentUserName", data[0]);
    }
    
	function logout() {
		clearUser();
    	window.location.replace("index.html");
	}

    function clearUser() {
    	localStorage.clear();
    }