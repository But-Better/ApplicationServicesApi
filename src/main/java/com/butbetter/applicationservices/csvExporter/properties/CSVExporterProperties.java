package com.butbetter.applicationservices.csvExporter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.MalformedURLException;
import java.net.URL;

@ConfigurationProperties("csv-exporter")
public class CSVExporterProperties {

	/** general storage url */
	private URL StorageUrl;

	{
		try {
			StorageUrl = new URL("localhost");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/** Folder location for storing files */
	private String location = "tmp";

	/**
	 * Folder location for moving file, when they couldn't be deleted
	 */
	private String manualDeletionLocation = "err";

	public CSVExporterProperties() {}

	public CSVExporterProperties(String customLocation, String manualDeletionLocation) {
		this.manualDeletionLocation = manualDeletionLocation;
		this.location = customLocation;
	}

	public String getSaveLocation() {
		return location;
	}

	public void setSaveLocation(String location) {
		this.location = location;
	}

	public String getManualDeletionLocation() {
		return manualDeletionLocation;
	}

	public void setManualDeletionLocation(String manualDeletionLocation) {
		this.manualDeletionLocation = manualDeletionLocation;
	}

	public URL getStorageUrl() {
		return StorageUrl;
	}
}
