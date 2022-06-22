package com.example.foodmarket.ui.auth.signup

import android.net.Uri
import android.view.View
import com.example.foodmarket.model.request.RegisterRequest
import com.example.foodmarket.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SignupPresenter (private val view: SignupContract.View) : SignupContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun submitRegister(registerRequest: RegisterRequest, viewParms: View) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.register(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password,
            registerRequest.password_confirmation,
            registerRequest.address,
            registerRequest.city,
            registerRequest.houseNumber,
            registerRequest.phoneNumber
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success")) {
                        it.data?.let { data -> view.onRegisterSuccess(data, viewParms) }
                    } else {
                        view.onRegisterFailed(it.meta?.message.toString())
                    }

                },
                {
                    view.dismissLoading()
                    view.onRegisterFailed(it.message.toString())
                })
        mCompositeDisposable!!.add(disposable)
    }

    override fun submitPhotoRegister(filePath: Uri, viewParms: View) {
        view.showLoading()
        var profileImageFile = File(filePath.path)
        val profileImageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), profileImageFile)
        var profileImageParams = MultipartBody.Part.createFormData("file", profileImageFile.name, profileImageRequestBody )


        val disposable = HttpClient.getInstance().getApi()!!.registerPhoto(profileImageParams)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success", true)) {
                        it.data?.let { it1 -> view.onRegisterPhotoSuccess(viewParms) }
                    } else {
                        view.onRegisterFailed(it.meta?.message.toString())
                    }
                },
                {
                    view.dismissLoading()
                    view.onRegisterFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subcribe() {

    }

    override fun unSubcribe() {
        mCompositeDisposable!!.clear()
    }

}