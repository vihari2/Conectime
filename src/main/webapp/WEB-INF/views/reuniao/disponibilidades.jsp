<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Disponibilidades | Conectime</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/disponibilidades.css">
</head>

<body>

    <!-- Cabeçalho -->
    <header class="topbar">

        <a href="${pageContext.request.contextPath}/reunioes"
           class="brand">
            Conectime
        </a>

        <a href="${pageContext.request.contextPath}/reunioes"
           class="back-link">
            Minhas reuniões
        </a>

    </header>

    <main class="container">

        <!-- Botão de voltar -->
        <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}"
           class="back-button">
            ← Voltar aos detalhes
        </a>

        <!-- Cabeçalho da página -->
        <section class="page-heading">

            <span class="eyebrow">RESPOSTAS DOS CONVIDADOS</span>

            <h1>Disponibilidades</h1>

            <p>
                Reunião:
                <strong>
                    <c:out value="${reuniao.titulo}"/>
                </strong>
            </p>

        </section>

        <!-- Mensagens de sucesso e erro -->
        <c:if test="${not empty sucesso}">
            <div class="page-message success">
                <c:out value="${sucesso}"/>
            </div>
        </c:if>

        <c:if test="${not empty erro}">
            <div class="page-message error">
                <c:out value="${erro}"/>
            </div>
        </c:if>

        <!-- Exibe o horário confirmado -->
        <c:if test="${reuniao.status == 'confirmada'}">

            <div class="confirmation-message">

                <strong>Reunião confirmada!</strong>

                <p>
                    Horário escolhido:

                    <fmt:parseDate
                        value="${reuniao.horarioEscolhido.dataHoraInicio}"
                        pattern="yyyy-MM-dd'T'HH:mm"
                        var="inicioFormatado" />
                    <fmt:formatDate value="${inicioFormatado}" pattern="dd/MM/yyyy 'às' HH:mm" />

                    até

                    <fmt:parseDate
                        value="${reuniao.horarioEscolhido.dataHoraFim}"
                        pattern="yyyy-MM-dd'T'HH:mm"
                        var="fimFormatado" />
                    <fmt:formatDate value="${fimFormatado}" pattern="dd/MM/yyyy 'às' HH:mm" />
                </p>

            </div>

        </c:if>

        <!-- Resumo dos convidados -->
        <section class="summary-card">

            <div>
                <span class="summary-label">Total de convidados</span>

                <strong>
                    <c:out value="${reuniao.convidados.size()}"/>
                </strong>
            </div>

            <div>
                <span class="summary-label">Já responderam</span>

                <strong>
                    <c:out value="${respondidos}"/>
                </strong>
            </div>

            <div>
                <span class="summary-label">Pendentes</span>

                <strong>
                    <c:out value="${pendentes}"/>
                </strong>
            </div>

        </section>

        <!-- Resultado por horário -->
        <section class="results-section">

            <div class="section-title">

                <h2>Resultado por horário</h2>

                <p>
                    Veja quantos convidados podem participar de cada opção.
                </p>

            </div>

            <c:choose>

                <c:when test="${empty horarios}">

                    <div class="empty-state">

                        <span class="empty-icon">📅</span>

                        <h3>Nenhum horário cadastrado</h3>

                        <p>
                            Cadastre opções de horários para começar a receber
                            as disponibilidades dos convidados.
                        </p>

                    </div>

                </c:when>

                <c:otherwise>

                    <div class="schedule-list">

                        <c:forEach items="${horarios}" var="horario">

                            <article class="schedule-card">

                                <!-- Informações do horário -->
                                <div class="schedule-header">

                                    <div>

                                        <span class="schedule-label">
                                            OPÇÃO DE HORÁRIO
                                        </span>

                                        <h3>
                                            <fmt:parseDate
                                                value="${horario.dataHoraInicio}"
                                                pattern="yyyy-MM-dd'T'HH:mm"
                                                var="inicioHorarioFormatado" />
                                            <fmt:formatDate
                                                value="${inicioHorarioFormatado}"
                                                pattern="dd/MM/yyyy 'às' HH:mm" />
                                        </h3>

                                        <p>
                                            Até
                                            <fmt:parseDate
                                                value="${horario.dataHoraFim}"
                                                pattern="yyyy-MM-dd'T'HH:mm"
                                                var="fimHorarioFormatado" />
                                            <fmt:formatDate
                                                value="${fimHorarioFormatado}"
                                                pattern="dd/MM/yyyy 'às' HH:mm" />
                                        </p>

                                    </div>

                                    <div class="availability-count">

                                        <strong>
                                            <c:out value="${totalPorHorario[horario.idHorario]}"/>
                                        </strong>

                                        <span>disponíveis</span>

                                    </div>

                                </div>

                                <!-- Lista de convidados disponíveis -->
                                <div class="participants">

                                    <h4>Convidados disponíveis</h4>

                                    <c:choose>

                                        <c:when test="${empty disponibilidadesPorHorario[horario.idHorario]}">

                                            <p class="no-answers">
                                                Nenhum convidado marcou este horário.
                                            </p>

                                        </c:when>

                                        <c:otherwise>

                                            <ul class="participant-list">

                                                <c:forEach
                                                    items="${disponibilidadesPorHorario[horario.idHorario]}"
                                                    var="disponibilidade">

                                                    <li>

                                                        <span class="participant-icon">
                                                            ✓
                                                        </span>

                                                        <div>

                                                            <strong>

                                                                <c:choose>

                                                                    <c:when test="${not empty disponibilidade.convidado.nome}">
                                                                        <c:out value="${disponibilidade.convidado.nome}"/>
                                                                    </c:when>

                                                                    <c:otherwise>
                                                                        <c:out value="${disponibilidade.convidado.email}"/>
                                                                    </c:otherwise>

                                                                </c:choose>

                                                            </strong>

                                                            <span>
                                                                <c:out value="${disponibilidade.convidado.email}"/>
                                                            </span>

                                                        </div>

                                                    </li>

                                                </c:forEach>

                                            </ul>

                                        </c:otherwise>

                                    </c:choose>

                                </div>

                                <!-- Confirmação do horário -->
                                <c:choose>

                                    <c:when test="${reuniao.status == 'aguardando'}">

                                        <form
                                            class="confirm-form"
                                            method="post"
                                            action="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/disponibilidades/confirmar">

                                            <input
                                                type="hidden"
                                                name="idHorario"
                                                value="${horario.idHorario}">

                                            <button
                                                type="submit"
                                                class="confirm-button"
                                                onclick="return confirm('Deseja confirmar este horário para a reunião?');">

                                                Confirmar este horário

                                            </button>

                                        </form>

                                    </c:when>

                                    <c:when test="${reuniao.status == 'confirmada' and reuniao.horarioEscolhido.idHorario == horario.idHorario}">

                                        <span class="confirmed-label">
                                            ✓ Horário confirmado
                                        </span>

                                    </c:when>

                                </c:choose>

                            </article>

                        </c:forEach>

                    </div>

                </c:otherwise>

            </c:choose>

        </section>

    </main>

</body>
</html>
