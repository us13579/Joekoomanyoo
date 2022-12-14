package com.ssafy.heritage.view.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileModifyBinding
import com.ssafy.heritage.util.FileUtil
import com.ssafy.heritage.util.FormDataUtil
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "ProfileModifyFragment___"
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE
)

class ProfileModifyFragment :
    BaseFragment<FragmentProfileModifyBinding>(R.layout.fragment_profile_modify) {

    //    private val profileViewModel by viewModels<ProfileViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()
    lateinit var oldNickname: String
    var img_multipart: MultipartBody.Part? = null

    override fun init() {

        initObserver()

        initClickListener()

        setTextChangedListener()

        setItemSelectedListener()
    }


    @SuppressLint("LongLogTag")
    private fun initObserver() = with(binding) {
        userViewModel.user.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver: $it")
            binding.user = it.copy()

            oldNickname = it.userNickname
            spinnerYear.text = it.userBirth
            spinnerGender.text = when (it.userGender) {
                'M' -> "??????"
                else -> "??????"
            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun initClickListener() = with(binding) {

        // ?????? ?????? ?????? ?????????
        btnChangeProfile.setOnClickListener {

            if (!hasPermissions()) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                pick()
            }
        }

        // ???????????? ?????? ?????????
        btnModify.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {

                // ????????? ???????????? ??????
                if (!userViewModel.nicknameVerify(
                        user!!.userNickname,
                        oldNickname,
                        tilNickname
                    )!!
                ) {
                    return@launch
                }
                Log.d(TAG, "????????? ???????????? ?????? ??????")
                // ???????????? ????????? ?????? ??????
                if (!userViewModel.validatePw(pw, pwCheck, tilPw, tilPwCheck)) {
                    return@launch
                }
                Log.d(TAG, "???????????? ????????? ?????? ??????")

                // ????????? ?????? ???????????? ???????????? ??????
                // ?????? ?????? ????????? ?????? ??????
                if (img_multipart == null || img_multipart?.let { userViewModel.sendImage(it) } == true) {
                    if (userViewModel.modify(user!!, pw) == true) {
                        // ??????????????? ?????????????????? ??????
                        makeToast("???????????? ?????? ??????")
                        findNavController().navigate(R.id.action_profileModifyFragment_to_profileFragment)
                    } else {
                        makeToast("???????????? ????????? ?????????????????????")
                    }
                } else {
                    makeToast("?????? ????????? ??????????????????")
                }
            }
        }
    }

    // ?????? ??????
    private fun pick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActivityLauncher.launch(intent)
    }

    // ?????? ????????? ????????? ??????
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {

                val imagePath = it.data!!.data
                Log.d(TAG, "imagePath: $imagePath")
                binding.ivProfile.setImageURI(imagePath)
                val file = FileUtil.toFile(requireContext(), imagePath!!)
                img_multipart = FormDataUtil.getImageBody("file", file)

            } else if (it.resultCode == RESULT_CANCELED) {
                makeToast("?????? ?????? ??????")
            } else {
                Log.d("ActivityResult", "something wrong")
            }
        }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            pick()
        } else {
            // PERMISSION NOT GRANTED
            makeToast("????????? ?????????")
        }
    }


    private fun setTextChangedListener() = with(binding) {

        // ????????? ????????? ?????? ????????????
        tilNickname.editText?.addTextChangedListener {
            tilNickname.isErrorEnabled = false
        }

        // ???????????? ????????? ?????? ????????????
        tilPw.editText?.addTextChangedListener {
            tilPw.isErrorEnabled = false
        }

        // ???????????? ????????? ????????? ?????? ????????????
        tilPwCheck.editText?.addTextChangedListener {
            tilPwCheck.isErrorEnabled = false
        }
    }

    @SuppressLint("LongLogTag")
    private fun setItemSelectedListener() = with(binding) {

        // ????????? ?????? ????????? ??????
        spinnerYear.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            Log.d(TAG, "setItemSelectedListener: $newItem")
            user?.userBirth = newItem
            spinnerYear.text = newItem
        }

        spinnerGender.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            Log.d(TAG, "setItemSelectedListener: $newItem")
            when (newItem) {
                "??????" -> {
                    user?.userGender = 'M'
                    spinnerGender.text = "??????"
                }
                "??????" -> {
                    user?.userGender = 'F'
                    spinnerGender.text = "??????"
                }
            }
        }
    }

    // ?????? ????????? ??????
    fun hasPermissions() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }


    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}