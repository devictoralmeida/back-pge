package br.gov.ce.pge.gestao.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageOptions;

import br.gov.ce.pge.gestao.service.FileService;

@Service
@Qualifier(value = "FileServiceGCP")
public class FileServiceGCPImpl implements FileService {

	@Value("${bucket.pge}")
	private String pathBucket;

	@Override
	public void upload(byte[] file, String fileName, String bucketName) {
		var storage = StorageOptions.getDefaultInstance().getService();
		var blobId = BlobId.of(bucketName, fileName);
		var blobInfo = BlobInfo.newBuilder(blobId).build();
		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file)) {
			storage.create(blobInfo, inputStream.readAllBytes());
			System.out.println("Array de bytes enviado com sucesso para " + fileName + " no bucket " + bucketName);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erro ao enviar array de bytes para o Google Cloud Storage");
		}
	}

	@Override
	public String download(String fileName, String bucketName) {
		var storage = StorageOptions.getDefaultInstance().getService();
		var blob = storage.get(BlobId.of(bucketName, fileName));

		if (blob != null) {
			byte[] contentBytes = blob.getContent();
			String contentString = new String(contentBytes, StandardCharsets.UTF_8);
			System.out.println("Conteúdo do arquivo " + fileName + " baixado com sucesso do bucket " + bucketName);
			return contentString;
		} else {
			System.out.println("Arquivo " + fileName + " não encontrado no bucket " + bucketName);
			return null;
		}

	}

	@Override
	public Boolean delete(String fileName, String bucketName) {
		var storage = StorageOptions.getDefaultInstance().getService();
		var blob = storage.get(BlobId.of(bucketName, fileName));

		if (blob != null) {
			return blob.delete();
		} else {
			System.out.println("Arquivo " + fileName + " não encontrado no bucket " + bucketName);
			return false;
		}

	}

	@Override
	public Boolean compareFile(String fileInBase, byte[] fileRequest) {
		return Objects.nonNull(fileInBase) && fileInBase.equals(Base64.getEncoder().encodeToString(fileRequest));
	}

}
