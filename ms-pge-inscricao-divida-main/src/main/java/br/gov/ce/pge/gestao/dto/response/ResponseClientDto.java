package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ResponseClientDto<T> implements Serializable {


	private static final long serialVersionUID = -7627385547775882785L;
	
	private T data;
    private List<String> errors = new ArrayList<>();
    private List<String> warns = new ArrayList<>();
    private List<String> infos = new ArrayList<>();
    private String mensagem;
    private URI uri;
    private Integer status;

    private ResponseClientDto() {}

    public static <T> ResponseClientDto<T> fromData(T data, Integer status, String mensagem) {
        return new ResponseClientDto<T>()
                .setData(data)
                .setStatus(status)
                .setMensagem(mensagem);
    }

    public static <T> ResponseClientDto<T> fromData(T data, Integer status, String mensagem, URI uri) {
        return new ResponseClientDto<T>()
                .setData(data)
                .setStatus(status)
                .setMensagem(mensagem)
                .setUri(uri);
    }

    public static <T> ResponseClientDto<T> fromData(T data, Integer status, String mensagem, List<String> errors) {
        return new ResponseClientDto<T>()
                .setData(data)
                .setStatus(status)
                .setMensagem(mensagem)
                .setErrors(errors);
    }

    public String getMensagem(){
        return this.mensagem;
    }

    public ResponseClientDto<T> setMensagem(String mensagem){
        this.mensagem = mensagem;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseClientDto<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseClientDto<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public ResponseClientDto<T> setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public List<String> getErrors() {
        return errors;
    }

    public ResponseClientDto<T> setErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public ResponseClientDto<T> addError(String error) {
        this.errors.add(error);
        return this;
    }

    public List<String> getWarns() {
        return warns;
    }

    public ResponseClientDto<T> setWarns(List<String> warns) {
        this.warns = warns;
        return this;
    }

    public ResponseClientDto<T> addWarn(String warn) {
        this.warns.add(warn);
        return this;
    }

    public List<String> getInfos() {
        return infos;
    }

    public ResponseClientDto<T> setInfos(List<String> infos) {
        this.infos = infos;
        return this;
    }

    public ResponseClientDto<T> addInfo(String info) {
        this.infos.add(info);
        return this;
    }

}
