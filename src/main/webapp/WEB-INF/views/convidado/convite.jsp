
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Responder convite | Conectime</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/convite.css">
</head>

<body>

<header class="topbar">
    <a href="#" class="brand">Conectime</a>
    <span>Convite para reunião</span>
</header>

<main class="container">

    <section class="heading">
        <span class="eyebrow">VOCÊ FOI CONVIDADO!</span>

        <h1>
            Olá,
            <c:choose>
                <c:when test="${not empty convidado.nome}">
                    <c:out value="${convidado.nome}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${convidado.email}"/>
                </c:otherwise>
            </c:choose>
        </h1>

        <p>
            Confira os detalhes da reunião e informe os horários
            em que você poderá participar.
        </p>
    </section>

    <!-- Informações da reunião -->
    <section class="card meeting-card">

        <span class="section-label">DETALHES DA REUNIÃO</span>

        <h2><c:out value="${reuniao.titulo}"/></h2>

        <c:if test="${not empty reuniao.descricao}">
            <p class="description">
                <c:out value="${reuniao.descricao}"/>
            </p>
        </c:if>

        <div class="meeting-info">

            <div>
                <span>Formato</span>
                <strong>
                    <c:out value="${reuniao.formato}"/>
                </strong>
            </div>

            <c:if test="${not empty reuniao.local}">
                <div>
                    <span>Local</span>
                    <strong>
                        <c:out value="${reuniao.local}"/>
                    </strong>
                </div>
            </c:if>

            <c:if test="${not empty reuniao.link}">
                <div>
                    <span>Link informado pelo organizador</span>
                    <strong>
                        <c:out value="${reuniao.link}"/>
                    </strong>
                </div>
            </c:if>

        </div>

    </section>

    <!-- Seleção de horários -->
    <section class="card">

        <div class="section-heading">
            <span class="step">01</span>

            <div>
                <h2>Quando você está disponível?</h2>
                <p>Selecione todos os horários em que poderá participar.</p>
            </div>
        </div>

        <c:if test="${not empty sucesso}">
            <div class="response-message success" role="status">
                <c:out value="${sucesso}" />
            </div>
        </c:if>

        <c:if test="${not empty erro}">
            <div class="response-message error" role="alert">
                <c:out value="${erro}" />
            </div>
        </c:if>

        <c:choose>

            <c:when test="${empty horarios}">

                <div class="empty-state">
                    <h3>Nenhum horário disponível ainda</h3>

                    <p>
                        O organizador ainda não cadastrou opções de horários.
                        Volte mais tarde para responder.
                    </p>
                </div>

            </c:when>

            <c:otherwise>

                <form
                    action="${pageContext.request.contextPath}/convites/${convidado.token}/responder"
                    method="POST">

                    <div class="availability-list">

                        <c:forEach items="${horarios}" var="horario">

                            <label class="time-option">

                                <input
                                    type="checkbox"
                                    name="idsHorarios"
                                    value="${horario.idHorario}"
                                >

                                <span class="checkbox-custom"></span>

                                <span class="time-info">

                                    <strong>
                                        <c:out value="${horario.dataHoraInicio}"/>
                                    </strong>

                                    <span>
                                        Até
                                        <c:out value="${horario.dataHoraFim}"/>
                                    </span>

                                </span>

                            </label>

                        </c:forEach>

                    </div>

                    <p class="helper-text">
                        Você pode selecionar mais de uma opção.
                        Caso não possa participar em nenhum horário,
                        envie a resposta sem selecionar opções.
                    </p>

                    <button type="submit" class="submit-button">
                        Enviar disponibilidade
                    </button>

                </form>

            </c:otherwise>

        </c:choose>

    </section>

    <footer>
        <p>Conectime — Encontrando o melhor horário para todos.</p>
    </footer>

</main>

</body>
</html>
