
// Elementos do formulário
const listaConvidados = document.getElementById("listaConvidados");
const listaHorarios = document.getElementById("listaHorarios");

const adicionarConvidado = document.getElementById("adicionarConvidado");
const adicionarHorario = document.getElementById("adicionarHorario");

const formato = document.getElementById("formato");
const campoLocal = document.getElementById("campoLocal");
const campoLink = document.getElementById("campoLink");

const localInput = document.getElementById("local");
const linkInput = document.getElementById("link");

// Mostra ou esconde os campos conforme o formato
function atualizarFormato() {

    const valor = formato.value;

    const precisaLocal = valor === "presencial" || valor === "hibrido";
    const precisaLink = valor === "online" || valor === "hibrido";

    campoLocal.hidden = !precisaLocal;
    campoLink.hidden = !precisaLink;

    localInput.required = precisaLocal;
    linkInput.required = precisaLink;

    if (!precisaLocal) localInput.value = "";
    if (!precisaLink) linkInput.value = "";
}

formato.addEventListener("change", atualizarFormato);
atualizarFormato();

// Adiciona um convidado
adicionarConvidado.addEventListener("click", () => {

    const linha = document.createElement("div");
    linha.className = "invite-row";

    linha.innerHTML = `
        <input
            type="email"
            name="emails"
            placeholder="email@exemplo.com"
            required
        >

        <button type="button"
                class="remove-row"
                aria-label="Remover convidado">
            ×
        </button>
    `;

    listaConvidados.appendChild(linha);
    atualizarBotoes();
});

// Adiciona uma opção de horário
adicionarHorario.addEventListener("click", () => {

    const linha = document.createElement("div");
    linha.className = "time-row";

    linha.innerHTML = `
        <div class="field">
            <label>Início</label>
            <input type="datetime-local"
                   name="dataHoraInicio"
                   required>
        </div>

        <div class="field">
            <label>Término</label>
            <input type="datetime-local"
                   name="dataHoraFim"
                   required>
        </div>

        <button type="button"
                class="remove-row"
                aria-label="Remover horário">
            ×
        </button>
    `;

    listaHorarios.appendChild(linha);
    atualizarBotoes();
});

// Remove convidados e horários
document.addEventListener("click", (event) => {

    const botao = event.target.closest(".remove-row");

    if (!botao) return;

    const linha = botao.closest(".invite-row, .time-row");

    if (!linha) return;

    const lista = linha.parentElement;

    // Mantém pelo menos um convidado ou horário
    if (lista.children.length > 1) {
        linha.remove();
        atualizarBotoes();
    }
});

// Desabilita a remoção quando só existe uma linha
function atualizarBotoes() {

    [listaConvidados, listaHorarios].forEach(lista => {

        const botoes = lista.querySelectorAll(".remove-row");

        botoes.forEach(botao => {
            botao.disabled = lista.children.length === 1;
        });
    });
}

atualizarBotoes();

// Validação básica de horários no navegador
document.getElementById("formReuniao").addEventListener("submit", event => {

    const linhas = listaHorarios.querySelectorAll(".time-row");

    for (const linha of linhas) {

        const inicio = linha.querySelector('[name="dataHoraInicio"]');
        const fim = linha.querySelector('[name="dataHoraFim"]');

        fim.setCustomValidity("");

        if (inicio.value && fim.value &&
            new Date(fim.value) <= new Date(inicio.value)) {

            fim.setCustomValidity(
                "O término deve ser posterior ao início."
            );

            fim.reportValidity();
            event.preventDefault();
            return;
        }
    }
});
