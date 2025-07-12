package br.com.navdata.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "WS_TOKEN")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private String username;

    @Column(name = "inicio_vigencia", nullable = false)
    private LocalDateTime inicioVigencia;

    @Column(name = "fim_vigencia", nullable = false)
    private LocalDateTime fimVigencia;

    @Column(nullable = false)
    private boolean valid;

    public TokenEntity() {}

    public TokenEntity(String token, String username, LocalDateTime inicioVigencia, LocalDateTime fimVigencia, boolean valid) {
        this.token = token;
        this.username = username;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = fimVigencia;
        this.valid = valid;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(LocalDateTime inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public LocalDateTime getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(LocalDateTime fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

    
}
