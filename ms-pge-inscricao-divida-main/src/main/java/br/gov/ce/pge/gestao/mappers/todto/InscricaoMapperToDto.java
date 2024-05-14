package br.gov.ce.pge.gestao.mappers.todto;

import java.util.List;
import java.util.Objects;

import br.gov.ce.pge.gestao.dto.response.InscricaoResponseDto;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.service.FileService;

public class InscricaoMapperToDto {
	
	private InscricaoMapperToDto() {}

	public static InscricaoResponseDto converter(Inscricao inscricao, FileService fileService) {
		var dto = new InscricaoResponseDto();
		dto.setId(inscricao.getId());
		dto.setDevedor(Objects.isNull(inscricao.getDevedor())? null : DevedorMapperToDto.converter(inscricao.getDevedor()));
		dto.setDivida(Objects.isNull(inscricao.getDivida())? null : DividaMapperToDto.converter(inscricao.getDivida(), fileService));
		dto.setCorresponsaveis(isNullOrVazio(inscricao.getCorresponsaveis()) ? null : CorresponsavelMapperToDto.converterList(inscricao.getCorresponsaveis()));
		dto.setDebitos(isNullOrVazio(inscricao.getDebitos()) ? null : DebitoMapperToDto.converterList(inscricao.getDebitos()));
		dto.setStatus(inscricao.getStatus());
		return dto;
	}
	
	private static Boolean isNullOrVazio(List<?> lista) {
		return Objects.isNull(lista) || lista.isEmpty();
	}
	
}
