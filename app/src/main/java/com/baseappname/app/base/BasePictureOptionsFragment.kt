package com.baseappname.app.base


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.baseappname.app.R
import com.baseappname.app.utils.*

import com.baseappname.app.utils.Constants.LOCAL_STORAGE_BASE_PATH_FOR_DOCS_IMG
import com.baseappname.app.utils.Constants.LOCAL_STORAGE_BASE_PATH_FOR_DOCS_PDF
import kotlinx.android.synthetic.main.show_picture_options_bottom_sheet.view.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.InputStream


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
abstract class BasePictureOptionsFragment<T : ViewDataBinding, V : BaseViewModel<out BaseViewActor>>
    : BaseFragment<T, V>(), GetSampledImage.SampledImageAsyncResp,
    EasyPermissions.PermissionCallbacks {

    companion object {
        private const val GALLERY_REQUEST = 234
        private const val DOC_REQUEST = 235
        private const val CAMERA_REQUEST = 23
        private const val CAMERA = 0
        private const val GALLERY = 1
        private const val DOC = 2
    }

    private var picturePath: String? = null
    private var imagesDirectory: String? = null
    private var isCameraOptionSelected: Int = -1 //0 for camera;1 for gallery;2 for file


    /**
     * Show picture options bottom sheet for selecting image from gallery
     * and capturing image from camera
     *
     * @param imagesDirectory image directory to save copy of image
     */
    fun showPictureOptionsBottomSheet(imagesDirectory: String) {

        val bottomSheetDialog = BottomSheetDialog(baseActivity!!)
        val view = (view as ViewGroup).inflate(R.layout.show_picture_options_bottom_sheet)

        view.tvDoc.hide()

        view.tvCamera.setOnClickListener {
            checkForPermissions(CAMERA, imagesDirectory)
            bottomSheetDialog.dismiss()
        }
        view.tvGallery.setOnClickListener {
            checkForPermissions(GALLERY, imagesDirectory)
            bottomSheetDialog.dismiss()
        }
        view.tvCancel.setOnClickListener { bottomSheetDialog.dismiss() }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    /**
     * Show doc options bottom sheet for selecting document from file explorer
     *
     * @param imagesDirectory image directory to create copy of file
     */
    fun showDocOptionsBottomSheet(imagesDirectory: String) {

        val bottomSheetDialog = BottomSheetDialog(baseActivity!!)
        val view = (view as ViewGroup).inflate(R.layout.show_picture_options_bottom_sheet)

        view.tvCamera.hide()
        view.tvGallery.hide()

        view.tvDoc.setOnClickListener {
            checkForPermissions(DOC, imagesDirectory)
            bottomSheetDialog.dismiss()
        }
        view.tvGallery.setOnClickListener {
            checkForPermissions(GALLERY, imagesDirectory)
            bottomSheetDialog.dismiss()
        }

        view.tvCancel.setOnClickListener { bottomSheetDialog.dismiss() }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    /**
     * Check for permissions before performing any action on camera or storage
     *
     * @param isCameraOptionSelected which option is selected by user is it gallery or camera
     * @param imagesDirectory image directory path
     */
    private fun checkForPermissions(isCameraOptionSelected: Int, imagesDirectory: String) {
        this.isCameraOptionSelected = isCameraOptionSelected
        this.imagesDirectory = imagesDirectory

        if (EasyPermissions.hasPermissions(
                baseActivity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            when (isCameraOptionSelected) {
                CAMERA -> {
                    if (EasyPermissions.hasPermissions(
                            baseActivity!!,
                            Manifest.permission.CAMERA
                        )
                    ) {
                        startCameraIntent()
                    } else {
                        // Request one permission
                        EasyPermissions.requestPermissions(
                            this,
                            getString(R.string.permission_message_camera),
                            Constants.RC_CAMERA_PERMISSION,
                            Manifest.permission.CAMERA
                        )
                    }

                }
                GALLERY -> {
                    openGallery()
                }
                DOC -> {
                    openFileExplorer()
                }
            }
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(
                this, getString(R.string.permission_message_storage),
                Constants.RC_WRITE_STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    /**
     * Open gallery for image selection
     *
     */
    private fun openGallery() {
        startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), GALLERY_REQUEST
        )
    }

    /**
     * Open file explorer for file selection
     *
     */
    private fun openFileExplorer() {

        val mimeTypes =
            arrayOf("image/*", "application/pdf")

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
        if (mimeTypes.isNotEmpty()) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }

        startActivityForResult(intent, DOC_REQUEST)

    }

    /**
     * Start camera intent to capture image
     *
     */
    private fun startCameraIntent() {
        val takePictureIntent = Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        )
        try {
            val file = GeneralFunctions.setUpImageFile(imagesDirectory!!)
            picturePath = file!!.absolutePath

            val outputUri = FileProvider.getUriForFile(
                requireActivity(),
                requireActivity().packageName + ".provider",
                file
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        } catch (e: Exception) {
            e.printStackTrace()
            picturePath = null
        }
        startActivityForResult(takePictureIntent, CAMERA_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                GALLERY_REQUEST, CAMERA_REQUEST -> {
                    processImageFile(data, requestCode)
                }
                DOC_REQUEST -> {
                    val uri = data?.data
                    if (null == uri) {
                        return
                    } else {
                        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(
                            requireActivity().contentResolver.getType(uri)
                        )

                        requireActivity().contentResolver.openInputStream(uri)
                            ?.let { inputStream ->
                                // Generate output pdf file
                                val docFile =
                                    if (extension?.contains("pdf") == true) GeneralFunctions.setUpPDFFile(
                                        LOCAL_STORAGE_BASE_PATH_FOR_DOCS_PDF
                                    )
                                    else GeneralFunctions.setUpImageFile(
                                        LOCAL_STORAGE_BASE_PATH_FOR_DOCS_IMG
                                    )


                                val docFilePath = docFile?.absolutePath

                                if (null != docFilePath) {
                                    // Write inputStream to output file path
                                    inputStream.toFile(docFilePath)
                                    onGettingFile(docFile)
                                }
                            }

                    }
                }
            }
        }
    }

    /**
     * Get image path and down sample image to save storage space
     *
     * @param data data received in activity result
     * @param isGalleryRequest
     */
    private fun processImageFile(data: Intent?, isGalleryRequest: Int) {
        var isGalleryImage = false
        if (isGalleryRequest == GALLERY_REQUEST) {
            val selectedImage = data!!.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(
                selectedImage!!,
                filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            picturePath = cursor.getString(columnIndex)
            cursor.close()
            isGalleryImage = true
        }

        // Downsample image
        GetSampledImage(this).execute(
            picturePath, imagesDirectory,
            isGalleryImage.toString(),
            resources.getDimension(R.dimen.image_downsample_size).toInt().toString()
        )
    }

    override fun onSampledImageAsyncPostExecute(file: File) {
        onGettingImageFile(file)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            Constants.RC_CAMERA_PERMISSION, Constants.RC_WRITE_STORAGE_PERMISSION -> if (EasyPermissions.somePermissionPermanentlyDenied(
                    this,
                    perms
                )
            ) {
                AppSettingsDialog.Builder(this).build().show()
            } else {
                showToast(resId = R.string.permission_denied)
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == Constants.RC_WRITE_STORAGE_PERMISSION) {
            when (isCameraOptionSelected) {
                CAMERA -> {
                    if (EasyPermissions.hasPermissions(
                            baseActivity!!,
                            Manifest.permission.CAMERA
                        )
                    ) {
                        startCameraIntent()
                    } else {
                        // Request one permission
                        EasyPermissions.requestPermissions(
                            this,
                            getString(R.string.permission_message_camera),
                            Constants.RC_CAMERA_PERMISSION,
                            Manifest.permission.CAMERA
                        )
                    }
                }
                GALLERY -> {
                    openGallery()
                }
            }
        } else if (requestCode == Constants.RC_CAMERA_PERMISSION && isCameraOptionSelected == CAMERA) {
            startCameraIntent()
        }
    }

    abstract fun onGettingFile(file: File)
    abstract fun onGettingImageFile(file: File)

    private fun InputStream.toFile(path: String) {
        File(path).outputStream().use { this.copyTo(it) }
    }
}