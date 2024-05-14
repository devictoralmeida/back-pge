package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.CorresponsavelRequestDto;
import br.gov.ce.pge.gestao.dto.request.CorresponsavelRequestDtoTest;
import br.gov.ce.pge.gestao.entity.Corresponsavel;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.entity.InscricaoTest;
import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CorresponsavelMapperToModelTest {

    @Test
    void teste_converter() {

        Corresponsavel model = CorresponsavelMapperToModel.converter(CorresponsavelRequestDtoTest.get_corresponsavel_pessoa_fisica(), InscricaoTest.get_inscricao_completa());
        assertEquals("Kamila Lima", model.getNomeRazaoSocial());
        assertEquals(TipoPessoa.PESSOA_FISICA, model.getTipoPessoa());
        assertEquals("03174180332", model.getDocumento());
        assertNull(model.getCgf());
        assertEquals(TipoContato.TELEFONE_CELULAR, model.getTipoContato());
        assertEquals("85999343912", model.getContato());
        assertEquals("kamila@gmail.com", model.getEmail());
        assertEquals("60360450", model.getCep());
        assertEquals("Travessa congo", model.getLogradouro());
        assertEquals("42", model.getNumero());
        assertEquals("Antonio Bezerra", model.getBairro());
        assertNull(model.getComplemento());
        assertNull(model.getDistrito());
        assertEquals("Fortaleza", model.getMunicipio());
        assertEquals("CE", model.getUf());
    }

    @Test
    void teste_converter_lista() {

        List<Corresponsavel> lista = CorresponsavelMapperToModel.converterList(CorresponsavelRequestDtoTest.get_list_corresponsaveis(), InscricaoTest.get_inscricao_completa());
        assertEquals("Osvaldo e Julio Marketing ME", lista.get(1).getNomeRazaoSocial());
        assertEquals(TipoPessoa.PESSOA_JURIDICA, lista.get(1).getTipoPessoa());
        assertEquals("86411461000172", lista.get(1).getDocumento());
        assertEquals("075035251", lista.get(1).getCgf());
        assertEquals(TipoContato.WHATSAPP, lista.get(1).getTipoContato());
        assertEquals("85994677445", lista.get(1).getContato());
        assertEquals("seguranca@osvaldoejuliomarketingme.com.br", lista.get(1).getEmail());
        assertEquals("60360450", lista.get(1).getCep());
        assertEquals("Travessa congo", lista.get(1).getLogradouro());
        assertEquals("42", lista.get(1).getNumero());
        assertEquals("Antonio Bezerra", lista.get(1).getBairro());
        assertNull(lista.get(1).getComplemento());
        assertNull(lista.get(1).getDistrito());
        assertEquals("Fortaleza", lista.get(1).getMunicipio());
        assertEquals("CE", lista.get(1).getUf());
    }

    @Test
    void testConverterListUpdate() {
        List<CorresponsavelRequestDto> requestList = new ArrayList<>();
        CorresponsavelRequestDto dto1 = new CorresponsavelRequestDto();
        dto1.setTipoPessoa(TipoPessoa.PESSOA_FISICA);
        dto1.setNomeRazaoSocial("Fulano");
        CorresponsavelRequestDto dto2 = new CorresponsavelRequestDto();
        dto2.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
        dto2.setNomeRazaoSocial("Empresa");
        dto2.setCgf("123456789");

        requestList.add(dto1);
        requestList.add(dto2);

        Inscricao inscricaoMock = mock(Inscricao.class);

        List<Corresponsavel> corresponsaveis = CorresponsavelMapperToModel.converterListUpdate(requestList, inscricaoMock);

        assertEquals(2, corresponsaveis.size());
        assertEquals("Fulano", corresponsaveis.get(0).getNomeRazaoSocial());
        assertEquals("Empresa", corresponsaveis.get(1).getNomeRazaoSocial());
        assertEquals("123456789", corresponsaveis.get(1).getCgf());
    }

    @Test
    void testConverterListUpdate_NullList() {
        Inscricao inscricaoMock = mock(Inscricao.class);

        List<Corresponsavel> corresponsaveis = CorresponsavelMapperToModel.converterListUpdate(null, inscricaoMock);

        assertEquals(0, corresponsaveis.size());
    }

    @Test
    void testConverter_VerificaTipoPessoa_Exception() {
        CorresponsavelRequestDto dto = new CorresponsavelRequestDto();
        dto.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
        dto.setNomeRazaoSocial("Empresa");

        Inscricao inscricaoMock = mock(Inscricao.class);

        assertThrows(NegocioException.class, () -> CorresponsavelMapperToModel.converter(dto, inscricaoMock));
    }

}
