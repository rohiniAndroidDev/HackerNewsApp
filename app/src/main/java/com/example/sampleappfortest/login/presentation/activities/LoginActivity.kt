package  com.example.sampleappfortest.login.presentation.activities

import android.os.Bundle
import com.example.sampleappfortest.databinding.ActivityLoginBinding
import dagger.android.support.DaggerAppCompatActivity


class LoginActivity : DaggerAppCompatActivity() {


    lateinit var mBinding: com.example.sampleappfortest.databinding.ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = com.example.sampleappfortest.databinding.ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


    }
    /* override fun onBackPressed() {
         val fragment =
             this.supportFragmentManager.findFragmentById(R.id.fragment)
         (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
             super.onBackPressed()
         }
     }*/

}