package com.softserve.auction.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softserve.auction.domain.UploadFile;

@Service
public class FileService {

	public void saveFile(String path, UploadFile img) {
		try {
			MultipartFile imgData = img.getFile();
			InputStream inputStream = null;
			OutputStream outputStream = null;
			if (imgData.getSize() > 0) {
				inputStream = imgData.getInputStream();
				System.out.println(path + imgData.getOriginalFilename());
				outputStream = new FileOutputStream(path + "\\"
						+ imgData.getOriginalFilename());
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
