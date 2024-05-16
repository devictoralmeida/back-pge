package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class RequisicaoRecuperarSenhaTest {

	public static RequisicaoRecuperarSenha getRequisicao() {
		var model = new RequisicaoRecuperarSenha();
		model.setEmail("teste@gov.br");
		model.setId(UUID.fromString("265a01bb-6235-40a0-83e0-692bb7856ae3"));
		model.setHoraRequisicao(LocalDateTime.now().minusHours(1));

		return model;
	}

	public static RequisicaoRecuperarSenha getOtherRequisicao() {
		var model = new RequisicaoRecuperarSenha();
		model.setEmail("teste@gov.br");
		model.setId(UUID.fromString("265a01bb-6235-40a0-83e0-692bb7856ae3"));
		model.setHoraRequisicao(LocalDateTime.now().minusHours(1));

		return model;
	}

	public static RequisicaoRecuperarSenha getRequest() {
		var model = new RequisicaoRecuperarSenha();
		model.setEmail("teste@gov.br");
		model.setId(UUID.fromString("265a01bb-6235-40a0-83e0-692bb7856ae3"));
		model.setHoraRequisicao(LocalDateTime.now().minusHours(1));

		return model;
	}

	public static RequisicaoRecuperarSenha getOtherRequest() {
		var model = new RequisicaoRecuperarSenha();
		model.setEmail("teste@gov.br");
		model.setId(UUID.fromString("265a01bb-6235-40a0-83e0-692bb7856ae3"));
		model.setHoraRequisicao(LocalDateTime.now().minusHours(1));

		return model;
	}

	public static List<RequisicaoRecuperarSenha> getListRequisicao() {
		return Arrays.asList(getRequisicao(), getOtherRequisicao(), getRequest(), getOtherRequest());
	}

	@Test
	public void test_get_requisicao() {
		RequisicaoRecuperarSenha requisicao = RequisicaoRecuperarSenhaTest.getRequisicao();

		assertEquals("teste@gov.br", requisicao.getEmail());
		assertEquals(UUID.fromString("265a01bb-6235-40a0-83e0-692bb7856ae3"), requisicao.getId());

		LocalDateTime horaAtual = LocalDateTime.now();
		LocalDateTime horaEsperada = horaAtual.minusHours(1);
		LocalDateTime horaRequisicao = requisicao.getHoraRequisicao();

		assertEquals(horaEsperada.getHour(), horaRequisicao.getHour());
		assertEquals(horaEsperada.getMinute(), horaRequisicao.getMinute());
		assertEquals(horaEsperada.getSecond(), horaRequisicao.getSecond());
	}

	@Test
	public void test_get_requisicao_com_outro_email() {
		RequisicaoRecuperarSenha requisicao = RequisicaoRecuperarSenhaTest.getRequisicao();
		requisicao.setEmail("outro@teste.com");

		assertEquals("outro@teste.com", requisicao.getEmail());
	}
}
