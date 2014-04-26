//****************************LOGIN*******************************//

	// Length of time (in minutes before logout)
	var maxTime = 15;
	
	function pageRedirect(name, password) {
		alert("got in pageRedirect");
        $.ajax({
            url: "rest/members/login?name="+name+"&password="+password+"&submit=Submit",
            type: "GET",
            cache: false,
            data:{name: name,password:password },
            success: function(data) {
            	alert("success");
            	if (data.length > 0) {
            		$('#info').empty();
            		alert(data[1]);
            		addCurrentUserToLocalStorage(data);
            	} else {
            		$('#userPassWord').val('');
            		$('#info').removeClass("hidden");
            		$('#info').empty().append("<br/>Login for Username: '"+name+"' failed. Please try again.");
                }
            },
            error: function(error) {
            	alert('login error');
            }
        });
    }
	
//    function pageRedirect(name, password) {
//    	alert("got into pageRedirect");
//        $.ajax({
//            url: "rest/members/login?name="+name+"&password="+password,
//            type: "GET",
//            cache: false,
//            data:{name:name, password:password },
//            success: function(data) {
//            	alert("success!");
//            	if (data.length > 0) {
//            		$('#info').empty();
//            		alert("got to pageRedirect in login.js");
//            		document.location.href = '/Assignment2Jessie/Home.html';
//            		addCurrentUserToLocalStorage(data);
//            	} else {
//            		$('#userPassWord').val('');
//            		$('#info').removeClass("hidden");
//            		$('#info').empty().append("<br/>Login for Username: '"+name+"' failed. Please try again.");
//                }
//            },
//            error: function(error) {
//            	alert('login error');
//            }
//        });
//    }
    
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