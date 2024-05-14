package br.gov.ce.pge.mspgeportalcolaborador.service;

import br.gov.ce.pge.mspgeportalcolaborador.dto.ColaboradorResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColaboradorService {

    ColaboradorResponseDto findByCpf(String cpf);

    List<ColaboradorResponseDto> findByListCpfs(List<String> cpfs);
}
