//package cz.minarik.alzatest.ui.login
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.layoutId
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import cz.minarik.alzatest.ui.base.ui.Location
//import cz.minarik.alzatest.R
//import cz.minarik.alzatest.ui.base.BaseScreen
//import cz.minarik.alzatest.ui.base.ui.LaunchListenForEffects
//import kotlinx.coroutines.flow.onEach
//import org.koin.androidx.compose.get
//
//
//private const val UsernameId = "UsernameId"
//private const val PasswordId = "PasswordId"
//private const val ButtonId = "ButtonId"
//
///*
//@Preview
//@Composable
//fun PreviewLoginScreen() {
//    ExampleTheme(darkTheme = true) {
//        LoginScreen()
//    }
//}
//*/
//
//@Composable
//fun LoginScreen(
//    viewModel: LoginVM = get(),
//    navController: NavHostController,
//) {
//
//    val viewState = viewModel.viewState.collectAsState()
//
//    LaunchedEffect(LaunchListenForEffects) {
//        viewModel.effect.onEach { effect ->
//            when (effect) {
//                is Effect.NavigateToDashboard -> Location.Dashboard.navigate(
//                    navController
//                )
//            }
//        }.collect()
//    }
//
//
//    BaseScreen(status = viewState.value.status, vm = viewModel) {
//        Column(
//            modifier = Modifier
//                .fillMaxHeight(1F)
//                .fillMaxWidth(1F),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//
//            EmailField(
//                value = viewState.value.email,
//                onChanged = { viewModel.setEvent(Event.EmailChanged(it)) },
//            )
//
//            PasswordField(
//                value = viewState.value.password,
//                isPasswordVisible = viewState.value.isPasswordShown,
//                onChanged = { viewModel.setEvent(Event.PasswordChanged(it)) },
//                onToggleVisibility = { viewModel.setEvent(Event.TogglePasswordVisibility) },
//                onDone = { viewModel.setEvent(Event.Login) }
//            )
//
//            Button(
//                onClick = { viewModel.setEvent(Event.Login) },
//                modifier = Modifier
//                    .layoutId(ButtonId) // TODO - kdy je nutno layoutId ?
//                    .padding(vertical = 8.dp), // todo remove workaround after beta09
//            ) {
//                Text(stringResource(R.string.login))
//            }
//        }
//    }
//}
//
//@Composable
//fun EmailField(value: String, onChanged: (String) -> Unit) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onChanged,
//        label = { Text(stringResource(R.string.username)) },
//        modifier = Modifier.layoutId(UsernameId),
//        singleLine = true,
//    )
//}
//
//@Composable
//fun PasswordField(
//    value: String,
//    isPasswordVisible: Boolean,
//    onChanged: (String) -> Unit,
//    onToggleVisibility: () -> Unit,
//    onDone: () -> Unit,
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onChanged,
//        label = { Text(stringResource(R.string.password)) },
//        modifier = Modifier.layoutId(PasswordId),
//        trailingIcon = {
//            IconButton(
//                onClick = onToggleVisibility,
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Lock,
//                    contentDescription = null,
//                )
//            }
//        },
//        singleLine = true,
//        visualTransformation =
//        if (isPasswordVisible) PasswordVisualTransformation()
//        else VisualTransformation.None,
//        keyboardActions = KeyboardActions(
//            onDone = { onDone.invoke() },
//        )
//    )
//}