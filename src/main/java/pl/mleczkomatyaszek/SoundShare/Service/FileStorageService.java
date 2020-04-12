package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final String path = new File("src/main/resources/media").getAbsolutePath();
    private Path uploadPath;

    @PostConstruct
    public void init() {
        this.uploadPath = Paths.get(path);
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public void store (MultipartFile file){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if(file.isEmpty())
            throw new RuntimeException("Failed to store empty file" + filename);

        if(filename.contains(".."))
            throw new RuntimeException("Given filename is not valid " + filename);

        try (InputStream inputStream = file.getInputStream()) {

            Files.copy(inputStream,this.uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Resource loadAsResource(String filename){
        try {

            Path file = uploadPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new RuntimeException("Could not read file: " + filename);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file" + filename, e);
        }

    }

    public Path getUploadPath() {
        return uploadPath;
    }
}
