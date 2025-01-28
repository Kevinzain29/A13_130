package com.example.soala13pam.ui.view.MainView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.soala13pam.R
import com.example.soala13pam.ui.navigation.DestinasiNavigasi

object DestinasiMainHome : DestinasiNavigasi {
    override val route = "Main Home"
    override val titleRes = "Peserta"
}

@Composable
fun HomeMainView(
    onPESERTAButton: ()->Unit,
    onEVENTButton: ()->Unit,
    onTIKETButton: ()->Unit,
    onTRANSAKSIButton: ()->Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(
                    id = R.color.blue
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(
                id = R.drawable.event
            ),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {onPESERTAButton()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Text(text = "PESERTA")
        }
        Button(
            onClick = {onEVENTButton()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Text(text = "EVENT")
        }
        Button(
            onClick = {onTIKETButton()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Text(text = "TIKET")
        }
        Button(
            onClick = {onTRANSAKSIButton()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Text(text = "TRANSAKSI")
        }
    }
}