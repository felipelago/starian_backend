<h1>Starian Backend API</h1>
<p></p>
<hr/>

<p>API REST desenvolvida em Java 21 com Spring Boot 3.4.1 para cadastro de usuários e consulta de endereços via ViaCEP. O projeto utiliza arquitetura Onion criada por Jeffrey Palermo (2008), onde não requer interfaces explícitas, mais focada em dependência entre camadas (Domain -> Application -> Infrastructure -> Presentation).</p>

<h2>💡 Tecnologias utilizadas</h2>

<h3>Java 21 + Spring Boot 3.4.1</h3>
<ul>
  <li><strong>Spring Boot</strong>: framework robusto e produtivo para aplicações Java modernas.</li>
  <li><strong>Spring Web</strong>: para criação de endpoints REST.</li>
  <li><strong>Spring Data JPA e Hibernate</strong>: para persistência de dados.</li>
  <li><strong>Spring Validation</strong>: para validação de inputs.</li>
  <li><strong>H2 Database</strong>: banco de dados em memória para desenvolvimento.</li>
  <li><strong>SpringDoc OpenAPI (Swagger)</strong>: para documentação da API.</li>
  <li><strong>Maven</strong>: gerenciamento de dependências.</li>
</ul>

<h2>🚀 Como Executar</h2>

<h3>Pré-requisitos</h3>
<ul>
  <li>Java 21</li>
  <li>Maven 3.6+</li>
</ul>

<h3>Executando a aplicação</h3>

<ol>
  <li><strong>Clone o repositório</strong>
    <pre>git clone https://github.com/felipelago/starian_backend.git
cd CepBackend</pre>
  </li>
  
  <li><strong>Execute com Maven</strong>
    <pre>./mvnw spring-boot:run</pre>
  </li>
  
  <li><strong>Acesse a documentação da API</strong>
    <pre>http://localhost:8080/swagger-ui.html</pre>
  </li>
</ol>

<h3>Banco de Dados</h3>
<ul>
  <li>A aplicação utiliza <strong>H2 Database</strong> em memória</li>
  <li>Console H2: <code>http://localhost:8080/h2-console</code></li>
  <li>JDBC URL: <code>jdbc:h2:mem:testdb</code></li>
  <li>Username: <code>sa</code></li>
  <li>Password: <code>password</code></li>
</ul>

<h2>📋 Endpoints Principais</h2>


<h2>📝 Próximos Passos</h2>

<ul>
  <li>[ ] Implementar paginação nas listagens</li>
  <li>[ ] Melhorar documentação da API</li>
  <li>[ ] Explorar integração com banco PostgreSQL para ambientes de produção</li>
  <li>[ ] Criar camada de segurança utilizando JWT</li>
</ul>
