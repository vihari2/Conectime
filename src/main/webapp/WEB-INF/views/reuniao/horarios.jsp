
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Horários | Conectime</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/horarios.css">
</head>

<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/reunioes"
       class="brand">Conectime</a>

    <a href="${pageContext.request.contextPath}/reunioes"
       class="back-link">Minhas reuniões</a>
</header>

<main class="container">

    <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}"
       class="back-button">
        ← Voltar aos detalhes
    </a>

    <section class="page-heading">
        <span class="eyebrow">GERENCIAMENTO DE HORÁRIOS</span>

        <h1>Opções de horários</h1>

        <p>
            Reunião:
            <strong><c:out value="${reuniao.titulo}"/></strong>
        </p>
    </section>

    <c:if test="${not empty sucesso}">
        <div class="message success">
            <c:out value="${sucesso}"/>
        </div>
    </c:if>

    <c:if test="${not empty erro}">
        <div class="message error">
            <c:out value="${erro}"/>
        </div>
    </c:if>

    <!-- Formulário de cadastro -->
    <section class="card">

        <div class="section-heading">
            <h2>Adicionar horário</h2>
            <p>Escolha uma data e um intervalo para a reunião.</p>
        </div>

        <form
            action="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/horarios"
            method="POST"
            id="formHorario">

            <div class="form-row">

                <div class="field">
                    <label for="dataHoraInicio">Data e horário de início</label>

                    <input
                        type="datetime-local"
                        id="dataHoraInicio"
                        name="dataHoraInicio"
                        required
                    >
                </div>

                <div class="field">
                    <label for="dataHoraFim">Data e horário de término</label>

                    <input
                        type="datetime-local"
                        id="dataHoraFim"
                        name="dataHoraFim"
                        required
                    >
                </div>

            </div>

            <button type="submit" class="primary-button">
                + Adicionar opção
            </button>

        </form>

    </section>

    <!-- Lista de horários -->
    <section class="card">

        <div class="section-heading">
            <h2>Horários cadastrados</h2>

            <span class="count">
                <c:out value="${horarios.size()}"/> opções
            </span>
        </div>

        <c:choose>

            <c:when test="${empty horarios}">

                <div class="empty-state">
                    <div class="empty-icon">📅</div>

                    <h3>Nenhum horário cadastrado</h3>

                    <p>
                        Adicione opções de horários para que os convidados
                        possam informar sua disponibilidade.
                    </p>
                </div>

            </c:when>

            <c:otherwise>

                <div class="schedule-list">

                    <c:forEach items="${horarios}" var="horario">

                        <article class="schedule-item">

                            <div>
                                <span class="schedule-label">Início</span>
                                <strong>
                                    <c:out value="${horario.dataHoraInicio}"/>
                                </strong>
                            </div>

                            <div>
                                <span class="schedule-label">Término</span>
                                <strong>
                                    <c:out value="${horario.dataHoraFim}"/>
                                </strong>
                            </div>

                        </article>

                    </c:forEach>

                </div>

            </c:otherwise>

        </c:choose>

    </section>

</main>

<script src="${pageContext.request.contextPath}/js/horarios.js"></script>

</body>
</html>
