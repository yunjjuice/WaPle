package com.ssafy.waple.review.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.waple.review.exception.ImageSaveFailedException;
import com.ssafy.waple.review.exception.InvalidFileFormatException;

@Service
public class FileService {
	//ubuntu 기준 폴더 경로, 로컬에서 실행 시 (프로젝트 드라이브)/file/에 저장됨 (폴더 미리 생성 필요)
	private static final String PATH = "/file/";

	public String store(MultipartFile image) {
		String originFilename = image.getOriginalFilename();

		if (!(originFilename.toLowerCase().endsWith(".png") || originFilename.toLowerCase().endsWith(".jpg")
			|| originFilename.toLowerCase().endsWith(".jpeg"))) {
			throw new InvalidFileFormatException();
		}

		String fileName = UUID.randomUUID().toString() + originFilename.substring(originFilename.indexOf('.'));

		try {
			FileOutputStream fos = new FileOutputStream(PATH + fileName);
			fos.write(image.getBytes());
			fos.close();
		} catch (IOException e) {
			throw new ImageSaveFailedException();
		}
		return fileName;
	}

	public void delete(String imagesPath) {
		String[] images = imagesPath.split(";");
		for (String image : images) {
			deleteFile(image);
		}
	}

	public void deleteFile(String filePath) {
		File file = new File(PATH + filePath);
		file.delete(); //삭제할 파일이 없으면 그냥 넘어간다
	}
}
