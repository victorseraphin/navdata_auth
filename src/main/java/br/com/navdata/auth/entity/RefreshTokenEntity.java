package br.com.navdata.auth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "ws_refresh_tokens")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @Column(name = "system_unit_id")
    private Integer systemUnitId;

    @Column(name = "system_user_id")
    private Integer systemUserId;

    @Column(name = "system_id")
    private Integer systemId;

    @Column(name = "system_name")
    private String systemName;

    @Column(nullable = false)
    private boolean valid;
    
    @OneToMany(mappedBy = "refreshToken", cascade = CascadeType.ALL)
    private List<TokenEntity> wsTokens;

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<TokenEntity> getWsTokens() {
		return wsTokens;
	}

	public void setWsTokens(List<TokenEntity> wsTokens) {
		this.wsTokens = wsTokens;
	}
    
    
}
