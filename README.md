# Raízes do Nordeste API 💻

API Back-End desenvolvida em Java com Spring Boot para uma rede de lanchonetes.

O projeto permite cadastrar usuários, produtos, unidades, controlar estoque, criar pedidos, adicionar itens ao pedido, processar pagamento mock e consultar pontos de fidelidade.

---

## 💻 Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Maven
- Lombok
- Bean Validation
- Swagger/OpenAPI
- Postman
- Git e GitHub
- IntelliJ IDEA

---

## 🚀 Como utilizar o projeto

### Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 21
- Maven
- MySQL
- Git
- Postman

---

### Clonando o projeto

```bash
git clone https://github.com/LeoXavierC/Raizes-do-Nordeste.git
```

### Configurando o banco de dados
Use o comando para criação no MySQL

```bash
CREATE DATABASE raizes_do_nordeste;
```

### Configurando .env
Use o arquivo `Publico.env` como referência

```bash
DB_URL=jdbc:mysql://localhost:3306/raizes_do_nordeste
DB_USERNAME=root
DB_PASSWORD=Senha
JWT_SECRET=troque_por_sua_senha_UNINTER
```
### Executando a API
Execute a classe principal, a API será iniciada por padrão na porta 8080.

`Observação:` acessar apenas http://localhost:8080 pelo navegador pode retornar 403 Forbidden, pois a API não possui uma página inicial pública. Para testar a aplicação, utilize o Swagger ou os endpoints documentados.

### Acesse a documentação do Swagger em:
```bash
http://localhost:8080/swagger-ui/index.html
```

Rota OpenAPI:
```Bash
http://localhost:8080/v3/api-docs
```
## 🔐 Autenticação

### A API utiliza autenticação com JWT.

Antes de realizar o login, é necessário cadastrar um usuário.

### 1. Criar usuário

Endpoint

```http
POST /usuarios
```

### Exemplo para cliente:

```bash
{
  "nome": "Leo",
  "email": "leo.teste@email.com",
  "senhaHash": "123456",
  "telefone": "41999990000",
  "consentimentoLGPD": true,
  "perfil": "CLIENTE"
}
```

### Exemplo para gerente:

```bash
{
  "nome": "Gerente",
  "email": "gerente.teste@email.com",
  "senhaHash": "123456",
  "telefone": "41988880000",
  "consentimentoLGPD": true,
  "perfil": "GERENTE"
}
```
### 2. Fazer login
Após o cadastro, faça login para obter o token JWT.

ENDPOINT
```http
POST /auth/login
```
Exemplos:
```bash
 {
  "email": "leo.teste@email.com",
  "senha": "123456"
 }
```
como resposta o esperado é um token:
```bash
{
    "token": "eyJhbiOiJIUzI1NiJ9.eyJzdWIiOiJVzdGVAZW1haWwuY2............"
}
```
### 3. Usar o token
Nos endpoints protegidos, envie o token no header da requisição:
```http
Authorization: Bearer seu_token
```
No Swagger, clique em Authorize e cole somente o token, sem escrever `Bearer`

### Tipos de perfil:


| Perfil  | Permissões |
|---|---|
| CLIENTE | Criar pedidos, adicionar itens, processar pagamento e consultar fidelidade |
| GERENTE | Gerenciar produtos e estoques |


## 📍 Principais endpoints

| Método | Rota | Descrição |
|---|---|---|
| POST | `/usuarios` | Cadastra usuário |
| POST | `/auth/login` | Realiza login e retorna token JWT |
| POST | `/unidades` | Cadastra unidade |
| POST | `/produtos` | Cadastra produto |
| POST | `/estoques` | Cadastra estoque |
| POST | `/pedidos` | Cria pedido |
| POST | `/itens-pedidos` | Adiciona item ao pedido |
| POST | `/pagamentos/{pedidoId}` | Processa pagamento mock |
| GET | `/fidelidade/usuario/{usuarioId}` | Consulta fidelidade do usuário |

---

## 🧪 Collection Postman
A Collection está disponível no repositório
```text
postman/Raizes-do-Nordeste-API.postman_collection.json
```
### Para executar:

1.Importe a collection no postman
2.Configure a variável `baseUrl`:
http://localhost:8080

3.Limpe o banco antes de rodar os testes:
```bash
USE raizes_do_nordeste;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE pagamentos;
TRUNCATE TABLE itens_pedidos;
TRUNCATE TABLE fidelidade;
TRUNCATE TABLE estoques;
TRUNCATE TABLE pedidos;
TRUNCATE TABLE produtos;
TRUNCATE TABLE unidades;
TRUNCATE TABLE usuarios;

SET FOREIGN_KEY_CHECKS = 1;
```
4. Execute a collection.
`MUITO IMPORTANTE!` execute a collection em `ORDEM CRESCENTE` `T01` ao `T27`

## ✅Testes implementados

A collection cobre os principais cenários:

- Cadastro de cliente e gerente  
- Login com JWT  
- Acesso sem token  
- Acesso com perfil sem permissão  
- Cadastro de unidade, produto e estoque  
- Criação de pedido  
- Adição de item ao pedido  
- Cálculo do valor total  
- Estoque insuficiente  
- Pagamento mock aprovado  
- Pagamento mock recusado  
- Pagamento duplicado  
- Consulta de fidelidade  
- Campo obrigatório ausente  
- Email inválido  
- Quantidade negativa  
- Produto inexistente  
- Estoque duplicado

## 📝 Logs

O projeto possui registro de logs simples em ações como:

- criação de pedido
- Adição de item ao pedido
- Atualização de estoque
- Pagamento aprovado
- Pagamento recusado

### Deploy
Este projeto não possui deploy publicado nesta versão

A execução é somente local.











