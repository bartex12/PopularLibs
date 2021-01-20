package com.example.popularlibs_homrworks.view

import android.net.Uri
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


const val TAG = "33333"

//при открытии приложения пишем jpg файл из  drawable на  sd карту, выводим Toast если успешно
//по щелчку на кнопке читаем jpg файл, показываем на экране , конвертируем в PNG и пишем обратно
//выводим Toast, если конвертация и запись прошли успешно
class MainActivity : AppCompatActivity(), MainView {

    companion object{
        const val IMAGE_URI = "IMAGE_URI"
        const val JPG_FILE = "tree.jpg"
        const val PNG_FILE = "tree.png"
    }

    private var uri:Uri? = null
    private val presenter: MainPresenter = MainPresenterImpl(this, RepositoryImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?. let{
            val stringUri = savedInstanceState.getString(IMAGE_URI)
            tv_1.text = stringUri

            uri = Uri.parse(stringUri)
            uri?. let{imageView.setImageURI(uri)}
        }

        btn_convert.setOnClickListener {
            presenter.readAndShowJPG() //читаем tree.jpg и показываем на экране
            presenter.convertJPG_toPNG() //конвертируем jpg в png и пишем на sd карту в tree.png
            presenter.showFilePath(PNG_FILE) //показываем путь к tree.png
        }

        presenter.saveJPGfile() //записываем файл из drawable на sd карту в файл tree.jpg
        presenter.showFilePath(JPG_FILE)//показываем путь к tree.jpg
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(IMAGE_URI, uri.toString())
    }

    override fun showError(message: String) {
        Toast.makeText(this, resources.getString(R.string.error)+message, Toast.LENGTH_LONG ).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT ).show()
    }

    override fun showPath(uriString: String) {
       // Log.d(TAG, "MainActivity showPath $uriString")
        tv_1.text = uriString
    }

    override fun showJPGimage(uriString: String) {
        uri  = Uri.parse(uriString)
        imageView.setImageURI(uri)

    }
}