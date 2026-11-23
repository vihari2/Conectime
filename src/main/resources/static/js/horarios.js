
const formulario = document.getElementById("formHorario");

formulario.addEventListener("submit", (event) => {

    const inicio = document.getElementById("dataHoraInicio");
    const fim = document.getElementById("dataHoraFim");

    fim.setCustomValidity("");

    // O término precisa ser posterior ao início
    if (inicio.value && fim.value && fim.value <= inicio.value) {

        event.preventDefault();

        fim.setCustomValidity(
            "O término deve ser posterior ao início."
        );

        fim.reportValidity();
    }
});
