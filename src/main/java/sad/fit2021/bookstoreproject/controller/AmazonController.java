package sad.fit2021.bookstoreproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sad.fit2021.bookstoreproject.service.AmazonService;

@RestController
@RequestMapping("/api/storage")
public class AmazonController {

    @Autowired
    private AmazonService amazonService;

    @PostMapping("/uploadUserAvt")
    public String uploadUserAvt(@RequestPart(value = "file") MultipartFile file) {
        return amazonService.uploadFile(file, "user");
    }

    @PostMapping("/uploadBookThumbail")
    public String uploadBookThumbnail(@RequestPart(value = "file") MultipartFile file) {
        return amazonService.uploadFile(file, "book");
    }

}
