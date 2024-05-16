package br.gov.ce.pge.gestao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDtoTest;

@ExtendWith({ MockitoExtension.class })
class TermoCondicaoDaoTest {
	
	@Mock
    private transient SqlSession sqlSession;
	
	@InjectMocks
	@Autowired
	private TermoCondicaoDao dao;

	@Test
	void teste_findBy_Sistema() {
		when(sqlSession.selectList(eq("TermoCondicaoDao.findBySistema"))).thenReturn(List.of(TermoCondicaoSistemaResponseDtoTest.getTermoCondicaoSistemaResponseDto()));
		
		List<TermoCondicaoSistemaResponseDto> termos = this.dao.findBySistema();
		
		assertEquals(1, termos.size());
		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termos.get(0).getId());
		assertEquals("Portal da Dívida Ativa", termos.get(0).getNomeSistema());
		assertEquals("0.1", termos.get(0).getVersao());
		assertEquals("anônimo", termos.get(0).getNomeUsuario());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termos.get(0).getDataCriacao());
	
	}

}
