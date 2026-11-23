
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Minhas reuniões | Conectime</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/lista.css">
</head>

<body>

<header class="topbar">

    <a href="${pageContext.request.contextPath}/reunioes"
       class="brand">
        Conectime
    </a>

    <div class="user-area">
        <span>Olá, <c:out value="${sessionScope.usuarioNome}"/>!</span>

        <form action="${pageContext.request.contextPath}/usuarios/logout"
              method="POST">
            <button type="submit" class="logout-button">
                Sair
            </button>
        </form>
    </div>

</header>

<main class="dashboard">

    <section class="welcome-section">

        <div>
            <span class="eyebrow">PAINEL DO ORGANIZADOR</span>

            <h1>Minhas reuniões</h1>

            <p>
                Organize seus encontros e encontre o melhor horário
                para todos.
            </p>
        </div>

        <a href="${pageContext.request.contextPath}/reunioes/nova"
           class="create-button">
            + Criar reunião
        </a>

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

    <section class="meetings-section">

        <div class="section-header">
            <h2>Seus agendamentos</h2>
            <span class="meeting-count">
                <c:out value="${reunioes.size()}"/> reuniões
            </span>
        </div>

        <c:choose>

            <c:when test="${empty reunioes}">

                <div class="empty-state">

                    <div class="empty-icon">📅</div>

                    <h3>Nenhuma reunião por aqui</h3>

                    <p>
                        Você ainda não criou nenhum agendamento.
                        Que tal organizar sua primeira reunião?
                    </p>

                    <a href="${pageContext.request.contextPath}/reunioes/nova"
                       class="create-button">
                        Criar minha primeira reunião
                    </a>

                </div>

            </c:when>

            <c:otherwise>

                <div class="meeting-list">

                    <c:forEach items="${reunioes}" var="reuniao">

                        <article class="meeting-card">

                            <div class="meeting-info">

                                <div class="meeting-title">
                                    <h3>
                                        <c:out value="${reuniao.titulo}"/>
                                    </h3>

                                    <span class="status status-${reuniao.status}">
                                        <c:out value="${reuniao.status}"/>
                                    </span>
                                </div>

                                <p class="meeting-description">
                                    <c:choose>
                                        <c:when test="${not empty reuniao.descricao}">
                                            <c:out value="${reuniao.descricao}"/>
                                        </c:when>
                                        <c:otherwise>
                                            Sem descrição.
                                        </c:otherwise>
                                    </c:choose>
                                </p>

                                <div class="meeting-meta">
                                    <span>
                                        Formato:
                                        <c:out value="${reuniao.formato}"/>
                                    </span>

                                    <span>
                                        Reunião #<c:out value="${reuniao.idReuniao}"/>
                                    </span>
                                </div>

                            </div>

                            <div class="meeting-actions">

                                <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}"
                                   class="action-link">
                                    Detalhes
                                </a>

                                <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/horarios"
                                   class="action-link">
                                    Horários
                                </a>

                                <a href="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/disponibilidades"
                                   class="action-link">
                                    Respostas
                                </a>

                                <form
                                    action="${pageContext.request.contextPath}/reunioes/${reuniao.idReuniao}/excluir"
                                    method="POST"
                                    onsubmit="return confirm('Tem certeza que deseja excluir esta reunião?');">

                                    <button type="submit" class="delete-button">
                                        Excluir
                                    </button>

                                </form>

                            </div>

                        </article>

                    </c:forEach>

                </div>

            </c:otherwise>

        </c:choose>

    </section>

</main>

</body>
</html>
