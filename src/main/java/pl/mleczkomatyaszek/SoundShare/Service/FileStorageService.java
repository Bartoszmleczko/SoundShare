package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Repository.UserRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {

    private final String path = new File("src/main/resources/media").getAbsolutePath();

    private Path uploadPath;


    public void initDir(String username) {
            this.uploadPath = Paths.get(path + File.separator +username);
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage", e);
            }
        }


    public void store (MultipartFile file,String name){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if(file.isEmpty())
            throw new RuntimeException("Failed to store empty file" + filename);

        if(filename.contains(".."))
            throw new RuntimeException("Given filename is not valid " + filename);

        try (InputStream inputStream = file.getInputStream()) {

            Path path2 = Paths.get(this.path);


            Files.copy(inputStream,path2.resolve(name).resolve(filename), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Path getUploadPath() {
        return uploadPath;
    }

    public String getPath() {
        return path;
    }
}
