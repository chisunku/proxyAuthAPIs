package com.sjsu.proxyAuth.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> listObjects() {
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    public void uploadFile(MultipartFile file, String file_name) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/png"); // Set the ContentType to image/png
        metadata.setContentLength(file.getSize());
        s3Client.putObject(new PutObjectRequest(bucketName, file_name, file.getInputStream(), metadata));
    }

    public S3Object downloadFile(String fileName) {
        return s3Client.getObject(new GetObjectRequest(bucketName, fileName));
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    //get bucket name
    public String getBucketName() {
        return bucketName;
    }

    //get region
    public String getRegion() {
        return s3Client.getBucketLocation(bucketName);
    }
}