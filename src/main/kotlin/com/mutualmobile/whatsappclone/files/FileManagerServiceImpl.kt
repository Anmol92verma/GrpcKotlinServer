package com.mutualmobile.whatsappclone.files

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream

class FileManagerServiceImpl :
    FileTransferServiceGrpcKt.FileTransferServiceCoroutineImplBase(coroutineContext = Dispatchers.IO) {
  override suspend fun uploadFile(requests: Flow<FileUploadType>): FileUploadResponseMessage {
    requests.flowOn(Dispatchers.IO).collect {
      writeBytesToFile(it)
    }
    return FileUploadResponseMessage.newBuilder().apply {
      this.success = true
    }.build()
  }

  private fun writeBytesToFile(it: FileUploadType) {
    val fileDir = getFilesDir()

    var fos: FileOutputStream? = null
    try {
      val file = File(fileDir, it.fileName)
      if (!file.exists()) {
        println("mk createNewFile")
        file.createNewFile()
      }
      if (fos == null) {
        fos = FileOutputStream(file)
      }

      fos.write(it.fileData.toByteArray())
    } catch (ex: Exception) {
      ex.printStackTrace()
    } finally {
      fos?.close()
    }
  }

  private fun getFilesDir(): File {
    val cwd = System.getProperty("user.dir")
    println("Current working directory : $cwd")
    val fileDir = File(cwd + File.separator + "images")
    if (!fileDir.exists()) {
      println("mk dirs")
      fileDir.mkdirs()
    }
    return fileDir
  }

  override fun downloadFile(request: FileDownloadRequestMessage): Flow<FileDownloadResponseMessage> {
    return super.downloadFile(request)
  }
}