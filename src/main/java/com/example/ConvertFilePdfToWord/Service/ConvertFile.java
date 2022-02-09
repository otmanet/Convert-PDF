// package com.example.ConvertFilePdfToWord.Service;

// import org.springframework.stereotype.Service;

// import java.io.ByteArrayOutputStream;
// import java.io.InputStream;

// @Service

// public class ConvertFile {

// private org.jodconverter.core.office.OfficeManager officeManager;

// public ByteArrayOutputStream letConvert(final
// org.jodconverter.core.document.DocumentFormat documentFormat,
// final InputStream inputStream) {

// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

// final org.jodconverter.core.DocumentConverter convert =
// org.jodconverter.local.LocalConverter.builder()
// .officeManager(officeManager)
// .build();

// try {

// convert.convert(inputStream)
// .to(outputStream)
// .as(documentFormat)
// .execute();
// } catch (org.jodconverter.core.office.OfficeException e) {
// e.printStackTrace();
// }
// return outputStream;
// }
// }
