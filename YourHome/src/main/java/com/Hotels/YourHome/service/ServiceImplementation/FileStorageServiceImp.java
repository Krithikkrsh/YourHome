package com.Hotels.YourHome.service.ServiceImplementation;

import com.Hotels.YourHome.config.FileStorageProperties;
import com.Hotels.YourHome.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageServiceImp implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImp(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String saveImg(MultipartFile file) throws IOException {

        File f = new File("C:\\Users\\krshk\\Downloads\\YourHome\\YourHome\\src\\main\\resources\\static"+file.getOriginalFilename());
        var newFile = f.createNewFile();
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(file.getBytes());
        fout.close();
        BufferedImage image = ImageIO.read(f);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains(". ."))
                throw new Exception("Invalid file");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String newFileName = System.currentTimeMillis() + "_" + fileName;
        Path targetLocation = this.fileStorageLocation.resolve(newFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }

    @Override
    public Resource loadImg(String fileName) throws MalformedURLException {

        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }
}
