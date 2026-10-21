# API de Assistência Técnica

API REST desenvolvida com Java e Spring Boot para cadastro de clientes, cadastro de equipamentos e abertura de Ordens de Serviço (OS).

## Requisitos
- JDK 25 (conforme configuração do projeto)
- Maven 3.9+

## Executar
Na pasta do projeto, rode:

    mvn spring-boot:run

Para executar os testes automatizados:

    mvn test

A aplicação usa o banco H2 em memória. Os registros são perdidos quando a aplicação é reiniciada.

## Fluxo de teste recomendado

A aplicação deve estar rodando em http://localhost:8080. Faça as requisições abaixo nesta ordem usando Postman, Insomnia ou PowerShell.

### 1. Cadastrar cliente

POST http://localhost:8080/clientes

Cabeçalho: Content-Type: application/json

Corpo JSON:

    {
      "nome": "Maria da Silva",
      "ativo": true
    }

Resposta esperada: HTTP 201 Created, com o ID gerado. O campo ativo é opcional; se omitido, o cliente será cadastrado como ativo.

### 2. Listar clientes

GET http://localhost:8080/clientes

Retorna os clientes cadastrados e seus IDs.

### 3. Cadastrar equipamento

POST http://localhost:8080/equipamentos

    {
      "descricao": "Notebook Lenovo",
      "clienteId": 1
    }

Use o ID real retornado na etapa 1. Resposta esperada: HTTP 201 Created.

### 4. Listar equipamentos

GET http://localhost:8080/equipamentos

Retorna os equipamentos cadastrados e o cliente a que cada um pertence.

### 5. Abrir Ordem de Serviço

POST http://localhost:8080/ordens

    {
      "clienteId": 1,
      "equipamentoId": 1,
      "descricaoProblema": "Notebook não liga após queda de energia."
    }

Use os IDs reais retornados nas etapas anteriores. Resposta esperada: HTTP 201 Created, com número de protocolo, status RECEBIDA e data de abertura. A aplicação também registra o histórico inicial da OS.

## Testar pelo PowerShell

Com a aplicação em execução, copie e execute os comandos abaixo um por vez:

    $cliente = Invoke-RestMethod -Uri "http://localhost:8080/clientes" -Method Post -ContentType "application/json" -Body '{"nome":"Maria da Silva","ativo":true}'
    $cliente | Format-List

    $equipamentoBody = @{
      descricao = "Notebook Lenovo"
      clienteId = $cliente.id
    } | ConvertTo-Json

    $equipamento = Invoke-RestMethod -Uri "http://localhost:8080/equipamentos" -Method Post -ContentType "application/json" -Body $equipamentoBody
    $equipamento | Format-List

    $ordemBody = @{
      clienteId = $cliente.id
      equipamentoId = $equipamento.id
      descricaoProblema = "Notebook não liga após queda de energia."
    } | ConvertTo-Json

    Invoke-RestMethod -Uri "http://localhost:8080/ordens" -Method Post -ContentType "application/json" -Body $ordemBody

Esse fluxo cadastra os registros necessários antes de abrir a OS, evitando o erro 404 Cliente não encontrado.

## Validações e regras de negócio
- Nome do cliente é obrigatório.
- Descrição do equipamento e ID do cliente são obrigatórios.
- Descrição do problema e IDs de cliente/equipamento são obrigatórios na abertura da OS.
- Não é possível cadastrar equipamento para um cliente inexistente.
- Não é permitido abrir OS para cliente inativo.
- O equipamento precisa pertencer ao cliente informado na OS.
- Uma OS válida começa com status RECEBIDA e recebe histórico inicial com responsável SISTEMA.

## Respostas de erro
- 400 Bad Request: campos ausentes ou inválidos.
- 404 Not Found: cliente ou equipamento não encontrado.
- 422 Unprocessable Entity: regra de negócio violada, como cliente inativo ou equipamento vinculado a outro cliente.

## Console do H2

Durante a execução local, acesse http://localhost:8080/h2-console.

- JDBC URL: jdbc:h2:mem:assistencia
- User Name: sa
- Password: deixe em branco

A console é opcional; para o teste normal, use os endpoints de cadastro acima.
