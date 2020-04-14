package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczkomatyaszek.SoundShare.Model.SongModel;
import pl.mleczkomatyaszek.SoundShare.Service.FileStorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

@RestController
public class FileUploadController {


    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile (@RequestParam("file") MultipartFile uploadedFile,@RequestParam("title") String title,@RequestParam("rate") Integer rate) throws IOException {

        //fileStorageService.store(uploadedFile.getMultipartFile());
        return new ResponseEntity<Object>("Uploaded successfully",HttpStatus.OK);

    }



}
