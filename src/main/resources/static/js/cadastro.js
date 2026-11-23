
const campoSenha = document.getElementById("senha");
const botaoMostrarSenha = document.getElementById("toggleSenha");

botaoMostrarSenha.addEventListener("click", () => {

    const senhaVisivel = campoSenha.type === "text";

    campoSenha.type = senhaVisivel ? "password" : "text";

    botaoMostrarSenha.textContent = senhaVisivel
        ? "Mostrar"
        : "Ocultar";
});
