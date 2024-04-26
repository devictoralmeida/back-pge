package br.gov.ce.pge.gestao.enums;

public enum TipoMovimento {

	CADASTRAR(0), ATUALIZAR(1), DELETAR(2);

	private final int value;

	TipoMovimento(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
