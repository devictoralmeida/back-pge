package br.gov.ce.pge.mspgeportalcolaborador.service.impl;

import static br.gov.ce.pge.mspgeportalcolaborador.utils.CpfUtil.formatarCpf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.mspgeportalcolaborador.constants.MessageCommonsContanst;
import br.gov.ce.pge.mspgeportalcolaborador.dto.ColaboradorResponseDto;
import br.gov.ce.pge.mspgeportalcolaborador.mapper.ColaboradorMapperToDto;
import br.gov.ce.pge.mspgeportalcolaborador.repository.ColaboradorRepository;
import br.gov.ce.pge.mspgeportalcolaborador.service.ColaboradorService;
import br.gov.ce.pge.mspgeportalcolaborador.shared.exception.ColaboradorNotFoundException;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

	private final ColaboradorRepository repository;
	
	public ColaboradorServiceImpl(ColaboradorRepository repository) {
		this.repository = repository;
	}

	@Override
	public ColaboradorResponseDto findByCpf(String cpf) {

		var colaborador = this.repository.findByCpf(formatarCpf(cpf));

		if (colaborador == null)
			throw new ColaboradorNotFoundException(MessageCommonsContanst.COLABORADOR_NAO_ENCONTRADO);
		
		return ColaboradorMapperToDto.toDto(colaborador);
	}

	@Override
	public List<ColaboradorResponseDto> findByListCpfs(List<String> cpfs) {
		var colaboradores = this.repository.findByCpfs(getCpfFormatados(cpfs));
		return colaboradores.stream().map(ColaboradorMapperToDto::toDto).collect(Collectors.toList());
	}

	private List<String> getCpfFormatados(List<String> cpfs) {
		List<String> listaCpfFormatados = new ArrayList<>();
		cpfs.stream().forEach(cpf -> listaCpfFormatados.add(formatarCpf(cpf)));
		return listaCpfFormatados;
	}
}
