package com.example.t2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.example.t2.ui.theme.T2Theme
import kotlin.random.Random

var listUser:ArrayList<User> = arrayListOf()
var listJenisTabungan:ArrayList<Tabungan> = arrayListOf()
var listVA:ArrayList<VirtualAccount> = arrayListOf()

fun main(){
    listJenisTabungan.add(Tabungan("Silver", 10000, 0.01, 15000))
    listJenisTabungan.add(Tabungan("Gold", 15000, 0.025, 50000))

    do {
        print("""
            Bank MDP
            1. Login
            2. Register
            3. Exit
            >> 
        """.trimIndent())
        var input = readln()?.toIntOrNull()
        println()

        if (input == 1){
            login()
        }
        else if (input == 2){
            register()
        }
    }
    while(input != 3)
}

fun register(){
    var donepass = true
    do{
        println("REGISTER")
        print("Nama: ")
        var nama = readln()
        print("Username: ")
        var username = readln()
        print("Password: ")
        var password = readln()
        print("Confirm Password: ")
        var confpass = readln()

        val cekUser = listUser.find { it.username == username }
        if (cekUser != null){
            println("Username '$username' already exists.")
            println()
            donepass = true
        }
        else {
            if (password != confpass){
                println("Password tidak sama")
                println()
                donepass = true
            }
            else {
                print("""
                    Bank:
                    1. BCA
                    2. BNI
                    3. CIMB
                    >> 
                    """.trimIndent())
                donepass = false
                var bankName = readln().toInt()
                print("""
                    Jenis Tabungan: 
                    1. Tabungan Silver
                    2. Tabungan Gold
                    >> 
                """.trimIndent())
                var jenis = readln().toInt()

                var cekSetoran = true
                do {
                    print("Setoran Awal: ")
                    var setoran = readln().toInt()
                    var tabungan:Tabungan = listJenisTabungan[0]
                    when(jenis){
                        1 -> tabungan = listJenisTabungan[0]
                        2 -> tabungan = listJenisTabungan[1]
                    }

                    if(setoran < tabungan.saldoMinim){
                        println("Saldo kurang!")
                        cekSetoran = true
                    }
                    else {
                        var noRek = Random.nextInt(10000,99999)
                        var bank:Bank = Bank("abc",0,0)
                        when(bankName){
                            1 -> bank = BCA(noRek)
                            2 -> bank = BNI(noRek)
                            3 -> bank = CIMB(noRek)
                        }
                        listUser.add(User(nama, username, password, bank, tabungan, setoran))
                        println("Berhasil register nasabah baru!")
                        println("Nomor rekening: $noRek")
                        cekSetoran = false
                        println()
                    }
                } while(cekSetoran)
            }
        }
    }while(donepass)
}

fun login(){
    println("LOGIN")
    print("Username: ")
    var username = readln()
    print("Password: ")
    var password = readln()
//    println()

    if (username == "admin" && password == "admin"){
        menuAdmin()
    }
    else {
        val cekUser = listUser.firstOrNull { it.username == username }
        if (cekUser != null){
            if (cekUser.password == password){
                menuNasabah(cekUser)
            }
            else {
                println("Password salah!")
            }
        }
        else {
            println("User tidak ditemukan")
            println()
        }
    }
}

fun menuAdmin(){
    do {
        print("""
            Welcome, Admin
            1. Daftar Nasabah
            2. Daftar VA
            3. Ganti Bulan
            4. Logout
            >> 
        """.trimIndent())
        var menuAdmin = readln().toInt()
        println()

        if (menuAdmin == 1){
            var cek1 = true
            do {
                print("""
                    Daftar Nasabah
                    1. Bank BCA
                    2. Bank BNI
                    3. Bank CIMB
                    99. Back
                    >> 
                """.trimIndent())
                var pilihBank = readln().toInt()
                println()

                if (pilihBank == 1){
                    var bool = true
                    do {
                        println("Nasabah Bank BCA")
                        Bank.listNasabah(1)
                        println("99. Back")
                        print(">> ")
                        var back = readln().toInt()
                        println()
                        if (back == 99){
                            bool = false
                        }
                    } while (bool)
                }
                else if (pilihBank == 2){
                    var bool = true
                    do {
                        println("Nasabah Bank BNI")
                        Bank.listNasabah(2)
                        println("99. Back")
                        print(">> ")
                        var back = readln().toInt()
                        println()

                        if (back == 99){
                            bool = false
                        }
                    } while (bool)
                }
                else if (pilihBank == 3){
                    var bool = true
                    do {
                        println("Nasabah Bank CIMB")
                        Bank.listNasabah(3)
                        println("99. Back")
                        print(">> ")
                        var back = readln().toInt()
                        println()

                        if (back == 99){
                            bool = false
                        }
                    } while (bool)
                }
                else if (pilihBank == 99){
                    cek1 = false
                }

            } while(cek1)
        }
        else if (menuAdmin == 2){
            var cek2 = true
            do {
                println("Daftar Virtual Account")
                if (listVA.isEmpty()){
                    println("Virtual Account Belum Terdaftar")
                }
                else{
                    VirtualAccount.listVA(listVA)
                }
                print("""
                    98. Tambah VA
                    99. Back
                    >> 
                """.trimIndent())
                var pilihVA = readln().toInt()
                println()

                if (pilihVA == 98){
                    print("Nama VA: ")
                    var namaVA = readln()
                    print("Kode VA: ")
                    var kodeVA = readln().toIntOrNull() ?: 0

                    if (listVA.filter { it.nama == namaVA }.isEmpty()){
                        if (namaVA.length > 0 && kodeVA in 100..999){
                            listVA.add(VirtualAccount(namaVA.toUpperCase(), kodeVA))
                            println("Berhasil menambahkan VA $namaVA dengan kode $kodeVA")
                            println()
                        }
                        else{
                            println("Invalid Input! Nama VA tidak boleh kosong dan Kode VA harus terdiri dari 3 angka!")
                            println()
                        }
                    }
                    else {
                        println("Nama VA sudah terdaftar!")
                        println()
                    }
                }
                else if (pilihVA == 99){
                    cek2 = false
                }
//                else if (pilihVA in 1..listVA.size){
//                    // ============= GATAUUU MAU DIAPAIN =============
//                }
            }while(cek2)
        }
        else if (menuAdmin == 3){
            println("Berhasil ganti bulan!")
            for (i in listUser){
                val biayaAdmin = i.jenisTabungan.biayaAdmin
                val bunga = (i.jenisTabungan.bungaBulanan * i.saldo).toInt()
                i.saldo -= biayaAdmin + bunga
                i.listMutasi.add("(- $biayaAdmin) Biaya Admin Bulanan")
                i.listMutasi.add("(+ $bunga) Bunga Rekening Bulanan")
            }
        }
    } while(menuAdmin != 4)
}

fun menuNasabah(nasabah:User){
    do {
        println()
        print("""
            Welcome, ${nasabah.nama}
            Nomor Rekening: ${nasabah.bank.noRek}
            Tabungan: ${nasabah.bank.namaBank} ${nasabah.jenisTabungan.jenis}
            Saldo: Rp ${nasabah.saldo}
            1. Mutasi
            2. Daftar Transfer
            3. Setor
            4. Transfer Antar Rekening
            5. Transfer Antar Bank
            6. Transfer Virtual Account
            7. Logout
            >> 
        """.trimIndent())
        var menuNasabah = readln().toInt()
        println()
        if (menuNasabah == 1){
            //mutasi()
        }
        else if (menuNasabah == 2){
            daftarTransfer(nasabah)
        }
        else if (menuNasabah == 3){
            setor(nasabah)
        }
        else if (menuNasabah == 4){
            //tfAntarRekening()
        }
        else if (menuNasabah == 5){
            //tfAntarBank()
        }
        else if (menuNasabah == 6){
            //tfVA()
        }
        else { print("") }
    }while (menuNasabah != 7)
}

fun setor(u:User){
    println("Setor")
    print("Jumlah Setor: ")
    var setor = readln().toInt()
    if(setor > 5000){
        u.saldo += setor
        println("Berhasil Setor Rp $setor!")
    }
    else{
        println("Minimal Jumlah Setor Rp 5000")
    }
}

fun daftarTransfer(u:User){
    var nomor:Int = 0
    var daftarTF:List<User> = listOf()
    do {
        print("""
            Daftar Transfer
            1. Antar Rekening
            2. Antar Bank
            3. Virtual Account
            99. Back
            >> 
        """.trimIndent())
        var tf = readln()
        if (tf == "1"){
            print("Nomor Rekening: ")
            nomor = readln().toInt()
            var bank :Bank = u.bank
            var getUser:User? = listUser.filter { it.bank == bank }.firstOrNull { it.bank.noRek == nomor }
            if (getUser == null){
                println("Nomor Rekening Tidak Ditemukan!")
                println()
            }
            else {
                print("Tambahkan ${getUser.bank.noRek} - ${getUser.nama} ke Daftar Transfer?\n" +
                        "(Y/N): ")
                var confirm = readln()
                if (confirm == "Y"){
                    u.listRekening.add(getUser)
                    println("Berhasil menambahkan rekening")
                }
                else if (confirm == "N"){
                    println("Gagal menambahkan rekening")
                }
            }
        }
        else if(tf == "2"){
            print("Bank: ")
            var namaBank = readln()
            if (namaBank == "BCA" || namaBank == "BNI" || namaBank == "CIMB"){
                print("Nomor Rekening: ")
                nomor = readln().toInt()
                daftarTF = Bank.getNasabah(listUser.sortedBy { it.nama }, namaBank)

                var getUser:User? = User("-", "-", "-", Bank("-", 0), Tabungan("-", 0, 0.4, 0))
                if (namaBank == "BCA"){
                    getUser = daftarTF.filter { it.bank is BCA }.firstOrNull { it.bank.noRek == nomor }
                }
                else if (namaBank == "BNI"){
                    getUser = daftarTF.filter { it.bank is BNI }.firstOrNull { it.bank.noRek == nomor }
                }
                else if (namaBank == "CIMB"){
                    getUser = daftarTF.filter { it.bank is CIMB }.firstOrNull { it.bank.noRek == nomor }
                }

                if (getUser == null){
                    println("Nomor Rekening Tidak Ditemukan!")
                }
                else {
                    print("Tambahkan ${getUser.bank.noRek} - ${getUser.nama} ke Daftar Transfer?\n" +
                            "(Y/N): ")
                    var confirm = readln()
                    if (confirm == "Y"){
                        u.listBank.add(getUser)
                        println("Berhasil menambahkan rekening")
                    }
                    else if (confirm == "N"){
                        println("Gagal menambahkan rekening")
                    }
                }
            }
            else{
                println("Nama Bank Tidak Valid!")
            }
        }
        else if(tf == "3"){

        }
    }while(tf != "99")
}



