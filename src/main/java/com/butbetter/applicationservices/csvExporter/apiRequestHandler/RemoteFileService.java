package com.butbetter.applicationservices.csvExporter.apiRequestHandler;

import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import javassist.tools.web.BadHttpRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

	public ResponseEntity<String> uploadFile(File handle) throws IOException, PermissionDeniedDataAccessException, BadHttpRequest {
		MultipartFile readyToUploadFile = convertToMultipartFile(handle);

		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add("file", readyToUploadFile);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		ResponseEntity<String> response = restTemplate.exchange(storageApiUrl.toString() + "/csv/v1",
				HttpMethod.POST, requestEntity, String.class);

		if (response.getStatusCodeValue() != 200) {
			String message = response.toString();
			logger.error(message);
			throw new IOException(message);
		}

		return response;
	}

	private MultipartFile convertToMultipartFile(File file) throws IOException {
		FileInputStream input = new FileInputStream(file);
		return new MockMultipartFile(file.getName(),
				file.getName(), "text/csv", IOUtils.toByteArray(input));
	}
}
