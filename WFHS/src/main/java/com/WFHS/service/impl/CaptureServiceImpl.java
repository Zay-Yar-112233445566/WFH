package com.WFHS.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.WFHS.dto.CaptureDto;
import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.Capture;
import com.WFHS.repository.CaptureRepository;
import com.WFHS.service.CaptureService;

@Service
public class CaptureServiceImpl implements CaptureService {

	@Autowired
	private CaptureRepository repo;

	@Override
	public void saveCapture(CaptureDto dto, ApplicantForm form) {
		if (dto.getOsType().equals("window")) {
			MultipartFile osTypeImg = dto.getWindowOsTypeImg();
			MultipartFile securityPatchUpdateImg = dto.getWindowSecurityPatchUpdateImg();
			MultipartFile antivirusSoftwareImg = dto.getWindowAntivirusSoftwareImg();
			MultipartFile antivirusPatternUpdatedImg = dto.getAntivirusPatternUpdatedImg();
			MultipartFile antivirusFullScanImg = dto.getAntivirusFullScanImg();

			Date createdAt = new Date();
			String osTypeImgName = createdAt.getTime() + "_" + osTypeImg.getOriginalFilename();
			String securityPatchUpdateImgName = createdAt.getTime() + "_"+ securityPatchUpdateImg.getOriginalFilename();
			String antivirusSoftwareImgName = createdAt.getTime() + "_" + antivirusSoftwareImg.getOriginalFilename();
			String antivirusPatternUpdatedImgName = createdAt.getTime() + "_"+ antivirusPatternUpdatedImg.getOriginalFilename();
			String antivirusFullScanImgName = createdAt.getTime() + "_" + antivirusFullScanImg.getOriginalFilename();

			try {
				String uploadDir = "public/images/";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try {
					MultipartFile[] imageFiles = { osTypeImg, securityPatchUpdateImg, antivirusSoftwareImg,
							antivirusPatternUpdatedImg, antivirusFullScanImg };

					for (MultipartFile imageFile : imageFiles) {
						try (InputStream inputStream = imageFile.getInputStream()) {
							Files.copy(inputStream,
									Paths.get(uploadDir + createdAt.getTime() + "_" + imageFile.getOriginalFilename()),
									StandardCopyOption.REPLACE_EXISTING);
						}
					}
				} catch (IOException ex) {
					System.out.println("Exception : " + ex.getMessage());
				}

			} catch (Exception ex) {
				System.out.println("Exception : " + ex.getMessage());
			}

			// Save to Window Capture
			Capture capture = new Capture();
			capture.setApplicantForm(form);
			capture.setOsType(dto.getOsType());
			capture.setOsTypeImg(osTypeImgName);
			capture.setSecurityPatchUpdateImg(securityPatchUpdateImgName);
			capture.setAntivirusSoftwareImg(antivirusSoftwareImgName);
			capture.setAntivirusPatternUpdatedImg(antivirusPatternUpdatedImgName);
			capture.setAntivirusFullScanImg(antivirusFullScanImgName);
			repo.save(capture);
		}

		if (dto.getOsType().equals("mac")) {
			MultipartFile macOsTypeImg = dto.getMacOsTypeImg();
			MultipartFile macSecurityPatchUpdateImg = dto.getMacSecurityPatchUpdateImg();
			MultipartFile macAntivirusSoftwareImg = dto.getMacAntivirusSoftwareImg();

			Date createdAt = new Date();
			String osTypeImgName = createdAt.getTime() + "_" + macOsTypeImg.getOriginalFilename();
			String securityPatchUpdateImgName = createdAt.getTime() + "_"+ macSecurityPatchUpdateImg.getOriginalFilename();
			String antivirusSoftwareImgName = createdAt.getTime() + "_" + macAntivirusSoftwareImg.getOriginalFilename();

			try {
				String uploadDir = "public/images/";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try {
					MultipartFile[] imageFiles = { macOsTypeImg, macSecurityPatchUpdateImg, macAntivirusSoftwareImg };

					for (MultipartFile imageFile : imageFiles) {
						try (InputStream inputStream = imageFile.getInputStream()) {
							Files.copy(inputStream,
									Paths.get(uploadDir + createdAt.getTime()+"_"+ imageFile.getOriginalFilename()),
									StandardCopyOption.REPLACE_EXISTING);
						}
					}
				} catch (IOException ex) {
					System.out.println("Exception : " + ex.getMessage());
				}

			} catch (Exception ex) {
				System.out.println("Exception : " + ex.getMessage());
			}
			// Save to Mac Capture
			Capture capture = new Capture();
			capture.setApplicantForm(form);
			capture.setOsType(dto.getOsType());
			capture.setOsTypeImg(osTypeImgName);
			capture.setSecurityPatchUpdateImg(securityPatchUpdateImgName);
			capture.setAntivirusSoftwareImg(antivirusSoftwareImgName);
			repo.save(capture);

		}
		if (dto.getOsType().equals("linux")) {
			MultipartFile linuxOsTypeImg = dto.getLinuxOsTypeImg();
			MultipartFile linuxSecurityPatchUpdateImg = dto.getLinuxSecurityPatchUpdateImg();
			MultipartFile linuxAntivirusSoftwareImg = dto.getLinuxAntivirusSoftwareImg();

			Date createdAt = new Date();
			String osTypeImgName = createdAt.getTime() + "_" + linuxOsTypeImg.getOriginalFilename();
			String securityPatchUpdateImgName = createdAt.getTime() + "_"+ linuxSecurityPatchUpdateImg.getOriginalFilename();
			String antivirusSoftwareImgName = createdAt.getTime() + "_"+ linuxAntivirusSoftwareImg.getOriginalFilename();

			try {
				String uploadDir = "public/images/";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try {
					MultipartFile[] imageFiles = { linuxOsTypeImg, linuxSecurityPatchUpdateImg,
							linuxAntivirusSoftwareImg };

					for (MultipartFile imageFile : imageFiles) {
						try (InputStream inputStream = imageFile.getInputStream()) {
							Files.copy(inputStream,
									Paths.get(uploadDir + createdAt.getTime() + "_" + imageFile.getOriginalFilename()),
									StandardCopyOption.REPLACE_EXISTING);
						}
					}
				} catch (IOException ex) {
					System.out.println("Exception : " + ex.getMessage());
				}

			} catch (Exception ex) {
				System.out.println("Exception : " + ex.getMessage());
			}
			// Save to Mac Capture
			Capture capture = new Capture();
			capture.setApplicantForm(form);
			capture.setOsType(dto.getOsType());
			capture.setOsTypeImg(osTypeImgName);
			capture.setSecurityPatchUpdateImg(securityPatchUpdateImgName);
			capture.setAntivirusSoftwareImg(antivirusSoftwareImgName);
			repo.save(capture);
		}
	}

	@Override
	public void updateCapture(CaptureDto dto, int id) {
		Date createdAt = new Date();
		Capture capture = repo.findById(id);
		if (capture == null) {
			throw new IllegalArgumentException("Capture not found with id: " + id);
		}
		String uploadDir = "public/images/"; // Define uploadDir here
		

		
		if (capture.getOsType().equals("window")) {
			 try {
				 	//For Os Type Image Saving
		            if (!dto.getWindowOsTypeImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getOsTypeImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getOsTypeImg());
		                    Files.delete(oldImagePath);
		                // Save new image file
		                MultipartFile image = dto.getWindowOsTypeImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir + createdAt.getTime() + "_" + storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setOsTypeImg(createdAt.getTime() +"_"+storageFileName);
		            }
		                }
		            
		            //For Security Patch Update Img Saving
		            
		            if (!dto.getWindowSecurityPatchUpdateImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getSecurityPatchUpdateImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getSecurityPatchUpdateImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getWindowSecurityPatchUpdateImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setSecurityPatchUpdateImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            
		            //For Anti-virus Software  Img Saving
		            if (!dto.getWindowAntivirusSoftwareImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getAntivirusSoftwareImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusSoftwareImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getWindowAntivirusSoftwareImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setAntivirusSoftwareImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            
		            //For Anti-virus Pattern Updated Img Saving
		            if (!dto.getAntivirusPatternUpdatedImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getAntivirusPatternUpdatedImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusPatternUpdatedImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getAntivirusPatternUpdatedImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setAntivirusPatternUpdatedImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            
		          //For Anti-virus Full Scan Img Saving
		            if (!dto.getAntivirusFullScanImg().isEmpty()) {
		            	
		            	// Delete old image file if imageFileName is not null
		                if (capture.getAntivirusFullScanImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusFullScanImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getAntivirusFullScanImg();   
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setAntivirusFullScanImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            repo.save(capture);
		        } 
		            catch (Exception ex) {
		            System.out.println("IOException occurred: " + ex.getMessage());
		           }
		}
		
		if (capture.getOsType().equals("mac")) {
			 try {
				 	//For Os Type Image Saving
		            if (!dto.getMacOsTypeImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getOsTypeImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getOsTypeImg());
		                    Files.delete(oldImagePath);
		                // Save new image file
		                MultipartFile image = dto.getMacOsTypeImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir + createdAt.getTime() + "_" + storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setOsTypeImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            }
		            
		            //For Security Patch Update Img Saving
		            if (!dto.getMacSecurityPatchUpdateImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getSecurityPatchUpdateImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getSecurityPatchUpdateImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getMacSecurityPatchUpdateImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setSecurityPatchUpdateImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            
		            //For Anti-virus Software  Img Saving
		            if (!dto.getMacAntivirusSoftwareImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getAntivirusSoftwareImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusSoftwareImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getMacAntivirusSoftwareImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setAntivirusSoftwareImg(createdAt.getTime() +"_"+storageFileName);
		            }        
		            repo.save(capture);
		        } catch (Exception ex) {
		            System.out.println("IOException occurred: " + ex.getMessage());
		        }

		}
		if (capture.getOsType().equals("linux")) {
			 try {
				 	 //For Os Type Image Saving
		            if (!dto.getLinuxOsTypeImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getOsTypeImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getOsTypeImg());
		                    Files.delete(oldImagePath);
		                // Save new image file
		                MultipartFile image = dto.getLinuxOsTypeImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir + createdAt.getTime() + "_" + storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setOsTypeImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            }
		            
		            //For Security Patch Update Img Saving
		            if (!dto.getLinuxSecurityPatchUpdateImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getSecurityPatchUpdateImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getSecurityPatchUpdateImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getLinuxSecurityPatchUpdateImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setSecurityPatchUpdateImg(createdAt.getTime() +"_"+storageFileName);
		            }
		            
		            //For Anti-virus Software  Img Saving
		            if (!dto.getLinuxAntivirusSoftwareImg().isEmpty()) {
		                // Delete old image file if imageFileName is not null
		                if (capture.getAntivirusSoftwareImg() != null) {
		                    Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusSoftwareImg());
		                    Files.delete(oldImagePath);
		                }
		                // Save new image file
		                MultipartFile image = dto.getLinuxAntivirusSoftwareImg();
		                String storageFileName = image.getOriginalFilename();
		                try (InputStream inputStream = image.getInputStream()) {
		                    Files.copy(inputStream, Paths.get(uploadDir +createdAt.getTime() +"_"+ storageFileName),
		                            StandardCopyOption.REPLACE_EXISTING);
		                }
		                capture.setAntivirusSoftwareImg(createdAt.getTime() +"_"+storageFileName);
		            }        
		            repo.save(capture);
		        } catch (Exception ex) {
		            System.out.println("IOException occurred: " + ex.getMessage());
		        }

		}
	}

}
