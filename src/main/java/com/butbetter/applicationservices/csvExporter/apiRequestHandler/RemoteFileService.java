package com.butbetter.applicationservices.csvExporter.apiRequestHandler;

import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.NotActiveException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class RemoteFileService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final RestTemplate restTemplate;

	private final URL storageApiUrl;

	public RemoteFileService(URL storageApiUrl, RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.storageApiUrl = storageApiUrl;
	}

	public RemoteFileService(URL storageApiUrl) {
		this.restTemplate = new RestTemplate();
		this.storageApiUrl = storageApiUrl;
	}

	@Autowired
	public RemoteFileService(CSVExporterProperties properties) {
		this.restTemplate = new RestTemplate();
		this.storageApiUrl = properties.getStorageUrl();
	}

	public boolean isUp() {
		try {
			return makeGeneralApiGetCall().getStatusCodeValue() == 200;
		} catch (URISyntaxException e) {
			logger.warn(e.getMessage());
			return false;
		}
	}

	public URL getStorageApiUrl() {
		return storageApiUrl;
	}

	private ResponseEntity<?> makeGeneralApiGetCall() throws URISyntaxException {
		return this.restTemplate.getForEntity(storageApiUrl.toURI(), Object.class);
	}

	public void uploadFile(File handle) throws NotActiveException, FileNotFoundException, PermissionDeniedDataAccessException, BadHttpRequest {

	}

	public void downloadRemoteFile(String name) throws NotActiveException, FileNotFoundException, BadHttpRequest {

	}
}
