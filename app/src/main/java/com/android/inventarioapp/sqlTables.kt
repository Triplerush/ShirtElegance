package com.android.inventarioapp

const val sqlQueryMarcas = """
    CREATE TABLE IF NOT EXISTS "Marcas" (
        "MarCod"    INTEGER NOT NULL,
        "MarNom"    TEXT NOT NULL,
        "Estado"    INTEGER NOT NULL DEFAULT 1,  
        PRIMARY KEY("MarCod")
    );
"""

const val sqlQueryTallas = """
    CREATE TABLE IF NOT EXISTS "Tallas" (
        "TalCod"    TEXT NOT NULL,
        "TalDes"    TEXT NOT NULL,
        "Estado"    INTEGER NOT NULL DEFAULT 1,
        PRIMARY KEY("TalCod")
    );
"""

const val sqlQueryEquipos = """
    CREATE TABLE IF NOT EXISTS "Equipos" (
        "EquCod"    TEXT NOT NULL,
        "EquNom"    TEXT NOT NULL,
        "PaiCod"    INTEGER NOT NULL,
        "Estado"    INTEGER NOT NULL DEFAULT 1,
        PRIMARY KEY("EquCod")
    );
"""

const val sqlQueryPaises = """
    CREATE TABLE IF NOT EXISTS "Paises" (
        "PaiCod"    INTEGER NOT NULL,
        "PaiNom"    TEXT NOT NULL,
        "Estado"    INTEGER NOT NULL DEFAULT 1,
        PRIMARY KEY("PaiCod")
    );
"""

const val sqlQueryCamisetas = """
    CREATE TABLE IF NOT EXISTS "Camisetas" (
        "CamCod"    TEXT NOT NULL,
        "CamNom"    TEXT NOT NULL,
        "TalCod"    TEXT NOT NULL,
        "CamDor"    INTEGER NOT NULL,
        "CamNomJug" TEXT NOT NULL,
        "CamTem"    TEXT NOT NULL,
        "EquCod"    TEXT NOT NULL,
        "CamCan"    INTEGER NOT NULL,
        "MarCod"    INTEGER NOT NULL,
        "CamIma"    TEXT NOT NULL,
        "Estado"    INTEGER NOT NULL DEFAULT 1,
        FOREIGN KEY("TalCod") REFERENCES "Tallas"("TalCod"),
        FOREIGN KEY("EquCod") REFERENCES "Equipos"("EquCod"),
        FOREIGN KEY("MarCod") REFERENCES "Marcas"("MarCod"),
        PRIMARY KEY("CamCod")
    );
"""

const val sqlQuerySalidasDet = """
    CREATE TABLE IF NOT EXISTS "Salidas_det" (
        "SalDetCod" INTEGER NOT NULL,
        "SalCabNum" INTEGER NOT NULL,
        "CamCod"    TEXT NOT NULL,
        "CanCam"    INTEGER NOT NULL,
        "DetPre"    REAL NOT NULL,
        "Estado"    INTEGER NOT NULL DEFAULT 1,
        FOREIGN KEY("CanCam") REFERENCES "Camisetas"("CamCod"),
        PRIMARY KEY("SalDetCod")
    );
"""

const val sqlQueryCliente = """
    CREATE TABLE IF NOT EXISTS "Cliente" (
        "CliCod"    TEXT NOT NULL,
        "CliNom"    TEXT NOT NULL,
        "CliNumTel" TEXT NOT NULL,
        "Estado"    INTEGER NOT NULL DEFAULT 1,
        PRIMARY KEY("CliCod")
    );
"""

const val sqlQuerySalidasCab = """
    CREATE TABLE IF NOT EXISTS "Salidas_cab" (
        "SalCabNum"  INTEGER NOT NULL,
        "SalCabYear" INTEGER NOT NULL,
        "SalCabMon"  INTEGER NOT NULL,
        "SalCabDay"  INTEGER NOT NULL,
        "CabPre"     REAL NOT NULL,
        "SalCabCli"  TEXT NOT NULL,
        "Estado"     INTEGER NOT NULL DEFAULT 1,
        FOREIGN KEY("SalCabCli") REFERENCES "Cliente"("CliCod"),
        PRIMARY KEY("SalCabNum")
    );
"""

