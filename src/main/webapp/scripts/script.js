/**
 * XMLHttpRequest allows you to request data from a server without reloading a page
 
 */
/*
function sendAjaxGet(url, func) {
	var xhr = new XMLHttpRequest()
			|| new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			func(this);
		}
	};
	xhr.open("GET", url, true);
	xhr.send();
};

function populateUser(xhr) {
	if (xhr.responseText) {
		var res = JSON.parse(xhr.responseText);
		console.log(res);
		if (res.username) {
			document.getElementById("user").innerText = "you are logged in as "
					+ res.username;
		}
	} else {
		window.location = "http://localhost:8080/ServletDemo/login";
	}
};

window.onload = function() {
	sendAjaxGet("http://localhost:8080/ServletDemo/session", populateUser);
}

*/
function sleep(milliseconds){
	const date = Date.now();
	let currentDate = null;
	do{
		currentDate = Date.now();
	}while(currentDate - date < milliseconds);
}

function sendLogin()
{
    const loader = document.querySelector('#loader');
	const displayer = document.querySelector('#displayer');
	const link = document.querySelector('#link');
    let uName = document.getElementById("username").value;
    let pWord = document.getElementById("password").value;
    let loginTemplate = {
        username: uName,
        password: pWord
    }
    
    let xhr = new XMLHttpRequest()
    xhr.onreadystatechange = function(){
    	console.log('ReadyState: ' + this.readyState);
    	if(this.readyState <= 3){
    		console.log('loading');
    		link.classList.add("hide");
    		loader.classList.remove("hide");
    		displayer.classList.remove("hide");
    		loader.classList.add("loading");
    		displayer.classList.add("show");
    	}
        if(this.readyState === 4 && this.status === 200)
        {
            console.log("Success")
            sleep(3000);
            loader.classList.remove("loading");
            displayer.classList.remove("show");
    		loader.classList.add("hide");
    		displayer.classList.add("hide");
    		sleep(1000);
            sessionStorage.setItem('currentUser', this.responseText)
            
            
            window.location = "http://localhost:8080/project-1/manager.html"
            console.log(sessionStorage.getItem('currentUser'))
        }
        if(this.readyState ===4 && this.status ===204)
        {
            console.log("Failed. Status Code: " + this.status)
			var reason = {
				code : this.status,
				issue : 'Wrong credentials. Failed to log in.'
			};
			console.log(reason);
			sessionStorage.setItem('failMessage', JSON.stringify(reason));
			console.log(sessionStorage.getItem('failMessage'));
			window.location = "http://localhost:8080/project-1/unauthorized.html"
        }
    }
    xhr.open("POST","http://localhost:8080/project-1/login")
    xhr.send(JSON.stringify(loginTemplate))
}