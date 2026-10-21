# API de Assistência Técnica

Projeto em Java com Spring Boot para cadastrar clientes, cadastrar equipamentos e abrir ordens de serviço.

## Como executar

É necessário ter o JDK 25 e o Maven instalados.

Na pasta do projeto, execute:

    mvn spring-boot:run

Para rodar os testes:

    mvn test

O projeto usa o banco H2 em memória. Ao reiniciar a aplicação, os dados cadastrados são apagados.

## Endpoints

### Clientes

- `POST /clientes` — cadastra um cliente.
- `GET /clientes` — lista os clientes.

Exemplo de corpo para cadastrar um cliente:

    {
      "nome": "Maria da Silva",
      "ativo": true
    }

O campo `ativo` pode ser omitido. Nesse caso, o cliente é cadastrado como ativo.

### Equipamentos

- `POST /equipamentos` — cadastra um equipamento para um cliente.
- `GET /equipamentos` — lista os equipamentos.

Exemplo:

    {
      "descricao": "Notebook Lenovo",
      "clienteId": 1
    }

O `clienteId` deve ser de um cliente que já existe.

### Ordens de Serviço

- `POST /ordens` — abre uma ordem de serviço.

Exemplo:

    {
      "clienteId": 1,
      "equipamentoId": 1,
      "descricaoProblema": "Notebook não liga."
    }

Use os IDs que foram retornados ao cadastrar o cliente e o equipamento. Quando a ordem é aberta, ela recebe um número de protocolo, começa com o status `RECEBIDA` e tem um registro no histórico.

## Regras

- O nome do cliente é obrigatório.
- A descrição do equipamento e o cliente são obrigatórios.
- A descrição do problema, o cliente e o equipamento são obrigatórios para abrir uma OS.
- Não é possível abrir uma OS para um cliente inativo.
- O equipamento deve pertencer ao cliente informado.
- Se o cliente ou equipamento não existir, a API retorna erro 404.
- Campos inválidos retornam erro 400 e regras de negócio não atendidas retornam erro 422.

## Banco de dados

A console do H2 fica em http://localhost:8080/h2-console enquanto a aplicação está rodando.

- JDBC URL: `jdbc:h2:mem:assistencia`
- Usuário: `sa`
- Senha: deixe em branco

## Exemplo de teste pelo PowerShell

Com a aplicação rodando, execute os comandos na ordem:

    $cliente = Invoke-RestMethod -Uri "http://localhost:8080/clientes" -Method Post -ContentType "application/json" -Body '{"nome":"Maria da Silva","ativo":true}'

    $equipamentoBody = @{
      descricao = "Notebook Lenovo"
      clienteId = $cliente.id
    } | ConvertTo-Json

    $equipamento = Invoke-RestMethod -Uri "http://localhost:8080/equipamentos" -Method Post -ContentType "application/json" -Body $equipamentoBody

    $ordemBody = @{
      clienteId = $cliente.id
      equipamentoId = $equipamento.id
      descricaoProblema = "Notebook não liga."
    } | ConvertTo-Json

    Invoke-RestMethod -Uri "http://localhost:8080/ordens" -Method Post -ContentType "application/json" -Body $ordemBody
