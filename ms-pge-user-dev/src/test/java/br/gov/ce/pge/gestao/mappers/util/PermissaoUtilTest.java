package br.gov.ce.pge.gestao.mappers.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.entity.Permissao;

class PermissaoUtilTest {

	@Test
	void adicionar_campo_alterado_permissoes_nenhum_idAdicionado_nemRemovido() {
		Permissao permissao1 = mock(Permissao.class);
		when(permissao1.getId()).thenReturn(UUID.fromString("a5c7a1b7-0b20-4f0a-a0a6-4c5b3f7c0659"));
		Permissao permissao2 = mock(Permissao.class);
		when(permissao2.getId()).thenReturn(UUID.fromString("81f344bb-1874-46b7-9b07-62d0a5f4b102"));
		Permissao permissao3 = mock(Permissao.class);
		when(permissao3.getId()).thenReturn(UUID.fromString("eda88f47-f1e3-4c44-9886-0b273e54e08d"));
		List<Permissao> listaPermissoesAntigos = Arrays.asList(permissao1, permissao2, permissao3);

		Map<String, Object> listas = new HashMap<>();
		listas.put("idsAdicionados", "");
		listas.put("idsRemovidos", "");

		List<Document> documents = new ArrayList<>();

		PermissaoUtil.adicionarCampoAlteradoPermissoes(documents, listaPermissoesAntigos, listas);

		assertEquals(0, documents.size());
	}

	@Test
	void adicionar_campo_alterado_permissoes_Nenhum_idAdicionado_removido_e_lista_antiga_vazia() {
		List<Permissao> listaPermissoesAntigos = new ArrayList<>();

		Map<String, Object> listas = new HashMap<>();
		listas.put("idsAdicionados", "");
		listas.put("idsRemovidos", "");

		List<Document> documents = new ArrayList<>();

		PermissaoUtil.adicionarCampoAlteradoPermissoes(documents, listaPermissoesAntigos, listas);

		assertEquals(0, documents.size());
	}

	@Test
	void adicionar_campo_alterado_permissoes_com_idsAdicionados_e_removidos() {
		Permissao permissao1 = mock(Permissao.class);
		when(permissao1.getId()).thenReturn(UUID.fromString("a5c7a1b7-0b20-4f0a-a0a6-4c5b3f7c0659"));
		Permissao permissao2 = mock(Permissao.class);
		when(permissao2.getId()).thenReturn(UUID.fromString("81f344bb-1874-46b7-9b07-62d0a5f4b102"));
		Permissao permissao3 = mock(Permissao.class);
		when(permissao3.getId()).thenReturn(UUID.fromString("eda88f47-f1e3-4c44-9886-0b273e54e08d"));
		List<Permissao> listaPermissoesAntigos = Arrays.asList(permissao1, permissao2, permissao3);

		Map<String, Object> listas = new HashMap<>();
		listas.put("idsAdicionados", "ccc23f99-5b73-456d-8431-9d4447f5895e,26ff1be2-9794-49c3-a4d7-d623d9f0c889");
		listas.put("idsRemovidos", "a5c7a1b7-0b20-4f0a-a0a6-4c5b3f7c0659,eda88f47-f1e3-4c44-9886-0b273e54e08d");

		List<Document> documents = new ArrayList<>();

		PermissaoUtil.adicionarCampoAlteradoPermissoes(documents, listaPermissoesAntigos, listas);

		assertEquals(1, documents.size());

		Document document = documents.get(0);
		assertEquals("Permissões", document.get("campoAtualizado"));
		assertEquals(List.of("a5c7a1b7-0b20-4f0a-a0a6-4c5b3f7c0659", "81f344bb-1874-46b7-9b07-62d0a5f4b102", "eda88f47-f1e3-4c44-9886-0b273e54e08d"), document.get("valorAntigo"));
		assertEquals(List.of("81f344bb-1874-46b7-9b07-62d0a5f4b102", "ccc23f99-5b73-456d-8431-9d4447f5895e", "26ff1be2-9794-49c3-a4d7-d623d9f0c889"), document.get("valorNovo"));
	}

}
