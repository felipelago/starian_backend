<h1>Starian Backend API</h1>

<p>API REST desenvolvida em Java 21 com Spring Boot 3.4.1 e banco de dados H2 para facilitar o desenvolvimento e teste (banco de dados em memória, sempre que a aplicação reiniciar os dados serão perdidos) para cadastro de usuários e consulta de endereços via ViaCEP. O projeto utiliza arquitetura Onion criada por Jeffrey Palermo (2008), onde não requer interfaces explícitas, mais focada em dependência entre camadas (Domain -> Application -> Infrastructure -> Presentation).</p>
<p>Sistema de auditoria que captura automaticamente todas as requisições HTTP através de um filtro (`AuditFilter`), registrando método, caminho, status, tempo de resposta, IP, usuário autenticado(se houver) e cabeçalhos customizados em banco de dados de forma assíncrona.
</p>

<h2>🏗️ Decisões Arquiteturais</h2>

Este projeto adota uma **arquitetura em camadas com Ports & Adapters** (inspirada em Onion Architecture e Arquitetura Hexagonal), com foco em **pragmatismo sobre pureza arquitetural**. As decisões foram tomadas considerando a natureza CRUD da aplicação e o princípio de não adicionar complexidade desnecessária.

### Estrutura de Camadas

- **Domain**: Entidades de negócio e exceções.  Utiliza anotações JPA diretamente nas entidades (Anemic Domain Model), apropriado para aplicações CRUD sem lógica de negócio complexa.
- **Application**: Contém os Services (casos de uso) e os **Ports** (interfaces que definem contratos de comunicação com a infraestrutura). Os Ports estão nesta camada porque a Application é o núcleo funcional do sistema.
- **Infrastructure**:  Implementações concretas dos Ports (repositories com Spring Data JPA, adapters de APIs externas, filtros). Depende da Application através das interfaces. 
- **Presentation**: Controllers REST que expõem os endpoints. Depende apenas da Application. 

### Por que JPA no Domain? 

Separar completamente o modelo de domínio do modelo de persistência (criando entidades duplicadas e mappers) seria **over-engineering** para um sistema CRUD.  O acoplamento com JPA é aceitável porque: 
- O domínio não possui lógica de negócio complexa que justifique isolamento total
- O mapeamento objeto-relacional é praticamente 1:1
- A produtividade e simplicidade superam o ganho teórico de "pureza" arquitetural
- Se a complexidade do domínio crescer, pode-se refatorar posteriormente

### Por que Ports na Application (e não no Domain)?

Os Ports (interfaces de repositórios e clients externos) estão na camada Application porque ela define **o que precisa da infraestrutura** para executar os casos de uso. Em um modelo de domínio anêmico, faz mais sentido semântico que a Application seja o centro funcional do sistema.  Esta decisão: 
- Mantém o Domain ainda mais limpo (apenas entidades e exceções)
- É apropriada para aplicações onde Services orquestram a lógica principal
- Facilita testes (mock dos Ports é direto)
- Evita complexidade desnecessária para um sistema sem agregados ou domain services complexos

**Resumo**:  Arquitetura limpa, testável e desacoplada, sem sacrificar produtividade com abstrações que não trazem valor real para este tipo de aplicação.

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
cd starian_backend</pre>
  </li>
  
  <li><strong>Execute com Maven</strong>
    <pre>mvn spring-boot:run</pre>
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

<h2>📋 Testando os Endpoints</h2>
<p>Para testar a aplicação eu recomendo fortemente que faça pelo próprio Swagger (link acima), pois tem a descrição dos endpoints e até alguns exemplos de body que podem facilitar o teste.</p>

<h2>📝 Próximos Passos</h2>

<ul>
  <li>[ ] Explorar integração com banco PostgreSQL para ambientes de produção</li>
  <li>[ ] Criar camada de segurança utilizando JWT</li>
</ul>
