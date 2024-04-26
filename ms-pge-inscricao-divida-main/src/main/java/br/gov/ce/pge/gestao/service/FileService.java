package br.gov.ce.pge.gestao.service;

import org.springframework.stereotype.Service;

@Service
public interface FileService {
	
	void upload(byte[] file, String fileName, String bucketName);
	
	String download(String fileName, String bucketName);
	
	Boolean delete(String fileName, String bucketName);
	
	Boolean compareFile(String fileInBase, byte[] fileRequest);

}
