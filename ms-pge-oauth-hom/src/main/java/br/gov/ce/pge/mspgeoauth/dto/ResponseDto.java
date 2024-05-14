package br.gov.ce.pge.mspgeoauth.dto;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ResponseDto<T> implements Serializable {
	
	private static final long serialVersionUID = 752389082874920957L;

	private transient T data;
    private List<String> errors = new ArrayList<>();
    private List<String> warns = new ArrayList<>();
    private List<String> infos = new ArrayList<>();
    private String mensagem;
    private URI uri;
    private Integer status;

    public static <T> ResponseDto<T> fromData(T data, Integer status, String mensagem) {
        return new ResponseDto<T>()
                .setData(data)
                .setStatus(status)
                .setMensagem(mensagem);
    }

    public static <T> ResponseDto<T> fromData(T data, Integer status, String mensagem, URI uri) {
        return new ResponseDto<T>()
                .setData(data)
                .setStatus(status)
                .setMensagem(mensagem)
                .setUri(uri);
    }

    public static <T> ResponseDto<T> fromData(T data, Integer status, String mensagem, List<String> errors) {
        return new ResponseDto<T>()
                .setData(data)
                .setStatus(status)
                .setMensagem(mensagem)
                .setErrors(errors);
    }

    public String getMensagem(){
        return this.mensagem;
    }

    public ResponseDto<T> setMensagem(String mensagem){
        this.mensagem = mensagem;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseDto<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseDto<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public ResponseDto<T> setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public List<String> getErrors() {
        return errors;
    }

    public ResponseDto<T> setErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public ResponseDto<T> addError(String error) {
        this.errors.add(error);
        return this;
    }

    public List<String> getWarns() {
        return warns;
    }

    public ResponseDto<T> setWarns(List<String> warns) {
        this.warns = warns;
        return this;
    }

    public ResponseDto<T> addWarn(String warn) {
        this.warns.add(warn);
        return this;
    }

    public List<String> getInfos() {
        return infos;
    }

    public ResponseDto<T> setInfos(List<String> infos) {
        this.infos = infos;
        return this;
    }

    public ResponseDto<T> addInfo(String info) {
        this.infos.add(info);
        return this;
    }

}
