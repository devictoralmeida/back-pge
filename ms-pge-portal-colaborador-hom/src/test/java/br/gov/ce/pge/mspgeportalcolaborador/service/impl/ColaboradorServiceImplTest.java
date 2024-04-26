package br.gov.ce.pge.mspgeportalcolaborador.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.ce.pge.mspgeportalcolaborador.entity.ColaboradorTest;
import br.gov.ce.pge.mspgeportalcolaborador.repository.ColaboradorRepository;
import br.gov.ce.pge.mspgeportalcolaborador.shared.exception.ColaboradorNotFoundException;

@ExtendWith({ MockitoExtension.class })
public class ColaboradorServiceImplTest {

	@Mock
	private ColaboradorRepository repository;

	@InjectMocks
	@Autowired
	private ColaboradorServiceImpl service;

	@Test
	void test_consulta_cpf_sucesso() {
		when(repository.findByCpf(any())).thenReturn(ColaboradorTest.getColaborador());

		var model = service.findByCpf("05459813360");

		assertNotNull(model);
		assertEquals("João", model.getNome());
		assertEquals("ti", model.getArea());
		assertEquals("05459813360", model.getCpf());

		verify(repository, times(1)).findByCpf(any());
	}

	@Test
	void test_consulta_cpf_erro() {
		String cpf = "12345678900";

		when(repository.findByCpf(anyString())).thenReturn(null);

		ColaboradorNotFoundException excecao = assertThrows(ColaboradorNotFoundException.class, () -> {
			service.findByCpf(cpf);
		});

    
		assertEquals("Colaborador não encontrado!", excecao.getMessage());
	}

	@Test
    void teste_pesquisa_lista_cpf() {
        when(repository.findByCpfs(any())).thenReturn(ColaboradorTest.getListColaborador());

        var listCpfs = service.findByListCpfs(Arrays.asList("05459813360"));

        assertNotNull(listCpfs);
        assertEquals("João", listCpfs.get(0).getNome());
        assertEquals("ti", listCpfs.get(0).getArea());
        assertEquals("05459813360", listCpfs.get(0).getCpf());

        verify(repository, times(1)).findByCpfs(any());
    }

}
