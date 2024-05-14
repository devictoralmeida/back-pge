package br.gov.ce.pge.gestao.mappers.todto;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.DividaResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.service.FileService;

public class DividaMapperToDto {
	
	private static final String BUCKET_DIVIDA = "bucket_inscricao_divida";

	private DividaMapperToDto() {
	}

	public static DividaResponseDto converter(Divida divida, FileService fileService) {
		var dto = new DividaResponseDto();
		BeanUtils.copyProperties(divida, dto);
		dto.setTipoDocumento(getTipoDocumento(divida));
		dto.setArquivo(fileService.download(divida.getNomeAnexoProcessoDigitalizado(), BUCKET_DIVIDA));
		return dto;
	}

	private static TipoDocumento getTipoDocumento(Divida divida) {
		return divida != null && divida.getTipoDocumento() != null? TipoDocumento.valueOf(divida.getTipoDocumento()) : null;
	}

}
