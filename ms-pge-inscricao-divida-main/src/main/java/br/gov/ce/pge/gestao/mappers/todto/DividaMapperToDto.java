package br.gov.ce.pge.gestao.mappers.todto;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.response.DividaResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.enums.TipoDocumento;

public class DividaMapperToDto {

	private DividaMapperToDto() {
	}

	public static DividaResponseDto converter(Divida divida) {
		var dto = new DividaResponseDto();
		BeanUtils.copyProperties(divida, dto);
		dto.setTipoDocumento(getTipoDocumento(divida));
		return dto;
	}

	private static TipoDocumento getTipoDocumento(Divida divida) {
		return divida != null && divida.getTipoDocumento() != null? TipoDocumento.valueOf(divida.getTipoDocumento()) : null;
	}

}
