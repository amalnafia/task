package com.example.task.util

import android.content.Context
import android.os.Environment
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


class DownloadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    companion object {
        const val Progress = "Progress"
    }

    override fun doWork(): Result {
        setProgressAsync(Data.Builder().putInt(Progress, 0).build())
        val urlLink = URL(inputData.getString("URL"))
        val imageItem = inputData.getString("URL")
        val fileName = imageItem?.substring(imageItem.lastIndexOf('/') + 1)

        try {


            val c = urlLink.openConnection() as HttpURLConnection
            c.connect()
            val root =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "file")
            if (!root.exists()) {
                root.mkdirs()
            }
            val outputFile = File(root, fileName)
            if (outputFile.exists()) {
                outputFile.delete()
            }
            val fos = FileOutputStream(outputFile)
            val input = c.inputStream
            val buffer = ByteArray(32768)
            var len1 = 0
            val lengthOfFile: Int = c.contentLength
            var total: Long = 0
            while (input.read(buffer).also { len1 = it } != -1) {
                fos.write(buffer, 0, len1)
                total += len1.toLong()
                val percent = (total * 100 / lengthOfFile).toInt()
                setProgressAsync(Data.Builder().putInt(Progress, percent).build())
            }
            fos.close()
            input.close()
            setProgressAsync(Data.Builder().putInt(Progress, 100).build())
            return Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()

        }
    }
}