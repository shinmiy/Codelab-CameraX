package com.shinmiy.cameraxdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.TextureView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// This is an arbitrary number we are using to keep tab of the permission
// request. Where an app has multiple context for requesting permission,
// this can help differentiate the different contexts
private const val REQUEST_CODE_PERMISSIONS = 10

// This is an array of all the permission specified in the manifest
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class MainActivity : AppCompatActivity() {

    private lateinit var viewFinder: TextureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewFinder = findViewById(R.id.view_finder)

        // Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    private fun startCamera() {
        // TODO: Implement CameraX operations
    }

    private fun updateTransform() {
        // TODO: Implement camera viewfinder transformations
    }

    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != REQUEST_CODE_PERMISSIONS) return

        if (allPermissionsGranted().not()) {
            Toast.makeText(this, R.string.toast_permission_failed, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewFinder.post { startCamera() }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.none {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    }
}
