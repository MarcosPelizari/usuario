
# API de Usuários

## Descrição

A API de Usuários é uma aplicação Java com Spring Boot que gerencia autenticação e dados de usuários. Ela fornece endpoints para criar, autenticar, buscar, atualizar e deletar usuários, além de gerenciar endereços e telefones associados. A autenticação é feita com JWT (JSON Web Token), com tokens válidos por 1 hora.


## Pré-requisitos

- **Java**: 17 ou superior
- **Gradle**: 8.x (usado para gerenciar dependências)
- **PostgreSQL**: 16.x (banco de dados relacional para armazenar dados de usuários)
- **IntelliJ IDEA** (opcional, recomendado para desenvolvimento)
- **Postman** (recomendado para testar os endpoints)

## Instalação
1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/MarcosPelizari/usuario.git
   cd usuario
   ```
## 2. Configure o Banco de Dados:

- Certifique-se de que o PostgreSQL está rodando.

- Crie um banco chamado db_usuario:

```bash
  CREATE DATABASE db_usuario;
```

## Como Usar
### 1. Criar um Usuário
Use o Postman ou outra ferramenta para fazer uma requisição `POST` ao endpoint `/usuario`.

- **POST /usuario**  
  Cria um novo usuário.  
  **Body**:
  ```json
  {
    "nome": "Nome Coloca Aqui",
    "email": "email@exemplo.com",
    "senha": "senha123",
    "enderecos": [
      {
        "rua": "Rua Exemplo",
        "numero": "123",
        "cidade": "São Paulo",
        "estado": "SP",
        "cep": "12345-678"
      }
    ],
    "telefones": [
      {
        "ddd": "11",
        "numero": "987654321"
      }
    ]
  }

### 2. Fazer Login e Obter um Token

Use o endpoint /usuario/login para autenticar e gerar um token JWT (válido por 1 hora).

- POST /usuario/login

Autentica um usuário e retorna um token JWT.

Body:
```json
{
  "email": "email@exemplo.com",
  "senha": "senha123"
}
```

Resposta
```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 3. Buscar Dados do Usuário
Use o endpoint `/usuario` com o parâmetro `email` e o token no header para buscar os dados do usuário.

- **GET /usuario?email=<email>**  
  Busca os dados de um usuário por email.  
  **Parâmetro**:
  - `email`: Email do usuário (ex.: `email@exemplo.com`).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

  **Nota**: Não inclua o prefixo "Bearer" no token.  
**Resposta**:
```json
{
  "nome": "João Silva",
  "email": "joao.silva@exemplo.com",
  "enderecos": [
    {
      "id": 1,
      "rua": "Rua Exemplo",
      "numero": "123",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "12345-678"
    }
  ],
  "telefones": [
    {
      "id": 1,
      "ddd": "11",
      "numero": "987654321"
    }
  ]
}
```

## Endpoints Adicionais
- **DELETE /usuario/{email}**  
  Deleta um usuário por email.  
  **Parâmetro**:
  - `email`: Email do usuário a ser deletado.  
  **Resposta**: Status 200 (OK).

- **PUT /usuario**  
  Atualiza os dados do usuário (requer token).  
  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

- **POST /usuario/endereco**  
  Cadastra um novo endereço para o usuário (requer token).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

  **Body**:
```json
{
  "rua": "Nova Rua",
  "numero": "456",
  "cidade": "Rio de Janeiro",
  "estado": "RJ",
  "cep": "87654-321"
}
```

- **POST /usuario/telefone**  
  Cadastra um novo endereço para o usuário (requer token).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

```json
{
  "ddd": "21",
  "numero": "912345678"
}
```

- **PUT /usuario/endereco?id=<id>**  
  Atualiza um endereço existente do usuário.  
  **Parâmetro**:
  - `id`: ID do endereço a ser atualizado.  
  **Body**:
  ```json
  {
    "rua": "Rua Atualizada",
    "numero": "789",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "12345-678"
  }
  ```

  ## Notas
- A API usa JWT para autenticação. O token gerado no login é válido por 1 hora.
- Para rotas protegidas, inclua o token no header `Authorization` sem o prefixo "Bearer".
- Logs de SQL são exibidos no console (configurado com `spring.jpa.show-sql=true`).