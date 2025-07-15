function plusSlides(n) {
	showSlides(slideIndex += n);
}

function currentSlide(n) {
	showSlides(slideIndex = n);
}

function showSlides(n) {
	
	let i;
  	let slides = document.querySelectorAll(".screenshot, .screenshot-bg");
  	
  	if (n > slides.length) {
		slideIndex = 1
	}
  	if (n < 1) {
		slideIndex = slides.length
	}
	
  	for (i = 0; i < slides.length; i++) {
		slides[i].style.display = "none";
  	}
  	
  	slides[slideIndex - 1].style.display = "block";
  	document.getElementById("slideNumber").innerText = slideIndex + " / " + slides.length;
}