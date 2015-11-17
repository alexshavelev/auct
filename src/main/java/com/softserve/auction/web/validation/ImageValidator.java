package com.softserve.auction.web.validation;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.auction.domain.UploadFile;

@Component
public class ImageValidator implements Validator {
	private final int GIF = 0x47494638;
	private final int JPEG = 0xffd8ffe0;
	private final int PNG = 0x89504e47;

	
	
	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(UploadFile.class);
	}

	
	@Override
	public void validate(Object arg0, Errors errors) {
		UploadFile file = (UploadFile) arg0;
		if (!file.getFile().isEmpty()){
		formatValidation(file, errors);
		}
	}

	
	private void formatValidation(UploadFile file, Errors errors) {
		String type = file.getFile().getContentType();
		if (!type.contains("jpeg") && !type.contains("png")
				&& !type.contains("gif")){
			errors.rejectValue("file", "file.notSupported");
		} else if (getImageFileType(file).equals("Unknown format")) {
			errors.rejectValue("file", "file.notSupported");
		}
	}

	
	private String getImageFileType(UploadFile file) {
		try {
			DataInputStream input = new DataInputStream(
					new BufferedInputStream((file.getFile().getInputStream())));
			for (int i = 0; i < 1;) {
				switch (input.readInt()) {
				case GIF:
					return "gif";
				case JPEG:
					return "jpeg";
				case PNG:
					return "png";
				default:
					return "Unknown format";
				}
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			return "Unknown format";
		}
		return "Unknown format";
	}
}
