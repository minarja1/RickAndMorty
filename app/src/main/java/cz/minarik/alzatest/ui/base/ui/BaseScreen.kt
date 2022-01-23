//package cz.minarik.alzatest.ui.base
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material.AlertDialog
//import androidx.compose.material.Button
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import cz.minarik.alzatest.ui.base.data.Status
//import cz.minarik.alzatest.ui.base.ui.BaseVM
//
//@Composable
//fun <T> BaseScreen(
//    status: Status,
//    vm: BaseVM<*, *, *>,
//    showFullscreenLoading: Boolean = false,
//    content: @Composable () -> Unit,
//) {
//
//    //TODO: vyřešit lépe
//    when {
//        status is Status.Loading -> {
//            if (showFullscreenLoading) {
//                LoadingIndicator()
//            }
//
//        }
//        status is Status.Failure -> {
//            ErrorDialog(
//                failure = status,
//                onDismiss = { vm.dismissErrorDialog() },
//            )
//        }
//        else -> { /* Ignored */
//        }
//    }
//
//    content()
//}
//
//@Composable
//fun LoadingIndicator() {
//    Box(
//        modifier = Modifier
//            .fillMaxHeight()
//            .fillMaxWidth()
//            .background(Color.Black)
//
//    )
//    {
//        CircularProgressIndicator(
//            modifier = Modifier
//                .align(Alignment.Center)
//        )
//    }
//}
//
//@Composable
//fun ErrorDialog(
//    failure: Status.Failure,
//    onDismiss: () -> Unit,
//) {
//    AlertDialog(
//        title = { Text(text = "Failure title") },
//        text = { Text(failure.error.toString()) },
//        confirmButton = {},
//        onDismissRequest = { },
//        dismissButton = {
//            Button(
//                onClick = { onDismiss.invoke() }) {
//                Text("Close")
//            }
//        }
//    )
//}