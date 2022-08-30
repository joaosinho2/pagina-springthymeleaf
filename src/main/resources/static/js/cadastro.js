// Example starter JavaScript for disabling form submissions if there are invalid fields,
$(document).ready(function () {
//$("#submit").on("click",function(){
//    console.log("botao clicado");
//    alert('aaa');
//});
  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  const forms = document.querySelectorAll('.needs-validation');

  // Loop over them and prevent submission
  Array.from(forms).forEach(form => {
    form.addEventListener('submit', event => {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }

      form.classList.add('was-validated');
    }, false);
  });
  $("#campoCpf").mask("999.999.999-99");
});