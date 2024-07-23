package br.gov.ce.pge.gestao.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.configs.DaoConfig;
import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.dto.request.SucessorRequestDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.mappers.tomodel.SucessorMapperToModel;
import br.gov.ce.pge.gestao.repository.DividaRepository;
import br.gov.ce.pge.gestao.service.SucessorService;
import br.gov.ce.pge.gestao.service.rules.RegraDividasDevedorSucessor;
import br.gov.ce.pge.gestao.service.rules.RegraDividasFase;
import br.gov.ce.pge.gestao.service.rules.RegraDividasSucessor;

@Service
public class SucessorServiceImpl implements SucessorService {

	private final DividaRepository repository;
	private final PessoaContatoConfig pessoaContatoConfig;
	private final DividaConsultaServiceImpl consultaDividaService;
	private final FaseDividaServiceImpl faseDividaServiceImpl;

	public SucessorServiceImpl(DividaRepository repository, PessoaContatoConfig pessoaContatoConfig,
			DaoConfig daoConfig, DividaConsultaServiceImpl consultaDividaService,
			FaseDividaServiceImpl faseDividaServiceImpl) {
		this.repository = repository;
		this.pessoaContatoConfig = pessoaContatoConfig;
		this.consultaDividaService = consultaDividaService;
		this.faseDividaServiceImpl = faseDividaServiceImpl;
	}

	@Override
	public void save(SucessorRequestDto request) {

		List<Divida> dividas = getDividas(request);

		validarRegras(request, dividas);

		dividas.stream().forEach(divida -> {
			divida.setDividaPessoas(SucessorMapperToModel.converterList(request, divida, pessoaContatoConfig));
			repository.save(divida);
		});

	}

	private void validarRegras(SucessorRequestDto request, List<Divida> dividas) {
		if (!request.getLiberaDividaComSucessor())
			RegraDividasSucessor.verificaDividasComSucessorCadastrado(dividas);

		RegraDividasSucessor.verificaSucessorJaCadastrado(request.getDocumento(), dividas);

		RegraDividasDevedorSucessor.verificaSucessorCadastradoComoDevedor(request.getDocumento(), dividas);

		if (!request.getLiberaDevedoresDistinto())
			RegraDividasDevedorSucessor.verificaDividasComDevedoresDistinto(dividas);

		if (!request.getLiberaDividaFaseFinalistica())
			RegraDividasFase.verificaFasesDasDividas(dividas, faseDividaServiceImpl);
	}

	private List<Divida> getDividas(SucessorRequestDto request) {
		return request.getIdsDivida().stream().map(consultaDividaService::findByIdModel).collect(Collectors.toList());
	}

}
