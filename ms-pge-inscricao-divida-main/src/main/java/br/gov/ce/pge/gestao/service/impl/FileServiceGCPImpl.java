package br.gov.ce.pge.gestao.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import br.gov.ce.pge.gestao.service.FileService;

@Service
@Qualifier(value = "FileServiceGCP")
public class FileServiceGCPImpl implements FileService {

	@Value("${gcp.credential}")
	private String credential;

	@Override
	public void upload(byte[] file, String fileName, String bucketName) {

		try {

			GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credential));

			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

			BlobId blobId = BlobId.of(bucketName, fileName);

			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

			try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file)) {
				storage.create(blobInfo, inputStream.readAllBytes());
				System.out.println("Array de bytes enviado com sucesso para " + fileName + " no bucket " + bucketName);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Erro ao enviar " + fileName + " no bucket " + bucketName);
			}
		} catch (IOException e) {
			System.err.println("Erro ao conectar com a GCP");
		}
	}

	@Override
	public String download(String fileName, String bucketName) {

		try {
			GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credential));
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			var blob = storage.get(BlobId.of(bucketName, fileName));

			if (blob != null) {
				byte[] contentBytes = blob.getContent();
				String base64Content = Base64.getEncoder().encodeToString(contentBytes);
				System.out.println("Conteúdo do arquivo " + fileName + " baixado com sucesso do bucket " + bucketName);
				return base64Content;
			} else {
				System.out.println("Arquivo " + fileName + " não encontrado no bucket " + bucketName);
				return null;
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Boolean delete(String fileName, String bucketName) {
		try {
			GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credential));

			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			var blob = storage.get(BlobId.of(bucketName, fileName));

			if (blob != null) {
				return blob.delete();
			} else {
				System.out.println("Arquivo " + fileName + " não encontrado no bucket " + bucketName);
				return false;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}

	@Override
	public Boolean compareFile(String fileInBase, byte[] fileRequest) {
		return Objects.nonNull(fileInBase) && fileInBase.equals(Base64.getEncoder().encodeToString(fileRequest));
	}

}
