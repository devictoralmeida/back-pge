package br.gov.ce.pge.gestao.services;

import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.InscricaoTest;
import br.gov.ce.pge.gestao.repository.InscricaoRepository;
import br.gov.ce.pge.gestao.service.FileService;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.service.impl.InscricaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceImplTest {
    @Mock
    private InscricaoRepository repository;

    @Mock
    private OrigemDebitoService origemDebitoService;

    @Mock
    private TipoReceitaService tipoReceitaService;

    @Mock
    @Qualifier("FileServiceGCP")
    private FileService fileService;

    @Mock
    private RegistroLivroService registroLivroService;

    @InjectMocks
    private InscricaoServiceImpl service;

    @Test
    void test_save() throws Exception {
        InscricaoRequestDto request = InscricaoRequestDtoTest.get_inscricao_completa();

        OrigemDebitoResponseDto origemDebitoResponseDto = OrigemDebitoResponseDtoTest.getOrigem();
        TipoReceitaResponseDto tipoReceitaResponseDto = TipoReceitaResponseDtoTest.getTipoReceita();

        when(origemDebitoService.findById(request.getDivida().getIdOrigemDebito())).thenReturn(origemDebitoResponseDto);
        when(tipoReceitaService.findById(request.getDivida().getIdTipoReceita())).thenReturn(tipoReceitaResponseDto);

        service.save(request);

        verify(repository, times(1)).save(any(Inscricao.class));
        verify(registroLivroService, times(1)).registrar(any(Inscricao.class));
    }

    @Test
    void test_find_by_id_model() {
        UUID id = UUID.fromString("c8bf3eff-2d65-46f7-bb6a-8f4c93be7886");
        when(repository.findById(id)).thenReturn(Optional.of(InscricaoTest.get_inscricao_completa()));
        Inscricao result = service.findByIdModel(id);
        assertNotNull(result);
    }

    @Test
    void test_find_by_id() {
        UUID id = UUID.fromString("c8bf3eff-2d65-46f7-bb6a-8f4c93be7886");
        when(repository.findById(id)).thenReturn(Optional.of(InscricaoTest.get_inscricao_completa()));
        InscricaoResponseDto result = service.findById(id);
        assertNotNull(result);
    }

    @Test
    void test_update() {
        when(repository.findById(any())).thenReturn(Optional.of(InscricaoTest.get_inscricao_completa()));
        when(origemDebitoService.findById(any())).thenReturn(OrigemDebitoResponseDtoTest.getOrigem());
        when(tipoReceitaService.findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceita());

        UUID id = UUID.fromString("c8bf3eff-2d65-46f7-bb6a-8f4c93be7886");
        service.update(id, InscricaoRequestDtoTest.get_inscricao_completa());
        verify(repository, times(1)).save(any(Inscricao.class));
    }
}
