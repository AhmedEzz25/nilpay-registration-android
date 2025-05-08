package com.example.nilpay_registration_android

import android.app.Application
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        // Initialize AWS SDK with Cognito credentials
        val credentialsProvider = CognitoCachingCredentialsProvider(
            applicationContext,
            getString(R.string.aws_cognito_identity_pool_id),
            Regions.US_EAST_1 // your region
        )

        // Initialize the AWSMobileClient (optional, if using Cognito)
//        AWSMobileClient.getInstance().initialize(applicationContext).execute()

        // Initialize the S3 client
        val s3Client = AmazonS3Client(credentialsProvider)
    }

}