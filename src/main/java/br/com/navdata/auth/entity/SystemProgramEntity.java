package br.com.navdata.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"system_program\"")
public class SystemProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String path;

    private String method;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id")
    private SystemEntity system;
    
    /*@ManyToMany
    @JoinTable(
        name = "system_program_system_group",
        joinColumns = @JoinColumn(name = "system_program_id"),
        inverseJoinColumns = @JoinColumn(name = "system_group_id")
    )
    private Set<SystemGroupEntity> systemGroups = new HashSet<>();*/
    
    @ManyToMany(mappedBy = "systemPrograms")
    private Set<SystemGroupEntity> systemGroups = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_unit_id")
    private SystemUnitEntity systemUnit;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
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

	public SystemEntity getSystem() {
		return system;
	}

	public void setSystem(SystemEntity system) {
		this.system = system;
	}

	public Set<SystemGroupEntity> getSystemGroups() {
		return systemGroups;
	}

	public void setSystemGroups(Set<SystemGroupEntity> systemGroups) {
		this.systemGroups = systemGroups;
	}

	public SystemUnitEntity getSystemUnit() {
		return systemUnit;
	}

	public void setSystemUnit(SystemUnitEntity systemUnit) {
		this.systemUnit = systemUnit;
	}



    
}
