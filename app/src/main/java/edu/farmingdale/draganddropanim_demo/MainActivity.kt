package edu.farmingdale.draganddropanim_demo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.farmingdale.draganddropanim_demo.ui.theme.DragAndDropAnim_DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        enableEdgeToEdge()
        setContent {
            DragAndDropAnim_DemoTheme {

                    DragAndDropBoxes()


            }
        }
    }
}



// This should be completed in a group setting
// ToDo 1: Analyze the requirements for Individual Project 3
// ToDo 2: Show the DragAndDropBoxes composable
// ToDo 3: Change the circle to a rect
// ToDo 4: Replace the command right with a image or icon
// ToDo 5: Make this works in landscape mode only
// ToDo 6: Rotate the rect around itself
// ToDo 7: Move - translate the rect horizontally and vertically
// ToDo 8: Add a button to reset the rect to the center of the screen
// ToDo 9: Enable certain animation based on the drop event (like up or down)
// ToDo 10: Make sure to commit for each one of the above and submit this individually


