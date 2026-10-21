# Sistema de Assistência Técnica

Projeto feito em Java com Spring Boot para cadastrar clientes e equipamentos e abrir ordens de serviço.

## Executar o projeto

Tenha o JDK 25 e o Maven instalados. Abra o terminal na pasta do projeto e execute:

    mvn spring-boot:run

Para executar os testes:

    mvn test

O banco usado é o H2 em memória, então os dados são apagados quando a aplicação é reiniciada.

## Endpoints

### Clientes
- `POST /clientes`: cadastrar cliente.
- `GET /clientes`: listar clientes.

Exemplo para cadastrar:

    {
      "nome": "Maria da Silva",
      "ativo": true
    }

### Equipamentos
- `POST /equipamentos`: cadastrar equipamento.
- `GET /equipamentos`: listar equipamentos.

Exemplo:

    {
      "descricao": "Notebook Lenovo",
      "clienteId": 1
    }

O cliente precisa estar cadastrado antes de criar o equipamento.

### Ordens de Serviço
- `POST /ordens`: abrir uma ordem de serviço.

Exemplo:

    {
      "clienteId": 1,
      "equipamentoId": 1,
      "descricaoProblema": "Notebook não liga."
    }

Use os IDs retornados no cadastro do cliente e do equipamento. A ordem é criada com status `RECEBIDA`, recebe um número de protocolo e tem seu histórico inicial registrado.

## Validações

Não é possível abrir uma ordem para um cliente inativo, nem usar um equipamento que pertença a outro cliente. Os campos obrigatórios também são validados pela API.

## Banco H2

Com a aplicação rodando, a console pode ser acessada em http://localhost:8080/h2-console.

- JDBC URL: `jdbc:h2:mem:assistencia`
- Usuário: `sa`
- Senha: em branco
