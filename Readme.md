# Projeto Fábrica de Software FSO 2024 Turma A
![The Clean Architecture](diagramas/png/CleanArchitecture.jpg "CleanArchitecture")

## Classes de Entidade
![Diagrama de Classes](diagramas/png/entidades.png "Diagrama")

# PROJETO projfso2024a
## Validação de campos
- Alterar o arquivo pom.xml para incluir a dependencia
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
```

- Na classe de entidade incluir as anotações necessárias para fazer a validação
```
    @NotBlank(message = "Campo nome não pode ser em branco")
    private String nome;
```

## Anotações para mapeamento objeto relacional
O @Column permite configurar o tamanho e a possibilidade de obrigar o usuário a informar o campo
```
@Column(length = 1000, nullable = false)
private String nome;
```
O @Temporal é utilizado para tipar corretamente o campo de data no banco de dados
```
@Temporal(TemporalType.DATE)
private Date dataNascimento;
```

## Classe Startup
- Esta classe foi criada para ser executada na inicialização do software para criar registros padrão no banco de dados
```
@Component
public class Startup {
    @Autowired
    private ClienteService service;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        var cliente1 = new Cliente();
        cliente1.setNome("Zezinho");
        cliente1.setEndereco("Rua lalalalla 100");
        cliente1.setDataNascimento(new Date(2024,04,17));
        service.save(cliente1);
    }
}
```
## Classe Repository
- Gera o código necessário para interação com o banco de dados no formato CRUD
```
@Repository
public interface ClienteRepository 
        extends JpaRepository<Cliente,Long>{
    
}
```
## Classe Service
- Utilizada para implementação da lógica de negócio da aplicação 
```
@Service
public class ClienteServiceImpl 
    implements ClienteService {
    
    @Autowired
    private ClienteRepository repository;
    
    @Override
    public void save(Cliente cliente) {
        repository.save(cliente);
    }

    @Override
    public Cliente getById(long id) {
        return repository.getById(id);
    }

    @Override
    public List<Cliente> getAll() {
        return repository.findAll();
    }
    
}
```


## Implementação do JWT no projeto API
1) alterar o arquivo pom.xml para inclur a dependência do Spring Security
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
</dependency>
```
2) Criar a entidade Usuário
```
package br.univille.projapifso2024a.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String usuario;
    private String senha;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
```
3) Criar o repositório do Usuário
```
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.univille.projapifso2024a.entity.Usuario;

@Repository
public interface UsuarioRepository 
    extends JpaRepository<Usuario,Long>{
        /*select * from usuario wher usuario.usuario = 'zezinho' */
        Optional<Usuario> findByUsuario(String usuario);
    
}
```
4) Criar o serviço do usuário
```
package br.univille.projapifso2024a.service;

import java.util.Optional;
import br.univille.projapifso2024a.entity.Usuario;

public interface UsuarioService {
    void save(Usuario usuario);
    Optional<Usuario> findByUsuario(String usuario);
}
```
```
package br.univille.projapifso2024a.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.univille.projapifso2024a.entity.Usuario;
import br.univille.projapifso2024a.repository.UsuarioRepository;
import br.univille.projapifso2024a.service.UsuarioService;
import org.springframework.security.core.userdetails.User;

@Service
public class UsuarioServiceImpl implements UserDetailsService,UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void save(Usuario usuario) {
        repository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByUsuario(String usuario) {
        return repository.findByUsuario(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = repository.findByUsuario(username);
        if(usuario.isPresent()){
            var usuarioEncontrado = usuario.get();
            return new User(usuarioEncontrado.getUsuario(),
                            usuarioEncontrado.getSenha(),
                            new ArrayList<>());
        }
        return null;
    }
    
}

```
5) Alterar a classe startup para cadastrar um novo usuario padrão
```
@Component
public class Startup {
    @Autowired
    private ClienteService service;

    @Autowired
    private UsuarioService serviceUsuario;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        var cliente1 = new Cliente();
        cliente1.setNome("Zezinho");
        cliente1.setEndereco("Rua lalalalla 100");
        cliente1.setDataNascimento(new Date(2024,04,17));
        service.save(cliente1);

        var usuario = new Usuario();
        usuario.setUsuario("admin");
        usuario.setSenha("admin");
        serviceUsuario.save(usuario);
    }
}
```
6) Implementar a classe de geração e validação dos tokens JWT JWTUtil
```
package br.univille.projapifso2024a.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
    @Value("${fsoapi2024.secretkey}")
    private String SECRET_KEY;
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken (String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

```
7) Alterar o arquivo application.properties para incluir a chave secreta de geração dos JWTs
```
spring.application.name=projapifso2024a
server.port=8081

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/bancodedados
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:file:~/dbapi;DB_CLOSE_DELAY=-1;MODE=MYSQL
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop


fsoapi2024.secretkey=j325895j4985j234095jj234095j23495395j4985j234095j
```
8) Implementar a classe JWTRequestFilter para incluir o filtro das requisições que obriga ao cliente passar o JWT
```
package br.univille.projapifso2024a.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTRequestFilter extends OncePerRequestFilter{
    @Autowired
    private JWTUtil serviceJWT;
    @Autowired
    private UserDetailsService serviceUser;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        var authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authHeader != null &&
            authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = serviceJWT.extractUserName(token);
        }

        if(username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null){
            var userDetail = serviceUser.loadUserByUsername(username);
            if(serviceJWT.validateToken(token, userDetail)){
                UsernamePasswordAuthenticationToken ut = 
                    new UsernamePasswordAuthenticationToken(username, null, userDetail.getAuthorities());
                
                ut.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(ut);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
```

8) Implementar a classe SecurityConfigurer para aplicar o filtro de requests
```
package br.univille.projapifso2024a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfigurer {

    @Autowired
    private JWTRequestFilter jwtAuthFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors  -> cors.disable())
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .authorizeHttpRequests(auth -> {
                        auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/api/v1/auth/signin").permitAll()
                        .anyRequest().authenticated().and()
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                })
                
                                
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return http.build();
    }

}
```

9) Criar o controlador com o método de autenticação
```
package br.univille.projapifso2024a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.projapifso2024a.entity.Usuario;
import br.univille.projapifso2024a.security.JWTUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerAPI {
    @Autowired
    private UserDetailsService service;
    @Autowired
    private JWTUtil serviceJWT;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody Usuario usuario){
        var userDetail = service.loadUserByUsername(usuario.getUsuario());
        if(userDetail.getPassword().equals(usuario.getSenha())){
            var token = serviceJWT.generateToken(userDetail);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }
    
}
```

10) Chamada do método de autenticação no testeapi.rest
```
# @name signin
POST http://localhost:8081/api/v1/auth/signin
Content-Type: application/json

{
    "usuario": "admin",
    "senha": "admin"
}

###
@token = {{signin.response.body.*}}
```

11) Alterar os métodos passando o token em cada requisição
```
### POST /api/v1/cidade
POST http://localhost:8081/api/v1/cidades
Content-Type: application/json
Authorization: Bearer {{token}}

{
    "nome": "Joinville",
    "estado": "Santa Catarina"
}

```