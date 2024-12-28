package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressIndicator(
    progress: Float, // 0.0f to 1.0f
    question: String,
    largeNumber: Int,
    circleColor: Color = Color.Blue,
    trackColor: Color = Color.LightGray,
    circleSize: Float = 150f, // in dp
    strokeWidth: Float = 10f // in dp
) {
    Box(
        modifier = Modifier
            .size(circleSize.dp),
        contentAlignment = Alignment.Center
    ) {
        // Draw track
        Canvas(modifier = Modifier.size(circleSize.dp)) {
            drawCircle(
                color = trackColor,
                radius = size.minDimension / 2,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }

        // Draw progress
        Canvas(modifier = Modifier.size(circleSize.dp)) {
            drawArc(
                color = circleColor,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }

        // Overlay with text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = question,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = largeNumber.toString(),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }
}
