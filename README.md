# API de Assistência Técnica

API REST em Spring Boot para abertura de Ordens de Serviço.

## Requisitos
- JDK 25
- Maven 3.9+ (ou Maven Wrapper, caso adicionado ao projeto)

## Executar
Na pasta do projeto:

```bash
mvn spring-boot:run
```

Executar testes:

```bash
mvn test
```

A aplicação usa H2 em memória para facilitar a execução local. Os dados são recriados quando a aplicação reinicia.

## Endpoint: abrir Ordem de Serviço

`POST /ordens`

Exemplo de requisição:

```json
{
  "clienteId": 1,
  "equipamentoId": 2,
  "descricaoProblema": "Notebook não liga após queda de energia elétrica."
}
```

Quando a requisição é válida e o cliente/equipamento existem, retorna HTTP `201 Created`:

```json
{
  "id": 1,
  "numeroProtocolo": "OS-20261020-A1B2",
  "status": "RECEBIDA",
  "dataAbertura": "2026-10-20T20:30:00"
}
```

O protocolo e a data são gerados pela aplicação; os valores acima são apenas ilustrativos.

## Regras e erros
- Campos ausentes ou inválidos: HTTP 400 com ProblemDetail e propriedade `campos`.
- Cliente ou equipamento inexistente: HTTP 404 com ProblemDetail.
- Cliente inativo: HTTP 422 com a mensagem `Não é permitido abrir Ordem de Serviço para um cliente inativo.`
- Equipamento que pertence a outro cliente: HTTP 422 com a mensagem `O equipamento informado não pertence ao cliente indicado.`
- Toda ordem nasce com status `RECEBIDA` e gera histórico inicial com responsável `SISTEMA`.

## Nota sobre dados iniciais
A API não cria clientes nem equipamentos automaticamente. Para testar o POST, cadastre dados de clientes/equipamentos no banco ou utilize os dados preparados nos testes automatizados. A console H2 fica disponível em `/h2-console` durante a execução local.
