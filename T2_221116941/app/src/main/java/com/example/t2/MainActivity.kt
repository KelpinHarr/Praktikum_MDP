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

    listUser.add(User("Calvin", "calvin", "calvin", BCA(12345), listJenisTabungan[0], 100000))
    listUser.add(User("Kelpin", "kelpin", "kelpin", BNI(67890), listJenisTabungan[1], 200000))
    listUser.add(User("Calpin", "calpin", "calpin", CIMB(54321), listJenisTabungan[0], 120000))

    listVA.add(VirtualAccount("GOPAY", 123))
    listVA.add(VirtualAccount("OVO", 456))
    listVA.add(VirtualAccount("DANA", 789))

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

                donepass = false
                var bankName = 0
                do {
                    print("""
                    Bank:
                    1. BCA
                    2. BNI
                    3. CIMB
                    >> 
                    """.trimIndent())
                    bankName = readln().toInt()
                }while (!(bankName in 1..3))
                println()
                var jenis = 0
                do {
                    print("""
                        Jenis Tabungan: 
                        1. Tabungan Silver
                        2. Tabungan Gold
                        >> 
                    """.trimIndent())
                    jenis = readln().toInt()
                }while (!(jenis in 1..2))
                println()

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
    var username = readln()!!
    print("Password: ")
    var password = readln()!!

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

                    if (listVA.filter { it.kode == kodeVA }.isEmpty()){
                        if (namaVA.length > 0 && kodeVA in 100..999){
                            listVA.add(VirtualAccount(namaVA.toUpperCase(), kodeVA))
                            println("Berhasil menambahkan VA ${namaVA.toUpperCase()} dengan kode $kodeVA")
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
        var menuNasabah = readln()
        println()
        if (menuNasabah == "1"){
            mutasi(nasabah)
        }
        else if (menuNasabah == "2"){
            daftarTransfer(nasabah)
        }
        else if (menuNasabah == "3"){
            setor(nasabah)
        }
        else if (menuNasabah == "4"){
            tfAntarRekening(nasabah)
        }
        else if (menuNasabah == "5"){
            tfAntarBank(nasabah)
        }
        else if (menuNasabah == "6"){
            tfVA(nasabah)
        }
        else { print("") }
    }while (menuNasabah != "7")
}

fun mutasi(u:User){
    do {
        println("Mutasi Rekening")
        if (u.listMutasi.isEmpty()){
            println("Tidak ada daftar mutasi")
            println()
        }
        else {
            for((idx, i) in u.listMutasi.withIndex()) {
                println("${idx + 1}. ${i}")
            }
        }
        println("99. Back")
        print(">> ")
        var menuMutasi = readln().toInt()
    } while(menuMutasi != 99)
}

fun setor(u:User){
    println("Setor")
    print("Jumlah Setor: ")
    var setor = readln().toInt()
    if(setor > 5000){
        u.saldo += setor
        println("Berhasil Setor Rp $setor!")
        u.listMutasi.add("(+ $setor) Setor")
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
         println()

        if (tf == "1"){
            do {
                println("Daftar Transfer ${u.bank.namaBank}")
                if (u.listRekening.isEmpty()){
                    println("Tidak ada daftar transfer")
                }
                else {
                    for((idx, i) in u.listRekening.withIndex()){
                        println("${idx + 1}. ${i.daftarTransfer()}")
                    }
                }
                print("98. Tambah Rekening\n" +
                        "99. Back\n>> ")
                var menuDaftarTF = readln().toInt()
                println()
                if (menuDaftarTF == 98){
                    print("Nomor Rekening: ")
                    nomor = readln().toInt()

                    if(User.cekKembar(u.listRekening, nomor)) {
                        menuDaftarTF = 99
                        println("Nomor Rekening Sudah Terdaftar")
                        println()
                    }
                    if (nomor == u.bank.noRek){
                        menuDaftarTF = 99
                        println("Ini Nomor Rekening Anda :)")
                        println()
                    }

                    var getUser:User? = Bank.getNasabah(listUser, u.bank.namaBank).firstOrNull { it.bank.noRek == nomor }

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
                            println()
                        }
                        else if (confirm == "N"){
                            println("Gagal menambahkan rekening")
                            println()
                        }
                    }
                }
            }while(menuDaftarTF != 99)
        }
        else if(tf == "2"){
            do {
                println("Daftar Transfer Antar Bank")
                if (u.listBank.isEmpty()){
                    println("Tidak ada daftar transfer")
                    println()
                }
                else {
                    for((idx, i) in u.listBank.withIndex()){
                        println("${idx + 1}. ${i.daftarBank()}")
                    }
                }
                print("98. Tambah Rekening\n" +
                        "99. Back\n>> ")
                var menuTF = readln().toInt()
                println()
                if (menuTF == 98){
                    print("Bank: ")
                    var namaBank = readln()
                    if (namaBank == u.bank.namaBank){
                        println("Tambahkan Rekening Bank Selain ${u.bank.namaBank}")
                    }
                    else if (namaBank == "BCA" || namaBank == "BNI" || namaBank == "CIMB"){
                        print("Nomor Rekening: ")
                        nomor = readln().toInt()

                        if(User.cekKembar(u.listBank, nomor)) {
                            menuTF = 99
                            println("Nomor Rekening Sudah Terdaftar")
                            println()
                        }
                        if (nomor == u.bank.noRek){
                            menuTF = 99
                            println("Ini Nomor Rekening Anda :)")
                            println()
                        }

                        var getUser:User? = Bank.getNasabah(listUser.sortedBy { it.nama }.toMutableList(), namaBank).firstOrNull { it.bank.noRek == nomor }

                        if (getUser == null){
                            println("Nomor Rekening Tidak Ditemukan!")
                            println()
                        }
                        else {
                            print("Tambahkan ${getUser.bank.noRek} - ${getUser.nama} ke Daftar Transfer?\n" +
                                    "(Y/N): ")
                            var confirm = readln()
                            if (confirm == "Y"){
                                u.listBank.add(getUser)
                                println("Berhasil menambahkan rekening")
                                println()
                            }
                            else if (confirm == "N"){
                                println("Gagal menambahkan rekening")
                                println()
                            }
                        }
                    }
                    else{
                        println("Nama Bank Tidak Valid!")
                        println()
                    }
                }
            } while(menuTF != 99)
        }
        else if(tf == "3"){
            do {
                println("Daftar Transfer VA")
                if (u.listVA.isEmpty()){
                    println("Tidak ada daftar VA")
                    println()
                }
                else {
                    for((idx, i) in u.listVA.withIndex()){
                        println("${idx + 1}. ${i.daftarVA()}")
                    }
                }
                print("98. Tambah VA\n" +
                        "99. Back\n>> ")
                var menuVA = readln().toInt()
                println()

                if (menuVA == 98){
                    do {
                        print("Nomor VA: ")
                        var inputVA = readln()
                        if (inputVA.length in 6..9){
                            var VA = VirtualAccount.getVA(listVA, inputVA)
                            if (VA == null){
                                println("Nomor VA tidak terdaftar")
                                println()
                            }
                            else {
                                print("Tambahkan ${VA.nama} - $inputVA ke Daftar Transfer?\n" +
                                        "(Y/N): ")
                                var confirm = readln()
                                if (confirm == "Y"){
                                    u.listVA.add(usersVA(VA.nama, inputVA.toInt(), u))
                                    println("Berhasil menambahkan VA")
                                    println()
                                }
                                else if (confirm == "N"){
                                    println("Gagal menambahkan VA")
                                    println()
                                }
                            }
                        }
                        else {
                            println("VA maksimal 6 digit")
                            println()
                        }
                    } while(!(inputVA.length in 6..9))
                }

            } while(menuVA != 99)
        }
    }while(tf != "99")
}

fun tfAntarRekening(u:User){
    do {
        println("Transfer Antar Rekening: ")

        if (u.listRekening.isEmpty()){
            println("Tidak ada daftar transfer")
            println()
        }
        else {
            for((idx, i) in u.listRekening.withIndex()){
                println("${idx + 1}. ${i.daftarTransfer()}")
            }
        }
        println("99. Back")
        print(">> ")
        var menuTF = readln().toInt()
        println()

        if (menuTF in 1..u.listRekening.size){
            var data_nasabah = u.listRekening[menuTF - 1]
            print("Jumlah Transfer: ")
            var jumlahTF = readln().toInt()

            if (jumlahTF > u.saldo){
                println("Saldo Anda tidak mencukupi")
                println()
            }
            else {
                if (jumlahTF < 5000){
                    println("Minimal Jumlah Transfer Rp 5000")
                    println()
                }
                else {
                    var sisa_saldo = u.saldo - jumlahTF
                    println("Berhasil transfer Rp $jumlahTF ke ${data_nasabah.nama}")
                    println("Sisa saldo Anda: Rp $sisa_saldo")
                    u.saldo = sisa_saldo
                    data_nasabah.saldo += jumlahTF

                    u.listMutasi.add("(- $jumlahTF) Transfer ke ${data_nasabah.nama}")
                    data_nasabah.listMutasi.add("(+ $jumlahTF) Transfer dari ${u.nama}")
                    println()
                }
            }
        }
    }while(menuTF != 99)
}

fun tfAntarBank(u:User){
    do {
        println("Transfer Antar Bank: ")
        if (u.listBank.isEmpty()){
            println("Tidak ada daftar rekening")
        }
        else {
            for((idx, i) in u.listBank.withIndex()){
                println("${idx + 1}. ${i.daftarBank()}")
            }
        }
        println("99. Back")
        println("** Anda akan dikenakan biaya admin ${u.bank.biayaTF}")
        print(">> ")
        var menuTF = readln().toInt()

        if (menuTF in 1..u.listBank.size){
            var data_nasabah = u.listBank[menuTF - 1]
            print("Jumlah Transfer: ")
            var jumlahTF = readln().toInt()
            if (jumlahTF + u.bank.biayaTF > u.saldo){
                println("Saldo Anda tidak mencukupi")
                println()
            }
            else {
                if (jumlahTF < 5000){
                    println("Minimal Jumlah Transfer Rp 5000")
                    println()
                }
                else {
                    var sisa_saldo = u.saldo - (jumlahTF + u.bank.biayaTF)
                    println("Berhasil transfer Rp $jumlahTF ke ${data_nasabah.nama}")
                    println("Sisa saldo Anda: $sisa_saldo")
                    u.saldo = sisa_saldo
                    data_nasabah.saldo += jumlahTF

                    u.listMutasi.add("(- $jumlahTF) Transfer ke ${data_nasabah.nama}")
                    u.listMutasi.add("(- ${u.bank.biayaTF}) Biaya transfer antar bank")
                    data_nasabah.listMutasi.add("(+ $jumlahTF) Transfer dari ${u.nama}")
                    println()
                }
            }
        }
    } while(menuTF != 99)
}

fun tfVA(u:User){
    do {
        println("Transfer Virtual Account")

        if (u.listVA.isEmpty()){
            println("Tidak ada daftar VA")
        }
        else {
            for((idx, i) in u.listVA.withIndex()){
                println("${idx + 1}. ${i.daftarVA()}")
            }
        }
        println("98. Input Nomor VA")
        println("99. Back")
        println("** Anda akan dikenakan biaya admin ${u.bank.biayaVA}")
        print(">> ")
        var menuTF = readln().toInt()

        if (menuTF in 1..listVA.size){
            var dataVA = u.listVA[menuTF - 1]
            print("Jumlah Transfer: ")
            var jumlahTF = readln().toInt()
            if (jumlahTF + u.bank.biayaVA!! > u.saldo){
                println("Saldo Anda tidak mencukupi")
            }
            else {
                var sisa_saldo = u.saldo - (jumlahTF + u.bank.biayaVA!!)
                println("Berhasil transfer Rp $jumlahTF ke ${dataVA.nama} ${dataVA.kode}")
                println("Sisa saldo Anda: Rp $sisa_saldo")
                u.saldo = sisa_saldo
                u.listMutasi.add("(- $jumlahTF) Transfer ke VA ${dataVA.nama} ${dataVA.kode}")
                u.listMutasi.add("(- ${u.bank.biayaVA}) Biaya admin VA")
            }
        }
        else if (menuTF == 98){
            do {
                print("Input Nomor VA: ")
                var inputVA = readln()
                if (inputVA.length in 6..9){
                    var VA = VirtualAccount.getVA(listVA, inputVA)
                    if (VA == null){
                        println("Nomor VA tidak terdaftar")
                        println()
                    }
                    else {
                        print("Jumlah Transfer: ")
                        var jumlahTF = readln().toInt()
                        if (jumlahTF + u.bank.biayaVA!! > u.saldo){
                            println("Saldo Anda tidak mencukupi")
                        }
                        else{
                            print("Transfer Rp $jumlahTF ke ${VA.nama} - ${inputVA}?\n" +
                                    "(Y/N): ")
                            var confirm = readln()
                            if (confirm == "Y"){
                                var sisa_saldo = u.saldo - (jumlahTF + u.bank.biayaVA!!)
                                println("Berhasil transfer Rp $jumlahTF ke ${VA.nama} ${inputVA}")
                                println("Sisa saldo Anda: Rp $sisa_saldo")
                                u.saldo = sisa_saldo
                                u.listMutasi.add("(- $jumlahTF) Transfer ke VA ${VA.nama} ${inputVA}")
                                u.listMutasi.add("(- ${u.bank.biayaVA}) Biaya admin VA")
                                println()
                            }
                            else if (confirm == "N"){
                                println("Gagal Transfer VA")
                                println()
                            }
                        }
                    }
                }
                else {
                    println("VA maksimal 6 digit")
                    println()
                }
            } while(!(inputVA.length in 6..9))

//            print("Input nomor VA: ")
//            var inputVA = readln().toInt()
//            var kodeVA = u.listVA.firstOrNull() { it.kode == inputVA }
//
//            if (kodeVA == null){
//                println("Nomor VA tidak ditemukan")
//            }
//            else {
//                print("Jumlah Transfer: ")
//                var jumlahTF = readln().toInt()
//                if (jumlahTF + u.bank.biayaVA!! > u.saldo){
//                    println("Saldo Anda tidak mencukupi")
//                }
//                else {
//                    print("Transfer Rp $jumlahTF ke ${kodeVA.nama} - ${kodeVA.kode}?\n" +
//                            "(Y/N): ")
//                    var confirm = readln()
//                    if (confirm == "Y"){
//                        var sisa_saldo = u.saldo - (jumlahTF + u.bank.biayaVA!!)
//                        println("Berhasil transfer Rp $jumlahTF ke ${kodeVA.nama} ${kodeVA.kode}")
//                        println("Sisa saldo Anda: Rp $sisa_saldo")
//                        u.saldo = sisa_saldo
//                        u.listMutasi.add("(- $jumlahTF) Transfer ke VA ${kodeVA.nama} ${kodeVA.kode}")
//                        u.listMutasi.add("(- ${u.bank.biayaVA}) Biaya admin VA")
//                        println()
//                    }
//                    else if (confirm == "N"){
//                        println("Gagal Transfer VA")
//                        println()
//                    }
//                }
//            }
        }
    } while(menuTF != 99)
}