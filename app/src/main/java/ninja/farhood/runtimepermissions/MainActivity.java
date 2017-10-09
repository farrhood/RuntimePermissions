package ninja.farhood.runtimepermissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Permission code that will be checked in the method:


    private int RUNTIME_PERMISSION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing button buttonRequestPermission
        Button buttonRequestPermission = (Button) findViewById(R.id.buttonRequestPermission);

        // Adding a click listener
        buttonRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First checking if the app already has the permission
                if (isCameraAccessAllowed()) {
                    // If permission is already granted then show the following toast
                    Toast.makeText(v.getContext(), "You already have permission to acess Camera", Toast.LENGTH_LONG).show();
                    // Exiting the method with return
                    return;
                }

                // If the app does not have permission then ask for it
                requestCameraPermission();
            }
        });

    }

    // We are calling this method to check the permission status

    private boolean isCameraAccessAllowed() {
        // Getting the permissions status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        // If permission is granted return true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        // If permission is not granted return false
        return false;
    }

    // Requesting permission
    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // If the user has denied the permission previously your code will come to this block
            // Here you can explain why you need this permission..
            showAlertDialog();
        }

        // And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RUNTIME_PERMISSION_CODE);
    }

    // This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // Checking the request code of our request
        if (requestCode == RUNTIME_PERMISSION_CODE) {

            // If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Displaying a toast
                Toast.makeText(this, "Permission granted you may now use the camera", Toast.LENGTH_LONG).show();
            } else {
                // Displaying another toast if permission is not granted
                Toast.makeText(this, "Oop you just denied the permission", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set title
        alertDialogBuilder.setTitle("Runtime Permissions");

        // Set dialog message
        alertDialogBuilder.setMessage("This is the tutorial for Runtime Permission App, which needs permission for accessing your device Camera."
                + "Please grant the permission.").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        // Create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Show it
        alertDialog.show();
    }

}
