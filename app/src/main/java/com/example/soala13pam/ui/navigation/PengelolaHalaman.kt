package com.example.soala13pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.soala13pam.ui.view.MainView.DestinasiMainHome
import com.example.soala13pam.ui.view.MainView.HomeMainView
import com.example.soala13pam.ui.view.event.DestinasiEntryEvent
import com.example.soala13pam.ui.view.event.DestinasiEventDetail
import com.example.soala13pam.ui.view.event.DestinasiEventHome
import com.example.soala13pam.ui.view.event.DestinasiUpdateEvent
import com.example.soala13pam.ui.view.event.EntryEventScreen
import com.example.soala13pam.ui.view.event.EventDetailView
import com.example.soala13pam.ui.view.event.EventHomeScreen
import com.example.soala13pam.ui.view.event.EventUpdateView
import com.example.soala13pam.ui.view.peserta.DestinasiEntry
import com.example.soala13pam.ui.view.peserta.DestinasiPesertaDetail
import com.example.soala13pam.ui.view.peserta.DestinasiPesertaHome
import com.example.soala13pam.ui.view.peserta.DestinasiUpdate
import com.example.soala13pam.ui.view.peserta.EntryPesertaScreen
import com.example.soala13pam.ui.view.peserta.PesertaDetailView
import com.example.soala13pam.ui.view.peserta.PesertaHomeScreen
import com.example.soala13pam.ui.view.peserta.PesertaUpdateView
import com.example.soala13pam.ui.view.tiket.DestinasiEntryTiket
import com.example.soala13pam.ui.view.tiket.DestinasiTiketDetail
import com.example.soala13pam.ui.view.tiket.DestinasiTiketHome
import com.example.soala13pam.ui.view.tiket.DestinasiUpdateTiket
import com.example.soala13pam.ui.view.tiket.EntryTiketScreen
import com.example.soala13pam.ui.view.tiket.TiketDetailView
import com.example.soala13pam.ui.view.tiket.TiketHomeScreen
import com.example.soala13pam.ui.view.tiket.TiketUpdateView
import com.example.soala13pam.ui.view.transaksi.DestinasiEntryTransaksi
import com.example.soala13pam.ui.view.transaksi.DestinasiTransaksiDetail
import com.example.soala13pam.ui.view.transaksi.DestinasiTransaksiHome
import com.example.soala13pam.ui.view.transaksi.EntryTransaksiScreen
import com.example.soala13pam.ui.view.transaksi.TransaksiDetailView
import com.example.soala13pam.ui.view.transaksi.TransaksiHomeScreen

//a
@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiMainHome.route// halaman yg akan ditampilkan pertama
    ) {
        composable(
            route = DestinasiMainHome.route
        ) {
            HomeMainView(
                onPESERTAButton = {
                    navController.navigate(DestinasiPesertaHome.route)
                },
                onEVENTButton = {
                    navController.navigate(DestinasiEventHome.route)
                },
                onTIKETButton = {
                    navController.navigate(DestinasiTiketHome.route)
                },
                onTRANSAKSIButton = {
                    navController.navigate(DestinasiTransaksiHome.route)
                }
            )
        }
        composable(DestinasiPesertaHome.route) {
            PesertaHomeScreen(
                navigateToPesertaEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { idPeserta ->
                    navController.navigate("${DestinasiPesertaDetail.route}/$idPeserta")
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(DestinasiEventHome.route) {
            EventHomeScreen(
                navigateToEventEntry = { navController.navigate(DestinasiEntryEvent.route) },
                onDetailClick = { idEvent ->
                    navController.navigate("${DestinasiEventDetail.route}/$idEvent")
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(DestinasiTiketHome.route) {
            TiketHomeScreen(
                navigateToTiketEntry = { navController.navigate(DestinasiEntryTiket.route) },
                onDetailClick = { idTiket ->
                    navController.navigate("${DestinasiTiketDetail.route}/$idTiket")
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(DestinasiTransaksiHome.route) {
            TransaksiHomeScreen(
                navigateToTransaksiEntry = { navController.navigate(DestinasiEntryTransaksi.route) },
                onDetailClick = { idTransaksi ->
                    navController.navigate("${DestinasiTransaksiDetail.route}/$idTransaksi")
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntryPesertaScreen(navigateBack = {
                navController.navigate(DestinasiPesertaHome.route) {
                    popUpTo(DestinasiPesertaHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiEntryEvent.route) {
            EntryEventScreen(navigateBack = {
                navController.navigate(DestinasiEventHome.route) {
                    popUpTo(DestinasiEventHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiEntryTiket.route) {
            EntryTiketScreen(navigateBack = {
                navController.navigate(DestinasiTiketHome.route) {
                    popUpTo(DestinasiTiketHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiEntryTransaksi.route) {
            EntryTransaksiScreen(navigateBack = {
                navController.navigate(DestinasiTransaksiHome.route) {
                    popUpTo(DestinasiTransaksiHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = "${DestinasiPesertaDetail.route}/{idPeserta}",
            arguments = listOf(navArgument("idPeserta") { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString("idPeserta") ?: ""
            PesertaDetailView(
                idPeserta = idPeserta,
                navigateBack = {
                    navController.navigate(DestinasiPesertaHome.route) {
                        popUpTo(DestinasiPesertaHome.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdate.route}/$idPeserta")
                }
            )
        }
        composable(
            route = "${DestinasiEventDetail.route}/{idEvent}",
            arguments = listOf(navArgument("idEvent") { type = NavType.StringType })
        ) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getString("idEvent") ?: ""
            EventDetailView(
                idEvent = idEvent,
                navigateBack = {
                    navController.navigate(DestinasiEventHome.route) {
                        popUpTo(DestinasiEventHome.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateEvent.route}/$idEvent")
                }
            )
        }
        composable(
            route = "${DestinasiTiketDetail.route}/{idTiket}",
            arguments = listOf(navArgument("idTiket") { type = NavType.StringType })
        ) { backStackEntry ->
            val idTiket = backStackEntry.arguments?.getString("idTiket") ?: ""
            TiketDetailView(
                idTiket = idTiket,
                navigateBack = {
                    navController.navigate(DestinasiTiketHome.route) {
                        popUpTo(DestinasiTiketHome.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateTiket.route}/$idTiket")
                }
            )
        }
        composable(
            route = "${DestinasiTransaksiDetail.route}/{idTransaksi}",
            arguments = listOf(navArgument("idTransaksi") { type = NavType.StringType })
        ) { backStackEntry ->
            val idTransaksi = backStackEntry.arguments?.getString("idTransaksi") ?: ""
            TransaksiDetailView(
                idTransaksi = idTransaksi,
                navigateBack = {
                    navController.navigate(DestinasiTransaksiHome.route) {
                        popUpTo(DestinasiTransaksiHome.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {}
            )
        }
        composable(
            route = "${DestinasiUpdate.route}/{idPeserta}",
            arguments = listOf(navArgument("idPeserta") { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString("idPeserta") ?: ""
            PesertaUpdateView(
                idPeserta = idPeserta,
                navigateBack = {
                    navController.navigate(DestinasiPesertaHome.route) {
                        popUpTo(DestinasiPesertaHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "${DestinasiUpdateEvent.route}/{idEvent}",
            arguments = listOf(navArgument("idEvent") { type = NavType.StringType })
        ) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getString("idEvent") ?: ""
            EventUpdateView(
                idEvent = idEvent,
                navigateBack = {
                    navController.navigate(DestinasiEventHome.route) {
                        popUpTo(DestinasiEventHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "${DestinasiUpdateTiket.route}/{idTiket}",
            arguments = listOf(navArgument("idTiket") { type = NavType.StringType })
        ) { backStackEntry ->
            val idTiket = backStackEntry.arguments?.getString("idTiket") ?: ""
            TiketUpdateView(
                idTiket = idTiket,
                navigateBack = {
                    navController.navigate(DestinasiTiketHome.route) {
                        popUpTo(DestinasiTiketHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

