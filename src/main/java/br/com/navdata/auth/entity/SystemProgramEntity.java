package br.com.navdata.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"system_program\"")
public class SystemProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String path;

    private String method;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "system_id")
    private SystemEntity systems;

    @ManyToMany
    @JoinTable(
        name = "system_group_system_program",
        joinColumns = @JoinColumn(name = "system_group_id"),
        inverseJoinColumns = @JoinColumn(name = "system_program_id")
    )
    private List<SystemProgramEntity> programs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public SystemEntity getSystems() {
		return systems;
	}

	public void setSystems(SystemEntity systems) {
		this.systems = systems;
	}

	public List<SystemProgramEntity> getPrograms() {
		return programs;
	}

	public void setPrograms(List<SystemProgramEntity> programs) {
		this.programs = programs;
	}
    
}
