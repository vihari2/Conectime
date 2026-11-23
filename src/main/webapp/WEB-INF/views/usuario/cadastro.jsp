
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Criar conta | Conectime</title>

    <link rel="stylesheet"
          href="<c:url value='/css/cadastro.css'/>">
</head>

<body>

    <main class="cadastro-container">

        <section class="cadastro-card">

            <div class="brand">
                <h1>Conectime</h1>
                <p>Conectando pessoas e horários.</p>
            </div>

            <div class="cadastro-heading">
                <h2>Crie sua conta</h2>
                <p>Organize reuniões e encontre o melhor horário para todos.</p>
            </div>

            <c:if test="${not empty erro}">
                <div class="message error">
                    <c:out value="${erro}"/>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/usuarios/cadastro"
                  method="POST">

                <div class="form-group">
                    <label for="nome">Nome completo</label>

                    <input
                        type="text"
                        id="nome"
                        name="nome"
                        placeholder="Digite seu nome"
                        maxlength="100"
                        autocomplete="name"
                        required
                    >
                </div>

                <div class="form-group">
                    <label for="email">E-mail</label>

                    <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Digite seu e-mail"
                        maxlength="150"
                        autocomplete="email"
                        required
                    >
                </div>

                <div class="form-group">
                    <label for="senha">Senha</label>

                    <div class="password-container">
                        <input
                            type="password"
                            id="senha"
                            name="senha"
                            placeholder="Crie uma senha"
                            autocomplete="new-password"
                            required
                        >

                        <button type="button" id="toggleSenha">
                            Mostrar
                        </button>
                    </div>
                </div>

                <button type="submit" class="cadastro-button">
                    Criar minha conta
                </button>

            </form>

            <div class="login-link">
                <p>
                    Já possui uma conta?
                    <a href="${pageContext.request.contextPath}/usuarios/login">
                        Entrar
                    </a>
                </p>
            </div>

        </section>

    </main>

    <script src="<c:url value='/js/cadastro.js'/>"></script>

</body>
</html>
