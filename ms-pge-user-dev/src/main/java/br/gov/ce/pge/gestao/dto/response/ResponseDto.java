package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseDto<T> implements Serializable {

    private static final long serialVersionUID = 920693103078426185L;

    private transient T data;
    private List<String> errors = new ArrayList<>();
    private List<String> warns = new ArrayList<>();
    private List<String> infos = new ArrayList<>();
    private String mensagem;
    private URI uri;
    private Integer status;

    private ResponseDto() {}

    public static <T> ResponseDto<T> fromData(T data, HttpStatus status, String mensagem) {
        return new ResponseDto<T>()
                .setData(data)
                .setStatus(status)
                .setMensagem(mensagem);
    }

    public static <T> ResponseDto<T> fromData(T data, HttpStatus status, String mensagem, List<String> errors) {
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

    public ResponseDto<T> setStatus(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public List<String> getErrors() {
        return errors;
    }

    public ResponseDto<T> setErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public List<String> getWarns() {
        return warns;
    }

    public List<String> getInfos() {
        return infos;
    }

}
