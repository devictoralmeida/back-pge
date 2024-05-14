package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.CodigoVerificacao;
import org.springframework.stereotype.Service;

@Service
public interface CodigoVerificacaoService {
    CodigoVerificacao save(CodigoVerificacao codigo);
}
