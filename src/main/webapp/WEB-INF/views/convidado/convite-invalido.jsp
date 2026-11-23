
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Convite inválido | Conectime</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/convite.css">
</head>

<body>

<header class="topbar">
    <span class="brand">Conectime</span>
</header>

<main class="container">

    <section class="card empty-state">

        <h2>Convite não encontrado</h2>

        <p>
            <c:out value="${erro}"/>
        </p>

        <p>
            Verifique o link recebido por e-mail ou entre em contato
            com a pessoa que organizou a reunião.
        </p>

    </section>

</main>

</body>
</html>
