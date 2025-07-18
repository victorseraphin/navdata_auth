package br.com.navdata.auth.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"system_group\"")
public class SystemGroupEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id")
    private SystemEntity system;
    
    @ManyToMany(mappedBy = "systemGroups")
    private Set<SystemProgramEntity> systemPrograms = new HashSet<>();

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

	public SystemEntity getSystem() {
		return system;
	}

	public void setSystem(SystemEntity system) {
		this.system = system;
	}

	public Set<SystemProgramEntity> getSystemPrograms() {
		return systemPrograms;
	}

	public void setSystemPrograms(Set<SystemProgramEntity> systemPrograms) {
		this.systemPrograms = systemPrograms;
	}
    
    
    
}
