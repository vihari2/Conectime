
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Entrar | Conectime</title>

    <link rel="stylesheet"
          href="<c:url value='/css/login.css'/>">
</head>

<body>

    <main class="login-container">

        <section class="login-card">

            <div class="brand">
                <h1>Conectime</h1>
                <p>Conectando pessoas e horários.</p>
            </div>

            <div class="login-heading">
                <h2>Bem-vindo de volta!</h2>
                <p>Entre na sua conta para continuar.</p>
            </div>

            <!-- Mensagem de erro -->
            <c:if test="${not empty erro}">
                <div class="message error">
                    <c:out value="${erro}"/>
                </div>
            </c:if>

            <!-- Mensagem de sucesso -->
            <c:if test="${not empty sucesso}">
                <div class="message success">
                    <c:out value="${sucesso}"/>
                </div>
            </c:if>

            <!-- Formulário de login -->
            <form action="${pageContext.request.contextPath}/usuarios/login"
                  method="POST">

                <div class="form-group">
                    <label for="email">E-mail</label>

                    <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Digite seu e-mail"
                        autocomplete="username"
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
                            placeholder="Digite sua senha"
                            autocomplete="current-password"
                            required
                        >

                        <button type="button" id="toggleSenha">
                            Mostrar
                        </button>

                    </div>
                </div>

                <button type="submit" class="login-button">
                    Entrar
                </button>

            </form>

            <div class="register-link">
                <p>
                    Ainda não tem uma conta?
                    <a href="${pageContext.request.contextPath}/usuarios/cadastro">
                        Cadastre-se
                    </a>
                </p>
            </div>

        </section>

    </main>

    <script src="<c:url value='/js/login.js'/>"></script>

</body>
</html>
