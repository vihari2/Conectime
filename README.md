# Conectime

Aplicação web para organizar reuniões. O organizador cria uma reunião e convida pessoas por e-mail. Cada convidado acessa um link exclusivo para informar sua disponibilidade, e o organizador escolhe o horário.

## Tecnologias

- Java 21 e Spring Boot 4.1.3
- Spring MVC, Spring Data JPA e Hibernate
- MySQL 8
- JSP, JSTL, HTML, CSS e JavaScript
- Maven

## Pré-requisitos

Instale Java 21, MySQL 8 e Git. O projeto já inclui o Maven Wrapper.

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

Crie o banco e o usuário no MySQL (defina uma senha própria):

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

Confira também nesse arquivo a configuração de criação/atualização das tabelas (`spring.jpa.hibernate.ddl-auto`) ou execute o script SQL do projeto, caso exista.

## E-mail (Gmail)

O envio de convites usa SMTP. Defina as variáveis no terminal antes de iniciar a aplicação:

```bash
export MAIL_USERNAME="seu-email@gmail.com"
export MAIL_PASSWORD="SUA_SENHA_DE_APP"
```

Use uma senha de app do Google, não a senha normal da conta. Não compartilhe nem envie credenciais ao Git.

## Executar

Com o MySQL ativo e as configurações prontas:

```bash
./mvnw clean compile
./mvnw test
./mvnw spring-boot:run
```

Acesse: <http://localhost:8080>

Para encerrar, pressione `Ctrl + C` no terminal.

## Comandos úteis

| Ação | Comando |
|---|---|
| Iniciar MySQL (Linux) | `sudo systemctl start mysql` |
| Acessar o banco | `mysql -u conectime_user -p -D conectime` |
| Compilar | `./mvnw clean compile` |
| Rodar testes automatizados | `./mvnw test` |
| Iniciar a aplicação | `./mvnw spring-boot:run` |
| Gerar o WAR | `./mvnw clean package` |

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

## Como usar

1. Crie uma conta e faça login como organizador.
2. Cadastre uma reunião, convidados e opções de horário.
3. Envie aos convidados os links exclusivos recebidos por e-mail.
4. Os convidados informam sua disponibilidade.
5. O organizador consulta as respostas e confirma um horário com pelo menos uma disponibilidade.

Link de convite:

```text
http://localhost:8080/convites/TOKEN_DO_CONVIDADO
```

## Testes principais

Verifique cadastro e login, criação e exclusão de reuniões, links de convite, envio e alteração de disponibilidades, acesso indevido a reuniões de terceiros e confirmação de horários. A confirmação deve ser bloqueada quando nenhum convidado estiver disponível; convidados pendentes não impedem a confirmação quando outro convidado marcou o horário.

> `./mvnw test` executa os testes automatizados existentes. Os fluxos da interface também precisam ser testados manualmente.

## Observações

- Se `mvnw` apresentar erro de permissão, execute `chmod +x mvnw`.
- Se a porta 8080 estiver ocupada, encerre a outra aplicação que a utiliza.
- Para usar em outro computador ou servidor, ajuste `app.base-url` no `application.properties`.
- Nunca publique senhas, tokens de convidados ou dados pessoais de teste.

---

Projeto acadêmico desenvolvido com Java, Spring Boot, JSP/JSTL e MySQL.
