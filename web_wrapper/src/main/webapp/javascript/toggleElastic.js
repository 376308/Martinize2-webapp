let checkbox = document.getElementById('flexSwitchCheckDefault');
let label = document.getElementById('elasticbutton');
let extraOptions = document.getElementById("hiddenElastic");

checkbox.addEventListener('change', function(event) {
    label.textContent = checkbox.checked ? 'True' : 'False';
    extraOptions.style.display = checkbox.checked ? "block" : "none";

});