package net.telepathix.petbase

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class PetBaseTestRunner: AndroidJUnitRunner() {
    @Throws(Exception::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, PetBaseTestApp::class.java.name, context)
    }
}
