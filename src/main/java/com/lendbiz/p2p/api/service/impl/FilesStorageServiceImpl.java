package com.lendbiz.p2p.api.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.exception.InvalidFileException;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.FilesStorageService;
import com.lendbiz.p2p.api.service.base.BaseService;

import lombok.extern.log4j.Log4j2;

@Service("filesStorageService")
@Log4j2
public class FilesStorageServiceImpl extends BaseResponse<FilesStorageService> implements FilesStorageService {
	// public FilesStorageServiceImpl(Environment env) {
	// super(env);

	// }

	private final String root = "images/";
	private Path direct;

	@Override
	public String init(String key, String custId) {
		String url = root + custId + "/" + key;

		direct = Paths.get(url);

		if (!Files.exists(direct)) {
			try {
				Files.createDirectories(direct);
				log.info("Create path = " + direct + " successfully!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (key.equalsIgnoreCase("avatar") && !isEmpty(direct)) {
					FileUtils.cleanDirectory(new File(url));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return direct.toString();
	}

	@Override
	public String save(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.direct.resolve(file.getOriginalFilename()));
			return "OK";
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.FAILED_SAVE_FILE,
					ErrorCode.FAILED_SAVE_FILE_DESCRIPTION + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> uploadFile(MultipartFile file, String key, String custId) {

		String direct = init(key, custId);
		MultipartFile mFile;
		// File nfile;
		String extension;
		String fileName = custId + System.currentTimeMillis();

		try {

			// nfile = new File(root + custId);
			// // mFile = new MockMultipartFile("hd", pdf.getName(), "text/plain",
			// IOUtils.toByteArray(file));

			// FileInputStream input = new FileInputStream(nfile);

			extension = FilenameUtils.getExtension(file.getOriginalFilename());
			mFile = new MockMultipartFile(fileName, fileName + "." + extension, "text/plain",
					IOUtils.toByteArray(file.getInputStream()));

			save(mFile);
			log.info("[API UPLOAD] Uploaded the file successfully: ", file.getOriginalFilename());

			return response(toResult(custId + "/" + key + "/" + fileName + "/" + extension));

		} catch (Exception e) {
			throw new InvalidFileException(e.getMessage());
		}
	}

	public boolean isEmpty(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
				return !directory.iterator().hasNext();
			}
		}

		return false;
	}

}
