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
    val cwd = System.getProperty("user.dir")
    println("Current working directory : $cwd")
    requests.flowOn(Dispatchers.IO).collect {
      try{

        val fileDir = File(cwd + File.separator + "images")
        if (!fileDir.exists()) {
          println("mk dirs")
          fileDir.mkdirs()
        }
        val file = (File(fileDir, it.fileName))
        if (!file.exists()) {
          println("mk createNewFile")
          file.createNewFile()
        }
        val fos = FileOutputStream(file)
        fos.write(it.fileData.toByteArray(), file.length().toInt(),it.fileData.size())
        println("writing byte ${it.fileData}")

        fos.close()
      }catch (ex:Exception){
        ex.printStackTrace()
      }
    }
    println("written all bytes")
    return FileUploadResponseMessage.newBuilder().apply {
      this.success = true
    }.build()
  }

  override fun downloadFile(request: FileDownloadRequestMessage): Flow<FileDownloadResponseMessage> {
    return super.downloadFile(request)
  }
}