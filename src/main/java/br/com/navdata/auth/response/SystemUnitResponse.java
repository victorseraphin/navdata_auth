package br.com.navdata.auth.response;

import java.util.List;

public class SystemUnitResponse {
	
	private Integer id;
    private String name;
    private String documento;
    private String matricula;
    private String localizacao;
    private String inscricao;
	private List<SystemResponse> systems;

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

	public List<SystemResponse> getSystems() {
		return systems;
	}

	public void setSystems(List<SystemResponse> systems) {
		this.systems = systems;
	}
	
    
}
