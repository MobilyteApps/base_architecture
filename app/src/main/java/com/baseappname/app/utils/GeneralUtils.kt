package com.baseappname.app.utils

import android.app.Activity
import android.app.AlertDialog
import com.baseappname.app.BuildConfig
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListPopupWindow
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.baseappname.app.R
import com.baseappname.app.databinding.DialogAppThemeBinding
import com.baseappname.app.utils.Constants.JPEG_FILE_PREFIX
import com.baseappname.app.utils.Constants.PDF_FILE_PREFIX
import com.baseappname.app.utils.Constants.PDF_FILE_SUFFIX
import com.baseappname.app.utils.DateTimeUtils.milliToDate
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
object GeneralFunctions {

    fun Int.addDecimal(): String {
        val formatter: NumberFormat = DecimalFormat("#0.00")
        return formatter.format(this)
    }

    /**
     * With dollar
     *
     * @return Amount with $
     */
    fun String.withDollar(): String {
        return "$$this"
    }


    @Throws(IOException::class)
    fun setUpPDFFile(directory: String): File? {
        var pdfFile: File? = null
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val storageDir = File(directory)
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("PDF", "failed to create directory")
                    return null
                }
            }

            pdfFile = File.createTempFile(
                PDF_FILE_PREFIX + System.currentTimeMillis() + "_", PDF_FILE_SUFFIX, storageDir
            )
        }
        return pdfFile
    }

    @Throws(IOException::class)
    fun setUpImageFile(directory: String): File? {
        var imageFile: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                .getExternalStorageState()
        ) {
            val storageDir = File(directory)
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("BasePicture", "failed to create directory")
                    return null
                }
            }
            createNoMedia()
            imageFile = File.createTempFile(
                JPEG_FILE_PREFIX
                        + System.currentTimeMillis() + "_",
                Constants.JPEG_FILE_SUFFIX, storageDir
            )
        }
        return imageFile
    }

    private fun createNoMedia() {

        val filepath = Constants.LOCAL_STORAGE_BASE_PATH_FOR_MEDIA + "/"

        val file = File("$filepath.nomedia")
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * Function to hide keyboard from view
     */
    fun hideSoftKeyboard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getFileName(path: String): String {
        return path.substring(path.lastIndexOf("/") + 1)
    }


}

/**
 * Easy to notify in logcat
 */
fun easyPrinter(message: String, className: String, obj: Any? = null) {
    if (BuildConfig.DEBUG) {
        println("Easy_Printer---------------------Start-------------------------------")
        if (obj != null) {
            println("Easy_Printer-$className----->$message<---->$obj")
        } else {
            println("Easy_Printer-$className---->$message")
        }
        println("Easy_Printer---------------------End---------------------------------")
    }
}

fun getStringFromLocalJson(assetsFileName: String?, activity: Activity): String? {
    val sb = StringBuffer()
    var br: BufferedReader? = null
    try {
        br = BufferedReader(InputStreamReader(activity.assets.open(assetsFileName!!)))
        var temp: String?
        while (br.readLine().also { temp = it } != null) sb.append(temp)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            br!!.close() // stop reading
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return sb.toString()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.openListPopupWindow(list: List<Any>, callback: GeneralCallback) {

    // initialize listPopWindow
    val listPopupWindow = ListPopupWindow(this.context)

    // set adapter
    val adapter = ArrayAdapter<Any>(this.context, R.layout.simple_list_item, list)
    listPopupWindow.setAdapter(adapter)

    adapter.notifyDataSetChanged()

    // set anchor and modal
    listPopupWindow.anchorView = this
    listPopupWindow.isModal = true

    listPopupWindow.width = this.width

    listPopupWindow.setOnItemClickListener { _, _, position, _ ->
        callback.onItemSelectedFromList(list[position], position)
        listPopupWindow.dismiss()
    }
    listPopupWindow.show()
}

fun Fragment.openMaterialDatePicker(callback: GeneralCallback) {

    val builder = MaterialDatePicker.Builder.datePicker()

    val picker = builder.build()

    picker.show(this.requireActivity().supportFragmentManager, picker.toString())


    /**
     * MaterialDatePicker Callback
     */
    picker.addOnCancelListener {

    }
    picker.addOnDismissListener {

    }

    picker.addOnPositiveButtonClickListener {
        val date = it as Long
        callback.onDatePicked(date.milliToDate("mm/dd/yyyy"), it)
        picker.dismiss()
    }
    picker.addOnNegativeButtonClickListener {

    }
}

fun Fragment.openMaterialDatePickerCurrentDate(callback: GeneralCallback) {
    val c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    c.set(
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    );

    val cc: CalendarConstraints = CalendarConstraints.Builder().setStart(c.timeInMillis).build()
    val builder = MaterialDatePicker.Builder.datePicker()
    val picker = builder.setCalendarConstraints(cc).build()
    picker.show(this.requireActivity().supportFragmentManager, picker.toString())


    /**
     * MaterialDatePicker Callback
     */
    picker.addOnCancelListener {

    }
    picker.addOnDismissListener {

    }

    picker.addOnPositiveButtonClickListener {
        val date = it as Long
        callback.onDatePicked(date.milliToDate("mm/dd/yyyy"), it)
        picker.dismiss()
    }
    picker.addOnNegativeButtonClickListener {

    }
}

/**
 *extension  fun to make any view visible
 *
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 *extension  fun to make any view's visibility to gone
 *
 */
fun View.hide() {
    visibility = View.GONE
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Extension function to get number from a known format i.e.  XXX-XXX-XXXX to normal XXXXXXXXXX
 *
 * @return
 */
fun EditText.getDigitsFromFormattedNumber(): String {
    val number = StringBuffer()
    this.text.toString().forEach {
        if (it.isDigit()) number.append(it)
    }
    return number.toString()
}

var mAppThemeDialog: AlertDialog? = null

/**
 * general purpose dialog for used anywhere in the app
 *
 * @param singleActionButtonText
 * @param dialogMessage
 * @param dialogTitle
 * @param dialogIcon
 * @param ctaColor
 * @param callBack
 */
fun Activity.showAppThemeDialog(
    singleActionButtonText: String?, dialogMessage: String, dialogTitle: String = "Prescribery",
    @DrawableRes dialogIcon: Int?, negativeText: String = "No",
    positiveText: String = "Yes", callBack: GeneralCallback
) {
    if (mAppThemeDialog != null && mAppThemeDialog?.isShowing == true) {
        mAppThemeDialog?.dismiss()
        return
    }
    if (mAppThemeDialog != null && mAppThemeDialog?.isShowing == true) {
        mAppThemeDialog?.dismiss()
    }
    val binding: DialogAppThemeBinding =
        DataBindingUtil.inflate(this.layoutInflater, R.layout.dialog_app_theme, null, false)
    val builder: AlertDialog.Builder?
    builder = AlertDialog.Builder(this)
    //set view
    builder.setView(binding.root)

    // set icon
    dialogIcon?.let {
        binding.tvIcon.show()
        binding.tvIcon.setImageResource(dialogIcon)
    }

    // set title
    binding.tvDialogTitle.text = dialogTitle

    // set message
    binding.tvAppDialogMessage.text = dialogMessage

    // set actions
    if (singleActionButtonText != null) {
        binding.btnSingleButtonText.show()
        binding.btnSingleButtonText.text = singleActionButtonText
        binding.llDualAction.hide()
    } else {
        binding.llDualAction.show()
        binding.btnAppDialogPositive.text = positiveText
        binding.btnAppDialogNegative.text = negativeText
        binding.btnSingleButtonText.hide()
    }


    //set positive button click
    binding.btnAppDialogPositive.setOnClickListener {
        callBack.onPositiveClick()
        mAppThemeDialog!!.dismiss()
    }
    //set negative button click
    binding.btnAppDialogNegative.setOnClickListener {
        callBack.onNegativeClick()
        mAppThemeDialog!!.dismiss()
    }
    binding.btnSingleButtonText.setOnClickListener {
        callBack.onPositiveClick()
        mAppThemeDialog!!.dismiss()
    }

    builder.setCancelable(false)
    mAppThemeDialog = builder.create()
    if (mAppThemeDialog!!.window != null) {
        mAppThemeDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mAppThemeDialog?.show()

    }
}


private const val MULTIPART_FORM_DATA = "multipart/form-data"

fun String.stringToRequestBody() =
    this.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())


fun File.fileToRequestBody(file: File) =
    this.asRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())


fun File.fileToNamedMultiPart(partName: String, fileName: String): MultipartBody.Part {
    // create RequestBody instance from file
    val requestFile = this.asRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())
    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, fileName, requestFile)
}

private fun removeLastComma(string: String): String {
    return if (string.endsWith(",")) {
        string.substring(0, string.length - 1)
    } else {
        string
    }
}