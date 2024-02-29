package com.example.t2

class CIMB(namaBank:String = "CIMB", biayaTF:Int = 1000):Bank(namaBank = namaBank, biayaTF = biayaTF) {
    constructor(nomor:Int):this(){
        noRek = nomor
        this.namaBank = namaBank
        this.biayaTF = biayaTF
        this.biayaVA = biayaVA
    }
}