package com.butbetter.applicationservices.csvExporter.csvConverter;

import com.opencsv.ICSVWriter;
import com.opencsv.ResultSetHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CSVCollector implements ICSVWriter {

	private final List<String> csvData = new ArrayList<>();

	private boolean closed = false;
	private boolean hadError = false;
	private IOException caughtException;

	private final Pattern quoteApplierPattern = Pattern.compile("[" + DEFAULT_SEPARATOR + DEFAULT_ESCAPE_CHARACTER + DEFAULT_QUOTE_CHARACTER + DEFAULT_LINE_END + "]");

	@Override
	public void writeAll(Iterable<String[]> allLines, boolean applyQuotesToAll) {
		try {
			checkIfClosed();
		} catch (IOException e) {
			hadError = true;
			caughtException = e;
			closed = true;
		}

		allLines.forEach(line -> addToList(line, applyQuotesToAll));
	}

	private void addToList(String[] line, boolean applyQuotesToAll) {
		String[] dataToAdd = new String[line.length];

		if(applyQuotesToAll) {
			IntStream.range(0, line.length).forEach(index -> dataToAdd[index] = DEFAULT_QUOTE_CHARACTER + line[index] + DEFAULT_QUOTE_CHARACTER);
		} else {
			IntStream.range(0, line.length).forEach(index -> {
				if(quoteApplierPattern.matcher(line[index]).matches()) {
					dataToAdd[index] = DEFAULT_QUOTE_CHARACTER + line[index] + DEFAULT_QUOTE_CHARACTER;
				} else {
					dataToAdd[index] = line[index];
				}
			});
		}

		csvData.addAll(Arrays.stream(dataToAdd).collect(Collectors.toList()));
	}

	private void checkIfClosed() throws IOException {
		if(closed) {
			throw new IOException("the the ICSVWriter is already closed");
		}
	}

	public synchronized void reset() {
		List<String> copy = new ArrayList<>(csvData);
		csvData.removeAll(copy);
		closed = false;
		hadError = false;
		caughtException = null;
	}

	@Override
	public int writeAll(ResultSet rs, boolean includeColumnNames, boolean trim, boolean applyQuotesToAll) {
		throw new UnsupportedOperationException("this method is not implemented in this version");
	}

	@Override
	public void writeNext(String[] nextLine, boolean applyQuotesToAll) {
		addToList(nextLine, applyQuotesToAll);
	}

	@Override
	public boolean checkError() {
		return hadError;
	}

	@Override
	public IOException getException() {
		return caughtException;
	}

	@Override
	public void resetError() {
		hadError = false;
		caughtException = null;
	}

	@Override
	public void setResultService(ResultSetHelper resultService) {
		throw new UnsupportedOperationException("this method is not implemented in this version");
	}

	@Override
	public void close() {
		closed = true;
	}

	@Override
	public void flush() {}

	public List<String> getCsvData() {
		return csvData;
	}
}
