package com.example.nilpay_registration_android.utils

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import android.util.Log
import java.io.File

fun uploadFileToS3(filePath: String, fileName: String) {
    // Use your AWS keys to authenticate
    val credentials =
        BasicAWSCredentials("DO00PENCDPEDUCJT4M4L", "hLZoQe1Ue3Yfuf88Egis1A+QQV7y7i3ruS6SW54i1tM")

    // Initialize the S3 client with the credentials and the region
    val s3Client = AmazonS3Client(credentials)
    s3Client.setRegion(com.amazonaws.regions.Region.getRegion("fra1"))  // The region you mentioned (fra1)

    // The file you want to upload
    val file = File(filePath)
    val finalFileName =
        "$fileName${System.currentTimeMillis()}.jpg" // Optionally append timestamp for uniqueness

    // Create a PutObjectRequest to upload the file to your S3 bucket
    val request = PutObjectRequest("249startups", finalFileName, file)

    // Upload the file to S3
    try {
        s3Client.putObject(request)
        Log.d("S3", "File uploaded successfully: $finalFileName")
    } catch (e: Exception) {
        Log.e("S3", "Error uploading file: ${e.message}")
    }
}

fun getFileUrl(fileName: String): String {
    // Get the public URL of the uploaded file
    val credentials =
        BasicAWSCredentials("DO00PENCDPEDUCJT4M4L", "hLZoQe1Ue3Yfuf88Egis1A+QQV7y7i3ruS6SW54i1tM")
    val s3Client = AmazonS3Client(credentials)
    s3Client.setRegion(com.amazonaws.regions.Region.getRegion("fra1"))  // The region you mentioned (fra1)

    val url = s3Client.getUrl("249startups", fileName)
    return url.toString()
}
