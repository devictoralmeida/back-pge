package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PaginacaoResponseDtoTest {

	public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getPaginacaoHistoricoPerfil() {
		return PaginacaoResponseDto.fromResultado(List.of(HistoricoAtualizacaoResponseDtoTest.getHistoricoAtualizacaoPerfilAcesso()),1,1,1);
	}
	
	public static PaginacaoResponseDto<List<PerfilAcessoFilterResponseDto>> getPaginacaoFilterPerfil() {
		return PaginacaoResponseDto.fromResultado(List.of(PerfilAcessoFilterResponseDtoTest.getPerfilAcessoFilter()),1,1,1);
	}
	
	public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getPaginacaoHistoricoPermissao() {
		return PaginacaoResponseDto.fromResultado(List.of(HistoricoAtualizacaoResponseDtoTest.getHistoricoAtualizacaoPermissao()),1,1,1);
	}

	public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getPaginacaoHistoricoModulo() {
		return PaginacaoResponseDto.fromResultado(List.of(HistoricoAtualizacaoResponseDtoTest.getHistoricoAtualizacaoModulo()),1,1,1);
	}
	
	public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getPaginacaoHistoricoSistema() {
		return PaginacaoResponseDto.fromResultado(List.of(HistoricoAtualizacaoResponseDtoTest.getHistoricoAtualizacaoSistema()),1,1,1);
	}

    public static PaginacaoResponseDto<List<UsuarioFilterResponseDto>> getPaginacaoFilterUsuario() {
        return PaginacaoResponseDto.fromResultado(List.of(UsuarioFilterResponseDtoTest.getUsuarioFilter()),1,1,1);
    }

    public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getPaginacaoHistoricoUsuario() {
        return PaginacaoResponseDto.fromResultado(List.of(HistoricoAtualizacaoResponseDtoTest.getHistoricoAtualizacaoUsuario()),1,1,1);
    }

    public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getPaginacaoHistoricoTermo() {
        return PaginacaoResponseDto.fromResultado(List.of(HistoricoAtualizacaoResponseDtoTest.getHistoricoAtualizacaoTermo()),1,1,1);
    }
	
	@Test
    public void test_from_resultado() {
        String resultList = "Sample Result List";
        PaginacaoResponseDto<String> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(resultList, 50, 3, 2);

        assertEquals(50, paginacaoResponseDto.getTotalRegistros());
        assertEquals(3, paginacaoResponseDto.getTotalPaginas());
        assertEquals(2, paginacaoResponseDto.getPaginaAtual());
        assertEquals(resultList, paginacaoResponseDto.getList());
    }

    @Test
    public void test_set_list() {
        PaginacaoResponseDto<Boolean> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(true, 1, 1, 1);
        assertEquals(true, paginacaoResponseDto.getList());
    }

    @Test
    public void test_setters_and_getters() {
        
        PaginacaoResponseDto<Integer> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(42, 100, 5, 2);
        
        assertEquals(100, paginacaoResponseDto.getTotalRegistros());
        assertEquals(5, paginacaoResponseDto.getTotalPaginas());
        assertEquals(2, paginacaoResponseDto.getPaginaAtual());
        assertEquals(42, paginacaoResponseDto.getList());
    }

    @Test
    public void test_null_list() {
        PaginacaoResponseDto<Object> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(null, 0, 1, 1);

        assertNull(paginacaoResponseDto.getList());
        assertEquals(0, paginacaoResponseDto.getTotalRegistros());
        assertEquals(1, paginacaoResponseDto.getTotalPaginas());
        assertEquals(1, paginacaoResponseDto.getPaginaAtual());
    }

}
