package com.android.inventarioapp.class_tables

data class Shirt (
    val CamCod: String,
    val CamNom: String,
    val TalCod: String,
    val CamDor: Int,
    val CamNomJug: String,
    val CamTem: String,
    val EquCod: String,
    var CamCan: Int,
    val MarCod: Int,
    val CamIma: String,
    val EstCam: Int = 1,
)
