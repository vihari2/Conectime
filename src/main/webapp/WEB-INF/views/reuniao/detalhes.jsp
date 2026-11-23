
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Detalhes da reunião | Conectime</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/detalhes.css">
</head>

<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/reunioes"
       class="brand">Conectime</a>

    <a href="${pageContext.request.contextPath}/reunioes"
       class="back-link">Voltar ao painel</a>
</header>

<main class="container">

    <a href="${pageContext.request.contextPath}/reunioes"
       class="back-button">← Minhas reuniões</a>

    <section class="meeting-header">

        <div>
            <span class="eyebrow">DETALHES DO AGENDAMENTO</span>

            <h1><c:out value="${reuniao.titulo}"/></h1>

            <span class="status status-${reuniao.status}">
                <c:out value="${reuniao.status}"/>
            </span>
        </div>

        <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/horarios"
           class="primary-button">
            Gerenciar horários
        </a>

    </section>

    <!-- Informações da reunião -->
    <section class="card">

        <h2>Informações gerais</h2>

        <div class="info-grid">

            <div class="info-item">
                <span>Descrição</span>
                <p>
                    <c:choose>
                        <c:when test="${not empty reuniao.descricao}">
                            <c:out value="${reuniao.descricao}"/>
                        </c:when>
                        <c:otherwise>Sem descrição.</c:otherwise>
                    </c:choose>
                </p>
            </div>

            <div class="info-item">
                <span>Formato</span>
                <p><c:out value="${reuniao.formato}"/></p>
            </div>

            <c:if test="${not empty reuniao.local}">
                <div class="info-item">
                    <span>Local</span>
                    <p><c:out value="${reuniao.local}"/></p>
                </div>
            </c:if>

            <c:if test="${not empty reuniao.link}">
                <div class="info-item">
                    <span>Link da reunião</span>
                    <p>
                        <a href="${reuniao.link}" target="_blank" rel="noopener noreferrer">
                            Acessar reunião online
                        </a>
                    </p>
                </div>
            </c:if>

            <div class="info-item">
                <span>Identificador</span>
                <p>#<c:out value="${reuniao.idReuniao}"/></p>
            </div>

        </div>

    </section>

    <!-- Horário escolhido -->
    <section class="card">

        <h2>Horário confirmado</h2>

        <c:choose>
            <c:when test="${not empty reuniao.horarioEscolhido}">
                <div class="selected-time">
                    <p>
                        Início:
                        <c:out value="${reuniao.horarioEscolhido.dataHoraInicio}"/>
                    </p>
                    <p>
                        Término:
                        <c:out value="${reuniao.horarioEscolhido.dataHoraFim}"/>
                    </p>
                </div>
            </c:when>

            <c:otherwise>
                <p class="empty-text">
                    Nenhum horário foi confirmado ainda.
                </p>
            </c:otherwise>
        </c:choose>

    </section>

    <!-- Opções de horário -->
    <section class="card">

        <div class="section-heading">
            <h2>Opções de horários</h2>
            <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/horarios">
                Gerenciar
            </a>
        </div>

        <c:choose>

            <c:when test="${empty reuniao.opcoesHorarios}">
                <p class="empty-text">
                    Nenhuma opção de horário cadastrada.
                </p>
            </c:when>

            <c:otherwise>
                <div class="list">

                    <c:forEach items="${reuniao.opcoesHorarios}" var="horario">

                        <div class="list-item">
                            <div>
                                <strong>
                                    <c:out value="${horario.dataHoraInicio}"/>
                                </strong>

                                <p>
                                    Até
                                    <c:out value="${horario.dataHoraFim}"/>
                                </p>
                            </div>
                        </div>

                    </c:forEach>

                </div>
            </c:otherwise>

        </c:choose>

    </section>

    <!-- Convidados -->
    <section class="card">

        <div class="section-heading">
            <h2>Convidados</h2>
            <span class="count">
                <c:out value="${reuniao.convidados.size()}"/> convidados
            </span>
        </div>

        <c:choose>

            <c:when test="${empty reuniao.convidados}">
                <p class="empty-text">
                    Nenhum convidado cadastrado.
                </p>
            </c:when>

            <c:otherwise>
                <div class="list">

                    <c:forEach items="${reuniao.convidados}" var="convidado">

                        <div class="list-item">

                            <div>
                                <strong>
                                    <c:choose>
                                        <c:when test="${not empty convidado.nome}">
                                            <c:out value="${convidado.nome}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${convidado.email}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </strong>

                                <p>
                                    <c:out value="${convidado.email}"/>
                                </p>
                            </div>

                            <span class="response-status">
                                <c:out value="${convidado.statusResposta}"/>
                            </span>

                        </div>

                    </c:forEach>

                </div>
            </c:otherwise>

        </c:choose>

    </section>

    <div class="footer-actions">

        <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/disponibilidades"
           class="primary-button">
            Ver disponibilidades
        </a>

    </div>

</main>

</body>
</html>
