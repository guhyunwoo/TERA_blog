package org.example.blog.domain.minio.service

import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.http.Method
import org.example.blog.global.config.MinioConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.*


@Service
class MinioService(
    private val minioConfig: MinioConfig,
    @Value("\${minio.url}") private val minioUrl: String,
    @Value("\${minio.default-bucket}") private val defaultBucket: String,
    private val minioClient: MinioClient,
) {
    fun createImageUrl(image: MultipartFile?): String {
        val defaultImage = "${minioUrl}/${defaultBucket}/image.png"
        val imageUrl = image?.let { uploadFile(it) } ?: defaultImage

        return imageUrl
    }

    private fun uploadFile(file: MultipartFile): String{
        val inputStream: InputStream = file.inputStream
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename

        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(defaultBucket)
                .`object`(fileName)
                .stream(inputStream, file.size, -1)
                .contentType(file.contentType)
                .build()
        )

        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .bucket(defaultBucket)
                .`object`(fileName)
                .method(Method.GET)
                .build()
        )
    }
}