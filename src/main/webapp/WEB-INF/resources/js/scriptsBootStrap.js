// scriptsBootStrap.js

// Popper.js
const script1 = document.createElement('script');
script1.src = 'https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js';
script1.integrity = 'sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r';
script1.crossOrigin = 'anonymous';
document.head.appendChild(script1);

// Bootstrap JS
const script2 = document.createElement('script');
script2.src = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js';
script2.integrity = 'sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+';
script2.crossOrigin = 'anonymous';
document.head.appendChild(script2);

// Alternatively, you can use only one script tag for Bootstrap 5.3.3 which includes Popper.js
const script3 = document.createElement('script');
script3.src = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js';
script3.integrity = 'sha384-2m7dTgB0zr7FOpnlzr43I2k1vF8h5U9pFT0f3qlv1M4kv+4iTbiTY18VJhGtp7tt';
script3.crossOrigin = 'anonymous';
document.head.appendChild(script3);
