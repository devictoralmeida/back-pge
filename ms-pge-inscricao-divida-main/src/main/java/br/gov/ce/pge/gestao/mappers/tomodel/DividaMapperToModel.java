package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.dto.request.DividaRequestDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.service.FileService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.UUID;

public class DividaMapperToModel {

    private static final String BUCKET_DIVIDA = "bucket_inscricao_divida";

    private DividaMapperToModel() {
    }

    public static Divida converter(DividaRequestDto request, TipoReceitaResponseDto tipoReceita,
                                   OrigemDebitoResponseDto origemDebito, FileService fileService) {

        validacaoCampos(request, tipoReceita, origemDebito);
        uploadFile(request, fileService);

        var divida = new Divida();
        BeanUtils.copyProperties(request, divida, "dataCriacao", "nomeUsuarioCadastro");
        divida.setTipoDocumento(getTipoDocumento(request));
        return divida;
    }

    public static Divida converterUpdate(DividaRequestDto request, Divida divida, TipoReceitaResponseDto tipoReceita,
                                         OrigemDebitoResponseDto origemDebito, FileService fileService) {

        validacaoCampos(request, tipoReceita, origemDebito);
        uploadFileUpdate(request, divida, fileService);

        BeanUtils.copyProperties(request, divida, "dataCriacao", "nomeUsuarioCadastro");
        divida.setTipoDocumento(getTipoDocumento(request));
        return divida;
    }

    public static void uploadFileUpdate(DividaRequestDto request, Divida divida, FileService fileService) {
        if (divida.getNomeAnexoProcessoDigitalizado() != null) {
            String fileInBucket = fileService.download(divida.getNomeAnexoProcessoDigitalizado(), BUCKET_DIVIDA);

            if (request.getArquivo() == null) {
                fileService.delete(divida.getNomeAnexoProcessoDigitalizado(), BUCKET_DIVIDA);
                request.setNomeAnexoProcessoDigitalizado(null);
            } else if (!fileService.compareFile(fileInBucket, request.getArquivo())) {
                fileService.delete(divida.getNomeAnexoProcessoDigitalizado(), BUCKET_DIVIDA);
                uploadFile(request, fileService);
            }
        } else {
            uploadFile(request, fileService);
        }

    }

    public static void uploadFile(DividaRequestDto request, FileService fileService) {
        if (request.getArquivo() != null) {
            var fileName = "divida_" + UUID.randomUUID().toString();
            request.setNomeAnexoProcessoDigitalizado(fileName);
            fileService.upload(request.getArquivo(), fileName, BUCKET_DIVIDA);
        }
    }

    public static String getTipoDocumento(DividaRequestDto request) {
        return request != null && request.getTipoDocumento() != null ? request.getTipoDocumento().name() : null;
    }

    public static void validacaoCampos(DividaRequestDto request, TipoReceitaResponseDto tipoReceita,
                                       OrigemDebitoResponseDto origemDebito) {

        isOrigemAiOuAiam(request, origemDebito);
        isOrigemRestoParcelamento(request, origemDebito);
        isOrigemItcd(request, origemDebito);
        isOrigemIpva(request, origemDebito);
        isOrigemCustasJudiciais(request, origemDebito);

        isTipoReceitaNaoTributario(request, tipoReceita);

    }

    /**
     * @param request
     * @param tipoReceita
     * @return Verificando_tipo_receita_nao_triburaria
     */

    public static void isTipoReceitaNaoTributario(DividaRequestDto request, TipoReceitaResponseDto tipoReceita) {
        if (tipoReceita.getNatureza() == Natureza.NAO_TRIBUTARIA) {
            verificaNaturezaFundamentacao(request);
            verificaDataConstituicaoDefinitivaCredito(request);
        }
    }

    public static void verificaNaturezaFundamentacao(DividaRequestDto request) {
        if (request.getNaturezaFundamentacao() == null) {
            throw new NegocioException("Favor informar a Natureza e Fundamentação Legal do Débito.");
        }
    }

    /**
     * @param request
     * @param origemDebito
     * @return Verificando_algumas_Origens
     */

    public static void isOrigemAiOuAiam(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
        if (origemDebito.getNome().equalsIgnoreCase("AI") || origemDebito.getNome().equalsIgnoreCase("AIAM")) {
            verificaTipoDocumento(request);
            verificaDataTransitoJulgado(request);
        }
    }

    public static void isOrigemRestoParcelamento(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
        if (origemDebito.getNome().equalsIgnoreCase("Resto de Parcelamento")) {
            verificaDataConstituicaoDefinitivaCredito(request);
            verificaSequencialParcelamento(request);
        }
    }

    public static void isOrigemItcd(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
        if (origemDebito.getNome().equalsIgnoreCase("ITCD")) {
            verificaDataConstituicaoDefinitivaCredito(request);
            verificaGuiaItcd(request);
        }
    }

    public static void isOrigemIpva(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
        if (origemDebito.getNome().equalsIgnoreCase("IPVA")) {
            verificaPlacaVeiculo(request);
            verificaChassi(request);
        }
    }

    public static void isOrigemCustasJudiciais(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
        if (origemDebito.getNome().equalsIgnoreCase("CUSTAS JUDICIAIS")) {
            verificaProtocoloJudicial(request);
        }
    }

    /**
     * @param request
     * @return Verificando_campos_dependentes
     */

    public static void verificaProtocoloJudicial(DividaRequestDto request) {
        if (request.getProtocoloJudicial() == null) {
            throw new NegocioException("Favor informar o protocolo judicial.");
        }
    }

    public static void verificaChassi(DividaRequestDto request) {
        if (request.getNumeroChassi() == null) {
            throw new NegocioException("Favor informar o número do chassi.");
        }
    }

    public static void verificaPlacaVeiculo(DividaRequestDto request) {
        if (request.getPlacaVeiculo() == null) {
            throw new NegocioException("Favor informar a placa do veículo.");
        }
    }

    public static void verificaGuiaItcd(DividaRequestDto request) {
        if (request.getGuiaItcd() == null) {
            throw new NegocioException("Favor informar a Guia do ITCD.");
        }
    }

    public static void verificaSequencialParcelamento(DividaRequestDto request) {
        if (request.getSequencialParcelamento() == null) {
            throw new NegocioException("Favor informar o Sequencial do Parcelamento.");
        }
    }

    public static void verificaDataConstituicaoDefinitivaCredito(DividaRequestDto request) {
        if (request.getDataConstituicaoDefinitivaCredito() == null) {
            throw new NegocioException("Favor informar a Data da Constituição Definitiva do Crédito.");
        } else {
            verificaDataConstituicaoFutura(request);
        }
    }

    public static void verificaDataConstituicaoFutura(DividaRequestDto request) {
        if (request.getDataConstituicaoDefinitivaCredito().isAfter(LocalDate.now())) {
            throw new NegocioException("A data não pode ser superior a data atual");
        }
    }

    public static void verificaDataTransitoJulgado(DividaRequestDto request) {
        if (request.getDataTransitoJulgado() == null) {
            throw new NegocioException("Favor informar a Data do Trânsito em Julgado.");
        } else {
            verificaDataTransitoJulgadoFutura(request);
            request.setDataConstituicaoDefinitivaCredito(request.getDataTransitoJulgado());
        }
    }

    public static void verificaDataTransitoJulgadoFutura(DividaRequestDto request) {
        if (request.getDataTransitoJulgado().isAfter(LocalDate.now())) {
            throw new NegocioException("A data trânsito em julgado não pode ser superior a data atual");
        }
    }

    public static void verificaTermoRevelia(DividaRequestDto request) {
        if (request.getTermoRevelia() == null) {
            throw new NegocioException("Favor informar o Termo de revelia.");
        } else {
            verificaDataTermoRevelia(request);
        }
    }

    public static void verificaDataTermoRevelia(DividaRequestDto request) {
        if (request.getDataTermoRevelia() == null) {
            throw new NegocioException("Favor informar a data do Termo de revelia.");
        }
    }

    public static void verificaTipoDocumento(DividaRequestDto request) {
        if (getTipoDocumento(request) == null) {
            throw new NegocioException("Favor informar o Tipo Documento");
        } else {
            verificaNumeroDocumento(request);
            verificaDataDocumento(request);
            verificaSeTipoDocumentoIsAI(request);
        }
    }

    public static void verificaSeTipoDocumentoIsAI(DividaRequestDto request) {
        if (request.getTipoDocumento() == TipoDocumento.AI) {
            verificaTermoRevelia(request);
        }
    }

    public static void verificaDataDocumento(DividaRequestDto request) {
        if (request.getDataDocumento() == null) {
            throw new NegocioException("Favor informar a data do documento");
        } else {
            verificaDataDocumentoFutura(request);
        }
    }

    public static void verificaDataDocumentoFutura(DividaRequestDto request) {
        if (request.getDataDocumento().isAfter(LocalDate.now())) {
            throw new NegocioException("A data do documento não pode ser superior a data atual");
        }
    }

    public static void verificaNumeroDocumento(DividaRequestDto request) {
        if (request.getNumeroDocumento() == null) {
            throw new NegocioException("Favor informar o número do documento");
        }
    }
}
