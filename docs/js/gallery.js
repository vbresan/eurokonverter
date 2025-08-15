function offsetSlide(n) {
	showSlide(slideIndex += n);
}

function showSlide(n) {
	
	let i;
  	let slides = document.querySelectorAll(".screenshot-hr, .screenshot-bg");
  	
  	if (n > slides.length) {
		slideIndex = 1
	}
  	if (n < 1) {
		slideIndex = slides.length
	}
	
  	for (i = 0; i < slides.length; i++) {
		slides[i].style.display = (i != slideIndex - 1)? "none" : "block";
    }

  	document.getElementById("slideNumber").innerText = slideIndex + " / " + slides.length;
}