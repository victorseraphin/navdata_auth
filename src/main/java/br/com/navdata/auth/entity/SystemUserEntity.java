package br.com.navdata.auth.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_user")
public class SystemUserEntity {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "razao", length = 200)
    private String razao;

    @Column(name = "doc", length = 20)
    private String doc;

    @Column(name = "fone", length = 20)
    private String fone;

    @Column(name = "login", length = 20)
    private String login;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "frontpage_id")
    private Integer frontpageId;

    @Column(name = "facebook_id", length = 255)
    private String facebookId;

    @Column(name = "google_id", length = 255)
    private String googleId;

    @Column(name = "active", nullable = false, length = 1)
    private String active = "Y";

    @Column(name = "remember_token", length = 100)
    private String rememberToken;
    
    @Column(name = "is_master", nullable = false)
    private boolean isMaster = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

	@ManyToMany
	@JoinTable(name = "system_unit_system_user", // nome da tabela de associação
			joinColumns = @JoinColumn(name = "system_user_id"), 
			inverseJoinColumns = @JoinColumn(name = "system_unit_id"))
	private List<SystemUnitEntity> systemUnit;
    
    @ManyToMany
    @JoinTable(name = "system_user_system",
        joinColumns = @JoinColumn(name = "system_user_id"),
        inverseJoinColumns = @JoinColumn(name = "system_id"))
    private List<SystemEntity> systems;

    @ManyToMany
    @JoinTable(name = "system_user_system_group",
        joinColumns = @JoinColumn(name = "system_user_id"),
        inverseJoinColumns = @JoinColumn(name = "system_group_id"))
    private List<SystemGroupEntity> groups;

    @ManyToMany
    @JoinTable(name = "system_user_system_program",
        joinColumns = @JoinColumn(name = "system_user_id"),
        inverseJoinColumns = @JoinColumn(name = "system_program_id"))
    private List<SystemProgramEntity> programs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getFrontpageId() {
		return frontpageId;
	}

	public void setFrontpageId(Integer frontpageId) {
		this.frontpageId = frontpageId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRememberToken() {
		return rememberToken;
	}

	public void setRememberToken(String rememberToken) {
		this.rememberToken = rememberToken;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public List<SystemEntity> getSystems() {
		return systems;
	}

	public void setSystems(List<SystemEntity> systems) {
		this.systems = systems;
	}

	public List<SystemGroupEntity> getGroups() {
		return groups;
	}

	public void setGroups(List<SystemGroupEntity> groups) {
		this.groups = groups;
	}

	public List<SystemProgramEntity> getPrograms() {
		return programs;
	}

	public void setPrograms(List<SystemProgramEntity> programs) {
		this.programs = programs;
	}

	public boolean getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	public List<SystemUnitEntity> getSystemUnit() {
		return systemUnit;
	}

	public void setSystemUnit(List<SystemUnitEntity> systemUnit) {
		this.systemUnit = systemUnit;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	
}
