package com.apap.tugas1.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class InstansiController {
	
	@Autowired
	private InstansiService instansiService;
	
	
	
/*	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewPegawaiTertuaTermuda(@RequestParam("id") Long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(id);
		model.addAttribute("instansi", instansi);
		//list
		List<PegawaiModel> objekPegawai = instansi.getListPegawai();
		List<Integer> listUmur = new ArrayList<>();
		
		for(int i = 0; i <objekPegawai.size(); i++) {
			int umur = 0;
			
			Date tanggalLahir = objekPegawai.get(i).getTanggalLahir();
			//parse
			LocalDate ldTglLahir = new java.sql.Date(tanggalLahir.getTime()).toLocalDate();
			LocalDate ldSekarang = ldTglLahir.now();
			
			//hitung
			if((ldTglLahir != null) && (ldSekarang !=null)) {
				umur = Period.between(ldTglLahir, ldSekarang).getYears();
			}
			
			listUmur.set(i, umur);	
		}
		
		//dapet tertua
		int umurTertua = 0;
		int indeksTertua = 0;
		for(int i = 0; i <listUmur.size(); i++) {
			if(listUmur.get(i) > umurTertua) {
				umurTertua = listUmur.get(i);
				indeksTertua = i;
			}
		}
		//ambil objectnya
		PegawaiModel pegawaiTertua = objekPegawai.get(indeksTertua);
		
		//dapet termuda
		int umurTermuda = 0;
		int indeksTermuda = 0;
		for(int i = 0; i <listUmur.size(); i++) {
			if(listUmur.get(i) < umurTertua) {
				umurTermuda = listUmur.get(i);
				indeksTermuda = i;
			}
		}
		
		//ambil objectnya
		PegawaiModel pegawaiTermuda = objekPegawai.get(indeksTermuda);
		
		return "view-jabatan";

	}*/
	
	
	
	
}