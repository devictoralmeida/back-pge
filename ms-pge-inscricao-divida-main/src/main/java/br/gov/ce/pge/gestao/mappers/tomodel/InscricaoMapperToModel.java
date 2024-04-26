package br.gov.ce.pge.gestao.mappers.tomodel;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.Corresponsavel;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Devedor;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.service.FileService;

public class InscricaoMapperToModel {
	
	private InscricaoMapperToModel() {}

	public static Inscricao converter(InscricaoRequestDto request, TipoReceitaResponseDto tipoReceita, OrigemDebitoResponseDto origemDebito, FileService fileService) {
		var inscricao = new Inscricao();
		inscricao.setDevedor(Objects.isNull(request.getDevedor())? null : DevedorMapperToModel.converter(request.getDevedor()));
		inscricao.setDivida(Objects.isNull(request.getDivida())? null : DividaMapperToModel.converter(request.getDivida(), tipoReceita, origemDebito, fileService));
		inscricao.setCorresponsaveis(isNullOrVazio(request.getCorresponsaveis()) ? null : CorresponsavelMapperToModel.converterList(request.getCorresponsaveis(), inscricao));
		inscricao.setDebitos(isNullOrVazio(request.getDebitos()) ? null : DebitoMapperToModel.converterList(request.getDebitos(), inscricao));
		inscricao.setStatus(request.getStatus());
		return inscricao;
	}
	
	private static Boolean isNullOrVazio(List<?> lista) {
		return Objects.isNull(lista) || lista.isEmpty();
	}

	public static Inscricao converterUpdate(InscricaoRequestDto request, Inscricao inscricao,
			TipoReceitaResponseDto tipoReceita, OrigemDebitoResponseDto origemDebito, FileService fileService) {
		
		BeanUtils.copyProperties(request, inscricao, "id", "dataCriacao", "nomeUsuarioCadastro");
		
		Devedor devedor = DevedorMapperToModel.converter(request.getDevedor());
		Divida divida = DividaMapperToModel.converterUpdate(request.getDivida(), inscricao.getDivida(), tipoReceita, origemDebito, fileService);
		List<Corresponsavel> corresponsaveis = CorresponsavelMapperToModel.converterListUpdate(request.getCorresponsaveis(), inscricao);
		List<Debito> debitos=  DebitoMapperToModel.converterListUpdate(request.getDebitos(), inscricao);
		
		inscricao.setDevedor(devedor);
		inscricao.setDivida(divida);
		inscricao.setCorresponsaveis(corresponsaveis);
		inscricao.setDebitos(debitos);
		
		return inscricao;
	}
	
}
