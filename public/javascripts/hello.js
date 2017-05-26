var rows = document.getElementsByTagName("tr");
for (var i=0; i<rows.length; i++) {
	rows[i].addEventListener("mouseover", function() {
		this.classList.add("row");
	});
	
	rows[i].addEventListener("mouseleave", function() {
		this.classList.remove("row");
	});
}

if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}


