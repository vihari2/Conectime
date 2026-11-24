# Conectime

Aplicação web para organizar reuniões. O organizador cria uma reunião e convida pessoas por e-mail. Cada convidado acessa um link exclusivo para informar sua disponibilidade, e o organizador escolhe o horário.

## Tecnologias

- Java 21 e Spring Boot 4.1.3
- Spring MVC, Spring Data JPA e Hibernate
- MySQL 8
- JSP, JSTL, HTML, CSS e JavaScript
- Maven
- Visual Studio Code

## Pré-requisitos

Instale Java 21, MySQL 8 e Git.

Confira as instalações:

```bash
java -version
mysql --version
git --version
```

## Instalação

Clone o repositório e entre na pasta:

```bash
git clone URL_DO_REPOSITORIO
cd conectime
chmod +x mvnw
```

Crie o banco e o usuário no MySQL:

```sql
CREATE DATABASE conectime CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'conectime_user'@'localhost' IDENTIFIED BY 'SUA_SENHA';
GRANT ALL PRIVILEGES ON conectime.* TO 'conectime_user'@'localhost';
FLUSH PRIVILEGES;
```

Configure a conexão em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/conectime
spring.datasource.username=conectime_user
spring.datasource.password=SUA_SENHA
```

## E-mail (Gmail)

O envio de convites usa SMTP. Defina as variáveis no terminal antes de iniciar a aplicação:

```bash
export MAIL_USERNAME="seu-email@gmail.com"
export MAIL_PASSWORD="SUA_SENHA_DE_APP"
```
Use uma senha de app do Google
https://myaccount.google.com/apppasswords

## Executar

Com o MySQL ativo e as configurações prontas:

```bash
./mvnw clean compile
./mvnw test
./mvnw spring-boot:run
```

Acesse: <http://localhost:8080>

## Estrutura principal

```text
src/main/
├── java/br/com/conectime/
│   ├── config/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   └── service/
├── resources/
│   └── static/        # CSS e JavaScript
└── webapp/WEB-INF/views/  # páginas JSP
```

- `controller`: rotas e fluxo das páginas.
- `service`: regras de negócio.
- `repository`: acesso ao banco.
- `model`: entidades do sistema.

## Testes principais

> `./mvnw test` 

---

Projeto acadêmico desenvolvido com Java, Spring Boot, JSP/JSTL e MySQL.
Aluna: Vitória de Freitas Machado da Silva.
