package project.everytime.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file")
public class ImageApiController {

    @Value("${file.dir}")
    public String fileDir;

    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String fileName) {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String filePath = fileDir + fileName;
        try {
            fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;
        try {
            while ((readCount = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, readCount);
            }
            fileArray = byteArrayOutputStream.toByteArray();
            fileInputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("File Error");
        }
        return fileArray;
    }

}
