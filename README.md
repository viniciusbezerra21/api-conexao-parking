# ⚙️ Conexão Parking - API (Backend)

Esta é a API RESTful que alimenta o sistema **Conexão Parking**. Desenvolvida com **Java 21** e **Spring Boot 3.5**, a aplicação gerencia as regras de negócio, persistência de dados e a segurança robusta do sistema de estacionamento.

---


## 🚀 Tecnologias Utilizadas

* **Java 21**: Utilizando as últimas funcionalidades da linguagem (LTS).
* **Spring Boot 3.5.9**: Framework base para a construção da API.
* **Spring Security & JWT (auth0)**: Autenticação baseada em tokens para acesso seguro.
* **Spring Data JPA**: Abstração da camada de persistência.
* **MySQL**: Banco de dados relacional para armazenamento de veículos e usuários.
* **Flyway**: Gerenciamento de migrações do banco de dados (Versionamento de Schema).
* **Lombok**: Redução de código boilerplate para maior produtividade.
* **SpringDoc OpenAPI (Swagger)**: Documentação automatizada e interativa dos endpoints.
* **Railway**: Plataforma de Cloud Hosting para a API e Banco de Dados MySQL.
* **Spring Profiles**: Configurações distintas para ambientes de `dev` (local) e `prod` (cloud).
* **JWT (java-jwt)**: Implementação robusta de tokens para autenticação Stateless.
---

## 🌐 Deploy (Produção) | Live API

A API está hospedada no Railway e integrada ao banco de dados MySQL gerenciado:
👉 **[Conexão Parking API - Swagger Produção](https://api-conexao-parking.railway.app/swagger-ui/index.html)**

---

## 🏗️ Repositórios do Ecossistema

O sistema é modularizado. Para a interface completa, utilize o repositório do Frontend:

* **Frontend (Angular)**: [Acesse o repositório da interface aqui](https://github.com/viniciusbezerra21/conexao-parking.git)

---

## 🛠️ Configuração e Instalação

### 1. Pré-requisitos
* **JDK 21** ou superior.
* **Maven** 3.x instalado.
* **MySQL** Server ativo.

---

### 2. Configuração do Banco de Dados
No arquivo `src/main/resources/application.properties`, configure suas credenciais locais:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/conexao_parking_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

---

### 3. Execução
Clone o repositório:

```properties
Bash
git clone [https://github.com/viniciusbezerra21/api-conexao-parking.git]()
cd api-conexao-parking
Compile e rode a aplicação:

Bash
# Para rodar com o perfil de desenvolvimento local:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

### 🔑 Variáveis de Ambiente (Environment Variables)

Para rodar o projeto, as seguintes variáveis devem estar configuradas no seu ambiente (IDE ou OS):

| Variável | Descrição | Exemplo Local |
| :--- | :--- | :--- |
| `JWT_SECRET` | Chave para assinatura do Token | `sua_chave_secreta_32_chars` |
| `MYSQL_URL` | URL de conexão com o Banco | `jdbc:mysql://localhost:3306/conexaoparking_api` |
| `MYSQL_USER` | Usuário do MySQL | `root` |
| `MYSQL_PASSWORD` | Senha do MySQL | `root` |

---

### 📖 Documentação da API (Swagger)
Com a aplicação rodando, você pode visualizar, testar e entender todos os endpoints através da interface do Swagger:

🔗 URL Local: http://localhost:8080/swagger-ui/index.html

---

### ✨ Funcionalidades Principais
* **🔐 Autenticação JWT:** Login seguro com geração de tokens para o Frontend.

* **🚗 Gestão de Veículos:** Endpoints completos para cadastro, listagem e filtros.

* **🕒 Cálculo de Fluxo:** Lógica de entrada e saída com controle de tempo de permanência.

* **🛡️ Validação de Dados:** Uso de Bean Validation para garantir integridade nos inputs.

* **📑 Migrações Automáticas:** O Flyway garante que a estrutura do banco esteja sempre sincronizada.

* **🔐 Autenticação & Autorização:** Diferenciação de permissões entre `ROLE_USER` e `ROLE_ADMIN` via Security Filter.

* **🚀 CORS Configuration:** Configuração ajustada para permitir requisições seguras do domínio da Vercel.

---

### 👨‍💻 Autor
Vinicius Bezerra Software Developer & Student
