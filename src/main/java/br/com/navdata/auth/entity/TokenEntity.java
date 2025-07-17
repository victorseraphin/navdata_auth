package br.com.navdata.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"WS_TOKEN\"")
public class TokenEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, unique = true, nullable = false)
    private String token;
    
    @Column(nullable = false)
    private String email;

    @Column(name = "inicio_vigencia", nullable = false)
    private LocalDateTime inicioVigencia;

    @Column(name = "fim_vigencia", nullable = false)
    private LocalDateTime fimVigencia;

    @Column(nullable = false)
    private boolean valid;
    
    @Column(name = "system_unit_id")
    private Integer systemUnitId;
    
    @Column(name = "system_user_id")
    private Integer systemUserId;

    @Column(name = "system_id")
    private Integer systemId;

    @Column(name = "system_name")
    private String systemName;
    
    @ManyToOne
    @JoinColumn(name = "refresh_token_id")
    private RefreshTokenEntity refreshToken;

    public TokenEntity() {}

    public TokenEntity(String token, String email, LocalDateTime inicioVigencia, LocalDateTime fimVigencia, boolean valid, Integer systemUnitId, Integer systemUserId, Integer systemId, String systemName, RefreshTokenEntity refreshToken) {
        this.token = token;
        this.email = email;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = fimVigencia;
        this.valid = valid;
        this.systemUnitId = systemUnitId;
        this.systemUserId = systemUserId;
        this.systemId = systemId;
        this.systemName = systemName;
        this.refreshToken = refreshToken;
    }

	public RefreshTokenEntity getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshTokenEntity refreshToken) {
		this.refreshToken = refreshToken;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getSystemUnitId() {
		return systemUnitId;
	}

	public void setSystemUnitId(Integer systemUnitId) {
		this.systemUnitId = systemUnitId;
	}

	public Integer getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(Integer systemUserId) {
		this.systemUserId = systemUserId;
	}

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	

	
	    
}
