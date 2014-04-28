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
            		addCurrentUserToLocalStorage(data);
            		document.location.href = '/Assignment2Jessie/Home.html';
            	} else {
            		$("#info").text("Login for user: " + name + " failed. Please try again.");  
            		document.getElementById("validate-status").style.color= "#CC0000";
                }
            },
            error: function(error) {
            	alert('login error');
            	$("#info").text("Login failed. Please try again.");
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
    
    function loginLogout(){
    	var currentUserName = localStorage.getItem("currentUserName");
		if(currentUserName == null){
			$("#loginLogout").text("Login | ");
			//document.getElementById("loginLogout").setAttribute("value", "login | ");
			//document.getElementById("loginLogout").setAttribute("innerHTML", "login | ");
			logout();
			return;
		}
		else{
			$("#loginLogout").text("Logout | ");
			//document.getElementById("loginLogout").setAttribute("value", "logout | ");
			//document.getElementById("loginLogout").setAttribute("innerHTML", "logout | ");
			return;
		}
    }
    