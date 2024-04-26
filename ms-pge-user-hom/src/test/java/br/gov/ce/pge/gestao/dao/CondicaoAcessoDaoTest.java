package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.response.AuditoriaModuloDtoTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class CondicaoAcessoDaoTest {
    
	@InjectMocks
    @Autowired
    private CondicaoAcessoDao dao;

    @Mock
    private transient SqlSession sqlSession;

    @Test
    public void contar_historico() {

        UUID permissaoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectOne(eq("CondicaoAcessoDao.countHistorysUpdates"), anyMap())).thenReturn(1);

        int count = dao.countHistorysUpdates(permissaoId);

        assertNotNull(count);
        assertEquals(1, count);

    }

    @Test
    public void historico() throws JsonProcessingException {

        UUID permissaoId = UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999");
        when(sqlSession.selectList(eq("CondicaoAcessoDao.findHistorysUpdates"), anyMap())).thenReturn(List.of(AuditoriaModuloDtoTest.getAuditoria()));

        var lista = dao.findHistorysUpdates(permissaoId, 1l, 10l);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(AuditoriaModuloDtoTest.json(), lista.get(0).getDadosAntigos());

    }
}
