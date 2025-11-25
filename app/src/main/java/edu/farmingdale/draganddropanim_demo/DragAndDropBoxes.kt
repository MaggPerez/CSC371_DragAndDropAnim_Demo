@file:OptIn(ExperimentalFoundationApi::class)

package edu.farmingdale.draganddropanim_demo

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.ui.res.painterResource

//private val rotation = FloatPropKey()


@Composable
fun DragAndDropBoxes(modifier: Modifier = Modifier) {
    var isPlaying by remember { mutableStateOf(true) }
    var resetToCenter by remember { mutableStateOf(false) }

    //will track which box was last dropped on
    var dragBoxIndex by remember {
        mutableIntStateOf(0)
    }
    var lastDroppedIndex by remember {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.2f)
        ) {
            val boxCount = 4

            repeat(boxCount) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(10.dp)
                        .border(1.dp, Color.Black)
                        .dragAndDropTarget(
                            shouldStartDragAndDrop = { event ->
                                event
                                    .mimeTypes()
                                    .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                            },
                            target = remember {
                                object : DragAndDropTarget {
                                    override fun onDrop(event: DragAndDropEvent): Boolean {
                                        isPlaying = !isPlaying
                                        dragBoxIndex = index
                                        lastDroppedIndex = index
                                        return true
                                    }
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    this@Row.AnimatedVisibility(
                        visible = index == dragBoxIndex,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.right_arrow),
                            contentDescription = "Right Arrow",
                            tint = Color.Red,
                            modifier = Modifier
                                .fillMaxSize()
                                .dragAndDropSource {
                                    detectTapGestures(
                                        onLongPress = { offset ->
                                            startTransfer(
                                                transferData = DragAndDropTransferData(
                                                    clipData = ClipData.newPlainText(
                                                        "text",
                                                        ""
                                                    )
                                                )
                                            )
                                        }
                                    )
                                }
                        )
                    }
                }
            }
        }


        val pOffset by animateIntOffsetAsState(

            //"to do" 7 completed here <--- accidentally removed this comment before
            targetValue = when {
                //resets rect to center
                resetToCenter -> IntOffset(400, 55)

                /**
                 * each box triggers a different pattern animation
                 */

                //box 1: Up/Down
                lastDroppedIndex == 0 -> if (isPlaying) IntOffset(100, 50) else IntOffset(100, 300)

                //box 2: Left/Right
                lastDroppedIndex == 1 -> if (isPlaying) IntOffset(50, 150) else IntOffset(500, 150)

                //box 3: Diagonal
                lastDroppedIndex == 2 -> if (isPlaying) IntOffset(200, 100) else IntOffset(400, 250)

                //box 4: Static (no movement)
                else -> if (isPlaying) IntOffset(300, 200) else IntOffset(300, 200)
            },
            animationSpec = tween(3000, easing = LinearEasing)
        )

        val rtatView by animateFloatAsState(
            targetValue = if (isPlaying) 360f else 0.0f,
            // Configure the animation duration and easing.
            animationSpec = repeatable(
                iterations = if (isPlaying) 10 else 1,
                tween(durationMillis = 3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(Color.Red)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .padding(10.dp)
                    //"to do " 7 completed here
                    .offset(pOffset.x.dp, pOffset.y.dp)
                    //this "to do" was completed at number 3
                    .rotate(rtatView)
                    .background(Color.Blue)
            )

            Button(
                onClick = {
                    resetToCenter = !resetToCenter
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text("Reset to Center")
            }
        }
    }
}

