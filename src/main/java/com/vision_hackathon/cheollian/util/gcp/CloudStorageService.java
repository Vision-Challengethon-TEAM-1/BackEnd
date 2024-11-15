package com.vision_hackathon.cheollian.util.gcp;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Component
public class CloudStorageService {

    @Value("${cloud.project-id}")
    private String projectId;

    @Value("${cloud.bucket.name}")
    private String bucketName;

    public String uploadObject(MultipartFile image) throws IOException {
        String uuid = UUID.randomUUID().toString();
        Storage storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .build()
                .getService();
        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        if (image.isEmpty()) {
            imgUrl = null;
        } else {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType("image/jpg").build();
            Blob blob = storage.create(blobInfo, image.getInputStream());
        }

        return imgUrl;
    }
}
