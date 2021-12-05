package sad.fit2021.bookstoreproject.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonService {

    private AmazonS3 amazonS3;

    @Value("${amazon.s3.default-bucket}")
    private String bucketName;
    @Value("${amazon.s3.endpoint}")
    private String endpointURL;
    @Value("${amazon.aws.access-key-id}")
    private String accessKey;
    @Value("${amazon.aws.access-key-secret}")
    private String secretKey;

    @PostConstruct
    public void initializeAmazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.amazonS3 = new AmazonS3Client(awsCredentials);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(generateFileName(file));
        FileOutputStream os = new FileOutputStream(convFile);
        os.write(file.getBytes());
        os.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multipartFile) {
        return new Date().getTime() + " " + multipartFile.getOriginalFilename().replace(" ", "-");
    }

    private void uploadFileTos3bucket(String fileName, File file, String specificFolder) {
        amazonS3.putObject(new PutObjectRequest(bucketName + "/" + specificFolder, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile, String specificFolder) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointURL + "/" + bucketName + "/" + specificFolder + "/" + fileName;
            uploadFileTos3bucket(fileName, file, specificFolder);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }
}
