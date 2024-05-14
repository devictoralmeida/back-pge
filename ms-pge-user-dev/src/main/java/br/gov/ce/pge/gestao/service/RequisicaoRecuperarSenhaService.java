package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.RequisicaoRecuperarSenha;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequisicaoRecuperarSenhaService {
    List<RequisicaoRecuperarSenha> getRequestByEmail(String email);

    void save(RequisicaoRecuperarSenha requisicao);
}
