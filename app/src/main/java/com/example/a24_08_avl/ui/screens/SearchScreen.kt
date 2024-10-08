package com.example.a24_08_avl.ui.screens

import android.content.pm.PackageManager
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction.Companion.Send
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.a24_08_avl.R
import com.example.a24_08_avl.ui.MyError
import com.example.a24_08_avl.ui.theme._24_08_avlTheme
import com.example.a24_08_avl.viewmodel.MainViewModel
import com.example.a24_08_avl.viewmodel.PictureBean
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Preview(showBackground = true, showSystemUi = true, device = Devices.NEXUS_10)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES, locale = "FR")
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _24_08_avlTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val viewModel = MainViewModel()
            viewModel.loadFakeData()
            viewModel.errorMessage = "Blabla"
            viewModel.runInProgress = true
            SearchScreen(viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    var searchText: MutableState<String> = remember {
        mutableStateOf("Nice")
    }

    val locationPermissionState = rememberPermissionState("android.car.permission.CAR_SPEED")


    Column(modifier= modifier.background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        SearchBar(searchText = searchText)

        AnimatedVisibility(visible = viewModel.runInProgress){
            CircularProgressIndicator()

        }

        MyError(errorMessage = viewModel.errorMessage)

        //Permet de remplacer très facilement le RecyclerView. LazyRow existe aussi
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)
,modifier = Modifier.weight(1f)
        ) {
            val filterList = viewModel.dataList //.filter { it.title.contains(searchText.value) }

            items(filterList.size) {
                PictureRowItem(data = filterList[it])
            }
        }

        Row {
            Button(
                onClick = { searchText.value = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear Filter")
            }

            Button(
                onClick = { viewModel.loadWeathers(searchText.value) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.Send,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(id = R.string.bt_load))
            }

            val pm : PackageManager = LocalContext.current.packageManager
            val isAutomotive = pm.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)
            if (isAutomotive) {
                Button(
                    onClick = {
                        //Si on a pas la permission on la demande
                        if (!locationPermissionState.status.isGranted) {
                            //Affiche la popup de demande de permission
                            locationPermissionState.launchPermissionRequest()
                        }
                    },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.Send,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Permission")
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureRowItem(data:PictureBean, modifier: Modifier = Modifier){

    var isExpanded  = remember {mutableStateOf(false) }

    Row (modifier = modifier
        .background(MaterialTheme.colorScheme.tertiary)
        .fillMaxWidth()) {

        //Permission Internet nécessaire
        GlideImage(
            model = data.url,
            //Pour aller le chercher dans string.xml
            //contentDescription = getString(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            // Image d'attente. Permet également de voir l'emplacement de l'image dans la Preview
            loading = placeholder(R.mipmap.ic_launcher_round),
            // Image d'échec de chargement
            failure = placeholder(R.mipmap.ic_launcher),
            contentScale = ContentScale.Fit,
            //même autres champs qu'une Image classique
            modifier = Modifier
                .heightIn(max = 200.dp) //Sans hauteur il prendra tous l'écran
                .widthIn(max = 200.dp)
        )


        Column(modifier = Modifier.clickable {
            isExpanded.value = !isExpanded.value
        }) {
            Text(
                text = data.title,
                fontSize = 40.sp,
            )

            Text(
                text = if(isExpanded.value) data.longText else  (data.longText.take(20) + "..."),
                fontSize = 28.sp,
                color = Color.Blue,
                modifier = Modifier.animateContentSize()
            )

        }
    }


}

@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText: MutableState<String>) {

    TextField(
        value = searchText.value, //Valeur affichée
        onValueChange = {newValue:String -> searchText.value = newValue}, //Nouveau texte entrée
        leadingIcon = { //Image d'icone
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { Text(stringResource(id = R.string.search_text), fontSize = 40.sp) }, //Texte d'aide qui se déplace
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum,
    )
}
