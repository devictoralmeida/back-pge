package br.gov.ce.pge.gestao.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.service.FileService;

@Service
@Qualifier(value = "FileServiceLocal")
public class FileServiceLocalImpl implements FileService {

	@Value("${bucket.pge}")
	private String pathBucket;

	@Override
	public void upload(byte[] file, String fileName, String bucketName) {
		if (Objects.nonNull(file)) {

			String folderPath = pathBucket + bucketName + File.separator;
			String filePath = folderPath + fileName;
			String stringBase64 = Base64.getEncoder().encodeToString(file);

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
				writer.write(stringBase64);
				System.out.println("Conteúdo escrito no arquivo com sucesso.");
			} catch (IOException e) {
				System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
			}
		}
	}

	@Override
	public String download(String fileName, String bucketName) {
		try (BufferedReader reader = new BufferedReader(
				new FileReader(pathBucket + bucketName + File.separator + fileName))) {

			String line;
			StringBuilder content = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				content.append(line);
			}

			return content.toString();
			
		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		}

		return null;
	}

	@Override
	public Boolean delete(String fileName, String bucketName) {
		String filePath = pathBucket + bucketName + File.separator + fileName;

		File file = new File(filePath);

		if (file.exists() && file.isFile()) {
			return file.delete();
		} else {
			System.err.println("Arquivo não encontrado.");
			return false;
		}

	}

	@Override
	public Boolean compareFile(String fileInBase, byte[] fileRequest) {
		return Objects.nonNull(fileInBase) && fileInBase.equals(Base64.getEncoder().encodeToString(fileRequest));
	}

}
