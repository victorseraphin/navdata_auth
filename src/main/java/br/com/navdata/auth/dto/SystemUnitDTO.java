package br.com.navdata.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SystemUnitDTO {
	
	private Integer id;
	
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80)
    private String name;

    @NotBlank(message = "O documento é obrigatório")
    @Size(max = 20)
    private String documento;

    @NotBlank(message = "A matrícula é obrigatória")
    @Size(max = 30)
    private String matricula;

    @NotBlank(message = "A localização é obrigatória")
    @Size(max = 100)
    private String localizacao;

    @NotBlank(message = "A inscrição é obrigatória")
    @Size(max = 20)
    private String inscricao;

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
    
}
