package com.butbetter.applicationservices.csvExporter.storageManager;

public class StorageNotReadyException extends Throwable {
	public StorageNotReadyException(String message) {
		super(message);
	}
}
