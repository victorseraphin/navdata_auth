package br.com.navdata.auth.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"system\"")
public class SystemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	// Cada sistema pertence a uma unidade (muitos sistemas pra 1 unidade)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_unit_id")
    @JsonBackReference
    private SystemUnitEntity systemUnit;
	
    // Um sistema tem vários programas
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    private List<SystemProgramEntity> programs;
    
    // Um sistema tem vários grupos
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    private List<SystemGroupEntity> groups;

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

	public SystemUnitEntity getSystemUnit() {
		return systemUnit;
	}

	public void setSystemUnit(SystemUnitEntity systemUnit) {
		this.systemUnit = systemUnit;
	}

	public List<SystemProgramEntity> getPrograms() {
		return programs;
	}

	public void setPrograms(List<SystemProgramEntity> programs) {
		this.programs = programs;
	}

	public List<SystemGroupEntity> getGroups() {
		return groups;
	}

	public void setGroups(List<SystemGroupEntity> groups) {
		this.groups = groups;
	}




	

}