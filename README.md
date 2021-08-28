# desafio_votoPauta

API Rest com o intuito de solucionar problemas de controle de Pautas para votação.

Possui endpoints para criar pauta, abrir sessão de votos, votar, consultar pautas e consultar status do usuário para voto.

Nela foram utilizadas algumas ferramentas como Java, Spring, RabbitMQ, JUnit, Task Scheduler e Swagger.

Para visualizar a documentação da API basta rodar o projeto e acessar o endpoint 'http://localhost:8080/swagger-ui/#/'.

É necessario ter o RabbitMQ rodando na maquina para utilizar o serviço de mensageria e atualizar o application.properties caso necessario:

	- Download erland = https://www.erlang.org/downloads
  
	- Download rabbitMQ = https://www.rabbitmq.com/download.html
  
	- run server = .../sbin/ rabbitmq-server start
  
	- run rabbitmq console = .../sbin/ rabbitmq-plugins.bat enable rabbitmq_management
  
