package br.gov.ce.pge.mspgeapigateway.utils;

import br.gov.ce.pge.mspgeapigateway.constante.SharedConstant;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public class DispositivoUtil {

    private DispositivoUtil() {}

    public static String getDetalhesDispositivo(String userAgent) {
        String deviceDetails = "";

        if(userAgent != null) {

            String plataforma = extrairPlataforma(userAgent);

            List<String> versoes = Arrays.asList(plataforma.split(";"));

            deviceDetails = setVersao(versoes);
        }

        return deviceDetails;
    }

    public static String extrairIp(ServerWebExchange request) {
        String clientIp;
        String clientXForwardedForIp = request.getRequest().getHeaders().getFirst("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            InetSocketAddress remoteAddress = request.getRequest().getRemoteAddress();
            if (remoteAddress != null) {
                InetAddress address = remoteAddress.getAddress();
                clientIp = (address != null) ? address.toString() : "";
            } else {
                clientIp = "";
            }
        }
        return clientIp.replace("/", "");
    }

    private static String parseXForwardedHeader(String header) {
        return header.split(SharedConstant.REGEX_SPLIT)[SharedConstant.INDICE_INICIAL];
    }

    private static String extrairPlataforma(String userAgent) {
        Pattern pattern = Pattern.compile(SharedConstant.REGEX_USER_AGENT);
        Matcher matcher = pattern.matcher(userAgent);
        if (matcher.find()) {
            return matcher.group(SharedConstant.SUBTRACAO_INDICE);
        }
        return "";
    }

    private static String setVersao(List<String> versoes) {

        if(versoes.size() == SharedConstant.SUBTRACAO_INDICE) {
            return "";
        }

        String primeiraVersao = versoes.get(SharedConstant.INDICE_INICIAL);
        String ultimaVersao = versoes.size() > SharedConstant.ULTIMO_INDICE ? versoes.get(SharedConstant.ULTIMO_INDICE) : versoes.get(SharedConstant.SUBTRACAO_INDICE);

        return primeiraVersao + ultimaVersao;
    }

}
