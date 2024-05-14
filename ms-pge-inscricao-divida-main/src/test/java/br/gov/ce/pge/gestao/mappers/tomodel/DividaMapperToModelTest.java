package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.DividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDtoTest;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.enums.TipoProcesso;
import br.gov.ce.pge.gestao.service.FileService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith({MockitoExtension.class})
class DividaMapperToModelTest {

    @Mock
    private FileService fileService;

    @Test
    void teste_converter() {
        Divida model = DividaMapperToModel.converter(DividaRequestDtoTest.get_divida(),
                TipoReceitaResponseDtoTest.getTipoReceita(), OrigemDebitoResponseDtoTest.getOrigem(), this.fileService);

        assertEquals(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"), model.getIdOrigemDebito());
        assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), model.getIdTipoReceita());
        assertEquals("teste", model.getDisposicoesLegais());
        assertNull(model.getNaturezaFundamentacao());
        assertNull(model.getInexistenciaCausaSuspensivas());
        assertEquals(TipoDocumento.AIAM.name(), model.getTipoDocumento());
        assertEquals("1234567890", model.getNumeroDocumento());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataDocumento());
        assertEquals("123456789", model.getTermoRevelia());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataTermoRevelia());
        assertEquals("ABC123", model.getNumeroOficio());
        assertEquals(TipoProcesso.CONAT, model.getTipoProcesso());
        assertEquals("12345/2024", model.getNumeroProcessoAdministrativo());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataProcesso());
        assertEquals("6789", model.getNumeroAcordao());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataTransitoJulgado());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataConstituicaoDefinitivaCredito());
        assertNull(model.getPlacaVeiculo());
        assertNull(model.getNumeroChassi());
        assertEquals("XYZ789", model.getGuiaItcd());
        assertNull(model.getSequencialParcelamento());
        assertNull(model.getProtocoloJudicial());
        assertNull(model.getNomeAnexoProcessoDigitalizado());
    }

    @Test
    void test_converter_update_no_file_to_update() {
        DividaRequestDto request = new DividaRequestDto();

        Divida dividaMock = new Divida();
        dividaMock.setNomeAnexoProcessoDigitalizado("file.txt");

        TipoReceitaResponseDto tipoReceitaMock = mock(TipoReceitaResponseDto.class);
        OrigemDebitoResponseDto request2 = new OrigemDebitoResponseDto();
        request2.setNome("teste");

        FileService fileServiceMock = mock(FileService.class);

        Divida result = DividaMapperToModel.converterUpdate(request, dividaMock, tipoReceitaMock, request2, fileServiceMock);

        assertNull(result.getNomeAnexoProcessoDigitalizado());
    }

    @Test
    void test_converter_validations() {
        DividaRequestDto request = new DividaRequestDto();
        request.setTipoDocumento(TipoDocumento.AI);
        request.setDataDocumento(LocalDate.now());

        TipoReceitaResponseDto tipoReceitaMock = mock(TipoReceitaResponseDto.class);
        FileService fileServiceMock = mock(FileService.class);

        OrigemDebitoResponseDto request2 = new OrigemDebitoResponseDto();
        request2.setNome("teste");

        Divida result = DividaMapperToModel.converter(request, tipoReceitaMock, request2, fileServiceMock);

        assertNotNull(result);
        assertEquals(TipoDocumento.AI.name(), result.getTipoDocumento());
        assertEquals(LocalDate.now(), result.getDataDocumento());
    }

    @Test
    void test_Upload_file_update_file_not_changed() {
        DividaRequestDto request = new DividaRequestDto();
        Divida divida = new Divida();
        divida.setNomeAnexoProcessoDigitalizado("file.txt");

        FileService fileServiceMock = mock(FileService.class);

        DividaMapperToModel.uploadFileUpdate(request, divida, fileServiceMock);

        assertEquals("file.txt", divida.getNomeAnexoProcessoDigitalizado());
    }

    @Test
    void test_Upload_file_update_file_changed() {
        DividaRequestDto request = new DividaRequestDto();
        request.setArquivo(new byte[10]);
        Divida divida = new Divida();
        divida.setNomeAnexoProcessoDigitalizado("old_file.txt");

        FileService fileServiceMock = mock(FileService.class);

        DividaMapperToModel.uploadFileUpdate(request, divida, fileServiceMock);

        assertEquals("old_file.txt", divida.getNomeAnexoProcessoDigitalizado());
    }

    @Test
    void test_get_tipo_documento() {
        DividaRequestDto request = new DividaRequestDto();
        request.setTipoDocumento(TipoDocumento.AI);

        String result = DividaMapperToModel.getTipoDocumento(request);

        assertEquals(TipoDocumento.AI.name(), result);
    }

    @Test
    void test_verifica_data_transito_julgado() {
        DividaRequestDto request = new DividaRequestDto();
        request.setDataTransitoJulgado(LocalDate.now());

        assertDoesNotThrow(() -> DividaMapperToModel.verificaDataTransitoJulgado(request));
    }

    @Test
    void test_verifica_data_transito_julgado_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataTransitoJulgado(request));
    }

    @Test
    void test_verifica_tipo_documento() {
        DividaRequestDto request = new DividaRequestDto();
        request.setTipoDocumento(TipoDocumento.AI);
        request.setNumeroDocumento("1234567890");
        request.setTermoRevelia("123456789");
        request.setDataDocumento(LocalDate.now());
        request.setDataTermoRevelia(LocalDate.now());

        assertDoesNotThrow(() -> DividaMapperToModel.verificaTipoDocumento(request));
    }

    @Test
    void test_verifica_tipo_documento_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaTipoDocumento(request));
    }

    @Test
    void test_verifica_data_constituicao_definitiva_credito() {
        DividaRequestDto request = new DividaRequestDto();
        request.setDataConstituicaoDefinitivaCredito(LocalDate.now());

        assertDoesNotThrow(() -> DividaMapperToModel.verificaDataConstituicaoDefinitivaCredito(request));
    }

    @Test
    void test_verifica_data_constituicao_definitiva_credito_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataConstituicaoDefinitivaCredito(request));
    }

    @Test
    void test_verifica_data_constituicao_futura() {
        DividaRequestDto request = new DividaRequestDto();
        request.setDataConstituicaoDefinitivaCredito(LocalDate.now().plusDays(1));

        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataConstituicaoFutura(request));
    }

    @Test
    void test_verifica_data_transito_julgado_futura() {
        DividaRequestDto request = new DividaRequestDto();
        request.setDataTransitoJulgado(LocalDate.now().plusDays(1));

        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataTransitoJulgadoFutura(request));
    }

    @Test
    void test_verifica_termo_revelia() {
        DividaRequestDto request = new DividaRequestDto();
        request.setTermoRevelia("termo");
        request.setDataTermoRevelia(LocalDate.now());

        assertDoesNotThrow(() -> DividaMapperToModel.verificaTermoRevelia(request));
    }

    @Test
    void test_verifica_termo_revelia_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaTermoRevelia(request));
    }

    @Test
    void test_verifica_data_documento() {
        DividaRequestDto request = new DividaRequestDto();
        request.setDataDocumento(LocalDate.now());

        assertDoesNotThrow(() -> DividaMapperToModel.verificaDataDocumento(request));
    }

    @Test
    void test_verifica_data_documento_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataDocumento(request));
    }

    @Test
    void test_verifica_data_documento_futura() {
        DividaRequestDto request = new DividaRequestDto();
        request.setDataDocumento(LocalDate.now().plusDays(1));

        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataDocumentoFutura(request));
    }

    @Test
    void test_verifica_natureza_fundamentacao() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaNaturezaFundamentacao(request));
    }

    @Test
    void test_verifica_protocolo_judicial() {
        DividaRequestDto request = new DividaRequestDto();
        request.setProtocoloJudicial("protocolo");
        assertDoesNotThrow(() -> DividaMapperToModel.verificaProtocoloJudicial(request));
    }

    @Test
    void test_verifica_protocolo_judicial_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaProtocoloJudicial(request));
    }

    @Test
    void test_verifica_chassi() {
        DividaRequestDto request = new DividaRequestDto();
        request.setNumeroChassi("chassi");
        assertDoesNotThrow(() -> DividaMapperToModel.verificaChassi(request));
    }

    @Test
    void test_verifica_chassi_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaChassi(request));
    }

    @Test
    void test_verifica_placa_veiculo() {
        DividaRequestDto request = new DividaRequestDto();
        request.setPlacaVeiculo("placa");
        assertDoesNotThrow(() -> DividaMapperToModel.verificaPlacaVeiculo(request));
    }

    @Test
    void test_verifica_placa_veiculo_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaPlacaVeiculo(request));
    }

    @Test
    void test_verifica_guia_itcd() {
        DividaRequestDto request = new DividaRequestDto();
        request.setGuiaItcd("guia");

        assertDoesNotThrow(() -> DividaMapperToModel.verificaGuiaItcd(request));
    }

    @Test
    void test_verifica_guia_itcd_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaGuiaItcd(request));
    }

    @Test
    void test_verifica_sequencial_parcelamento() {
        DividaRequestDto request = new DividaRequestDto();
        request.setSequencialParcelamento("sequencial");

        assertDoesNotThrow(() -> DividaMapperToModel.verificaSequencialParcelamento(request));
    }

    @Test
    void test_verifica_sequencial_parcelamento_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaSequencialParcelamento(request));
    }

    @Test
    void test_verifica_data_termo_revelia() {
        DividaRequestDto request = new DividaRequestDto();
        request.setDataTermoRevelia(LocalDate.now());

        assertDoesNotThrow(() -> DividaMapperToModel.verificaDataTermoRevelia(request));
    }

    @Test
    void test_verifica_numero_documento() {
        DividaRequestDto request = new DividaRequestDto();
        request.setNumeroDocumento("numero");

        assertDoesNotThrow(() -> DividaMapperToModel.verificaNumeroDocumento(request));
    }

    @Test
    void test_verifica_numero_documento_exception() {
        DividaRequestDto request = new DividaRequestDto();
        assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaNumeroDocumento(request));
    }

    @Test
    void test_verifica_se_tipo_documento_is_ai() {
        DividaRequestDto request = new DividaRequestDto();
        request.setTipoDocumento(TipoDocumento.AI);
        request.setTermoRevelia("termo");
        request.setDataDocumento(LocalDate.now());
        request.setDataTermoRevelia(LocalDate.now());

        assertDoesNotThrow(() -> DividaMapperToModel.verificaSeTipoDocumentoIsAI(request));
    }
}
