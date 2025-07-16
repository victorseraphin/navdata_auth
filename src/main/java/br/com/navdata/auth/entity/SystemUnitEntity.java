package br.com.navdata.auth.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "system_unit")
public class SystemUnitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 80, nullable = false)
    private String name;

    @Column(name = "documento", length = 20, nullable = false)
    private String documento;

    @Column(name = "matricula", length = 30, nullable = false)
    private String matricula;

    @Column(name = "localizacao", length = 100, nullable = false)
    private String localizacao;

    @Column(name = "inscricao", length = 20, nullable = false)
    private String inscricao;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @OneToMany
    @JoinTable(
        name = "system_unit_system",
        joinColumns = @JoinColumn(name = "system_unit_id"),
        inverseJoinColumns = @JoinColumn(name = "system_id")
    )
    private List<SystemEntity> systems;
    
    @OneToMany
    @JoinTable(
        name = "system_unit_system_user",
        joinColumns = @JoinColumn(name = "system_unit_id"),
        inverseJoinColumns = @JoinColumn(name = "system_user_id")
    )
    private List<SystemUserEntity> systemsUsers;

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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
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
	
	
    
}
