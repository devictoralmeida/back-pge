package br.gov.ce.pge.gestao.dao;

import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.SistemaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.UsuarioFilterResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UsuarioDao {

    private final SqlSession sqlSession;

    public UsuarioDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Integer countfindByFilter(UsuarioFilterRequestDto request) {
        return this.sqlSession.selectOne("UsuarioDao.countfindByFilter", request.filters());
    }

    public List<UsuarioFilterResponseDto> findByFilter(UsuarioFilterRequestDto request) {
        List<UsuarioFilterResponseDto> listaUsuarios    = this.sqlSession.selectList("UsuarioDao.findByFilter", request.filters());
        List<SistemaFilterResponseDto> listaSistemas    = this.sqlSession.selectList("UsuarioDao.findByFilterSystem", request.filters());
        List<PerfilAcessoFilterResponseDto> listaPerfis = this.sqlSession.selectList("UsuarioDao.findByFilterPerfil", request.filters());

        listaUsuarios.stream().forEach(usuario -> {
            var sistemas = listaSistemas.stream().filter(sistema -> sistema.getIdUsuario().equals(usuario.getId())).collect(Collectors.toList());

            var perfisAcessos = listaPerfis.stream().filter(perfil -> perfil.getIdUsuario().equals(usuario.getId())).collect(Collectors.toList());

            perfisAcessos.stream().forEach(perfil -> 
                perfil.setSistemas(sistemas.stream().filter(sistema -> sistema.getIdUsuario().equals(perfil.getIdUsuario()) && perfil.getId().equals(sistema.getIdPerfil())).collect(Collectors.toList()))
            );

            usuario.setSistemas(this.groupBySistemas(sistemas));
            usuario.setPerfisAcessos(perfisAcessos);
        });

        return request.getOrderBy() != null && request.getOrderBy().contains("sistema")? ordenacaoPorSistema(request, listaUsuarios) : listaUsuarios;
    }

    private List<SistemaFilterResponseDto> groupBySistemas(List<SistemaFilterResponseDto> sistemas) {

        return sistemas.stream()
                .collect(Collectors.toMap(
                        SistemaFilterResponseDto::getId,
                        sistema -> sistema,
                        (sistema1, sistema2) -> sistema1
                ))
                .values()
                .stream()
                .toList();
    }

    private List<UsuarioFilterResponseDto> ordenacaoPorSistema(UsuarioFilterRequestDto request, List<UsuarioFilterResponseDto> listaUsuarios) {
        Comparator<UsuarioFilterResponseDto> comparator;

        if (request.getOrderBy().contains("desc")) {
            comparator = Comparator.comparing(dto -> dto.getSistemas().get(0).getNome(), Comparator.reverseOrder());
        } else {
            comparator = Comparator.comparing(dto -> dto.getSistemas().get(0).getNome());
        }

        return listaUsuarios.stream().sorted(comparator).collect(Collectors.toList());
    }

    public Integer countHistorysUpdates(UUID id) {
        return this.sqlSession.selectOne("UsuarioDao.countHistorysUpdates", CommonsDao.getParamsCount(id));
    }

    public List<AuditoriaDto> findHistorysUpdates(UUID id, Long offset, Long limit) {
        return this.sqlSession.selectList("UsuarioDao.findHistorysUpdates", CommonsDao.getParamsHistorys(id, offset, limit));
    }
}
