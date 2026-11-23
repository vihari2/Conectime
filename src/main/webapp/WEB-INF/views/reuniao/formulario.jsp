
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Criar agendamento | Conectime</title>

    <link rel="stylesheet"
          href="<c:url value='/css/reuniao.css'/>">
</head>

<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/reunioes"
       class="brand">Conectime</a>

    <span class="user-label">Área do organizador</span>
</header>

<main class="page-container">

    <div class="page-heading">
        <span class="eyebrow">NOVO AGENDAMENTO</span>
        <h1>Vamos organizar uma reunião?</h1>
        <p>Defina os detalhes, convide participantes e proponha horários.</p>
    </div>

    <c:if test="${not empty erro}">
        <div class="message error">
            <c:out value="${erro}"/>
        </div>
    </c:if>

    <form id="formReuniao"
          action="${pageContext.request.contextPath}/reunioes"
          method="POST">

        <!-- Dados da reunião -->
        <section class="form-card">

            <div class="section-heading">
                <span class="step">01</span>
                <div>
                    <h2>Dados da reunião</h2>
                    <p>Informe os detalhes do seu agendamento.</p>
                </div>
            </div>

            <div class="field">
                <label for="titulo">Nome da reunião</label>
                <input
                    type="text"
                    id="titulo"
                    name="titulo"
                    placeholder="Ex.: Reunião de planejamento"
                    maxlength="150"
                    required
                >
            </div>

            <div class="field">
                <label for="descricao">Descrição</label>
                <textarea
                    id="descricao"
                    name="descricao"
                    placeholder="Descreva brevemente o objetivo da reunião..."
                    rows="3"
                ></textarea>
            </div>

            <div class="field">
                <label for="formato">Formato da reunião</label>

                <select id="formato" name="formato" required>
                    <option value="">Selecione o formato</option>
                    <option value="presencial">Presencial</option>
                    <option value="online">Online</option>
                    <option value="hibrido">Híbrido</option>
                </select>
            </div>

            <div class="field" id="campoLocal" hidden>
                <label for="local">Local da reunião</label>
                <input
                    type="text"
                    id="local"
                    name="local"
                    placeholder="Endereço ou local"
                    maxlength="150"
                >
            </div>

            <div class="field" id="campoLink" hidden>
                <label for="link">Link da reunião</label>
                <input
                    type="url"
                    id="link"
                    name="link"
                    placeholder="https://..."
                    maxlength="255"
                >
            </div>

        </section>

        <!-- Convidados -->
        <section class="form-card">

            <div class="section-heading">
                <span class="step">02</span>
                <div>
                    <h2>Convidados</h2>
                    <p>Adicione os e-mails das pessoas que participarão.</p>
                </div>
            </div>

            <div id="listaConvidados">

                <div class="invite-row">
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
                </div>

            </div>

            <button type="button"
                    id="adicionarConvidado"
                    class="add-button">
                + Adicionar convidado
            </button>

        </section>

        <!-- Opções de horários -->
        <section class="form-card">

            <div class="section-heading">
                <span class="step">03</span>
                <div>
                    <h2>Opções de horários</h2>
                    <p>Proponha datas e horários para os convidados.</p>
                </div>
            </div>

            <div id="listaHorarios">

                <div class="time-row">

                    <div class="field">
                        <label>Início</label>
                        <input
                            type="datetime-local"
                            name="dataHoraInicio"
                            required
                        >
                    </div>

                    <div class="field">
                        <label>Término</label>
                        <input
                            type="datetime-local"
                            name="dataHoraFim"
                            required
                        >
                    </div>

                    <button type="button"
                            class="remove-row"
                            aria-label="Remover horário">
                        ×
                    </button>

                </div>

            </div>

            <button type="button"
                    id="adicionarHorario"
                    class="add-button">
                + Adicionar horário
            </button>

        </section>

        <div class="form-actions">

            <a href="${pageContext.request.contextPath}/reunioes"
               class="cancel-button">
                Cancelar
            </a>

            <button type="submit" class="submit-button">
                Criar agendamento
            </button>

        </div>

    </form>

</main>

<script src="<c:url value='/js/reuniao.js'/>"></script>

</body>
</html>
