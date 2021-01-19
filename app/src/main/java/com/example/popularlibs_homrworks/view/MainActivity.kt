package com.example.popularlibs_homrworks.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.presenter.MainPresenter
import com.example.popularlibs_homrworks.presenter.MainPresenterImpl
import com.example.popularlibs_homrworks.presenter.MainView
import com.example.popularlibs_homrworks.repository.RepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


const val TAG = "33333"

//при открытии приложения пишем jpg файл из  drawable на  sd карту, выводим тост если успешно
//по щелчку на кнопке читаем jpg файл, показываем на экране , конвертируем в PNG и пишем обратно
//выводим тосты если конвертация и запись прошли успешно
class MainActivity : AppCompatActivity(), MainView {
    //Создание презентера можно вынести это в класс-инжектор с методом inject
    private val presenter: MainPresenter = MainPresenterImpl(this, RepositoryImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_counter1.setOnClickListener {
            presenter.readJPG()
            presenter.convertJPG_toPNG()
        }
    }

    override fun onResume() {
        super.onResume()
       //записываем файл из drawable на sd карту
        presenter.saveJPGfile()
    }

    override fun saveJPGsuccess_Toast() {
        Toast.makeText(this, " Сохранено в файл в формате JPEG", Toast.LENGTH_LONG ).show()
    }

    override fun showJPGerror(message:String) {
        Toast.makeText(this, "НЕ сохранено: $message", Toast.LENGTH_LONG ).show()
    }

    override fun showError(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG ).show()
    }

    override fun showPNGsuccess_Toast() {
        Toast.makeText(this, " Сохранено в файл в формате PNG", Toast.LENGTH_LONG ).show()
    }

    override fun showJPGimage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
        Log.d(TAG, "MainActivity showJPGimage: ")
    }
}